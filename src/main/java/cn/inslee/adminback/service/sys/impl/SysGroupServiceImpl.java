package cn.inslee.adminback.service.sys.impl;

import cn.inslee.adminback.common.constant.RedisConstant;
import cn.inslee.adminback.model.dao.sys.SysGroupMapper;
import cn.inslee.adminback.model.dao.sys.SysGroupRoleMapper;
import cn.inslee.adminback.model.domain.sys.SysGroup;
import cn.inslee.adminback.model.domain.sys.SysGroupRole;
import cn.inslee.adminback.model.dto.sys.SysGroupDTO;
import cn.inslee.adminback.model.query.sys.GroupQuery;
import cn.inslee.adminback.service.sys.SysGroupService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

/**
 * @author dean.lee
 * <p>
 */
@Service
public class SysGroupServiceImpl implements SysGroupService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SysGroupMapper groupMapper;
    @Autowired
    private SysGroupRoleMapper groupRoleMapper;

    @Override
    public List<SysGroup> all() {
        SysGroup group = new SysGroup()
                .setDelFlag(false);
        return groupMapper.select(group);
    }

    @Override
    public PageInfo<SysGroupDTO> list(GroupQuery query) {
        query.setPageNum((query.getPageNum() - 1) * query.getPageSize());

        List<SysGroupDTO> data = groupMapper.selectList(query);
        long total = groupMapper.countList(query);

        PageInfo<SysGroupDTO> info = new PageInfo<>(data);
        info.setTotal(total);
        return info;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String add(SysGroup group, List<SysGroupRole> sysGroupRoleList) {
        //校验用户组名称是否存在，如果不存在添加角色
        SysGroup example = new SysGroup()
                .setName(group.getName())
                .setDelFlag(false);

        synchronized (this) {
            Assert.isNull(groupMapper.selectOne(example), "用户组名称重复");
            groupMapper.insertSelective(group);
        }

        //添加关联角色的信息
        groupRoleMapper.insertList(sysGroupRoleList);
        return "用户组添加成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String update(SysGroup group, List<SysGroupRole> sysGroupRoleList) {
        //校验角色名称是否存在，不存在则修改
        SysGroup example = new SysGroup()
                .setName(group.getName())
                .setDelFlag(false);
        synchronized (this) {
            SysGroup sysGroup = groupMapper.selectOne(example);
            Assert.isTrue(sysGroup == null || sysGroup.getId().equals(group.getId()), "角色名称重复");
            groupMapper.updateById(group);
            //删除之前关联的数据，添加更新的数据
            SysGroupRole sysGroupRole = new SysGroupRole()
                    .setGroupId(group.getId());
            groupRoleMapper.delete(sysGroupRole);
            groupRoleMapper.insertList(sysGroupRoleList);
        }

        //删除所有用户的权限缓存
        Set<String> keys = redisTemplate.keys(RedisConstant.PERM_KEY + "*");
        redisTemplate.delete(keys);
        return "用户组修改成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String delete(SysGroup group) {
        //判断当前删除的角色是否关联用户，关联用户不能删除
        long userTotal = groupMapper.countUserByGroupId(group.getId());
        Assert.isTrue(userTotal == 0, "当前用户组有用户关联，请先解除");

        groupMapper.updateByPrimaryKeySelective(group);

        //删除所有用户的权限缓存
        Set<String> keys = redisTemplate.keys(RedisConstant.PERM_KEY + "*");
        redisTemplate.delete(keys);
        return "用户组删除成功";
    }
}
