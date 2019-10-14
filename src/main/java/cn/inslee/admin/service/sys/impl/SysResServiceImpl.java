package cn.inslee.admin.service.sys.impl;

import cn.inslee.admin.common.constant.Constant;
import cn.inslee.admin.model.dao.sys.SysResMapper;
import cn.inslee.admin.model.domain.sys.SysRes;
import cn.inslee.admin.service.sys.SysResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dean.lee
 * <p>
 */
@Service
public class SysResServiceImpl implements SysResService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SysResMapper resMapper;

    @Override
    @Cacheable(value = Constant.PERM_KEY, key = "'res:' + #p0")
    public Set<SysRes> selectByRoleIds(List<Long> roleIds) {
        List<SysRes> ress = resMapper.selectByRoleIds(roleIds);
        return new HashSet<>(ress);
    }

    @Override
    @Cacheable(value = Constant.PERM_KEY, key = "'menu:' + #p0")
    public List<SysRes> resources(List<Long> roleIds) {
        List<SysRes> resList = resMapper.selectMenuByRoleIds(roleIds);
        return resList;
    }

    @Override
    public List<SysRes> list() {
        //查询所有未删除的资源
        SysRes example = new SysRes().setDelFlag(false);
        List<SysRes> resList = resMapper.select(example);

        return resList;
    }

    @Override
    public List<SysRes> menuList() {
        Example example = new Example(SysRes.class);
        example.selectProperties("id", "name");
        example.and().andEqualTo("delFlag", false)
                .andEqualTo("type", 1);
        List<SysRes> menuList = resMapper.selectByExample(example);
        return menuList;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String add(SysRes res) {
        SysRes example = new SysRes()
                .setDelFlag(false)
                .setName(res.getName());
        synchronized (this) {
            Assert.isNull(resMapper.selectOne(example), "资源名称重复");
            resMapper.insertSelective(res);
        }
        return "添加资源成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(value = Constant.PERM_KEY, allEntries = true)
    public String update(SysRes res) {
        SysRes example = new SysRes()
                .setDelFlag(false)
                .setName(res.getName());
        synchronized (this) {
            SysRes sysRes = resMapper.selectOne(example);
            Assert.isTrue(sysRes == null || sysRes.getId().equals(res.getId()), "资源名称重复");
            resMapper.updateById(res);
        }
        return "修改资源成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(value = Constant.PERM_KEY, allEntries = true)
    public String delete(SysRes res) {
        //判断当前删除的资源是否关联角色，关联角色则不能删除
        long roleTotal = resMapper.countRoleByResId(res.getId());
        Assert.isTrue(roleTotal == 0, "当前资源有角色关联，请先解除");

        resMapper.updateByPrimaryKeySelective(res);
        return "资源删除成功";
    }
}
