package cn.inslee.admin.service.sys.impl;

import cn.inslee.admin.common.constant.PermConstant;
import cn.inslee.admin.model.dao.sys.SysRoleMapper;
import cn.inslee.admin.model.dao.sys.SysRoleResMapper;
import cn.inslee.admin.model.domain.sys.SysRole;
import cn.inslee.admin.model.domain.sys.SysRoleRes;
import cn.inslee.admin.model.dto.sys.SysRoleDTO;
import cn.inslee.admin.model.query.sys.RoleQuery;
import cn.inslee.admin.service.sys.SysRoleService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysRoleResMapper roleResMapper;

    @Override
    @Cacheable(value = PermConstant.ROLE, key = PermConstant.OBJ_KEY + "+ #p0")
    public Set<SysRole> selectByUserId(Long uid) {
        List<SysRole> roles = roleMapper.selectByUserId(uid);
        List<SysRole> groupRoles = roleMapper.selectByUserIdAssociateGroup(uid);
        //去除重复的角色
        roles.addAll(groupRoles);
        Set<SysRole> setRoles = Sets.newHashSet(roles);
        return setRoles;
    }

    @Override
    @Cacheable(value = PermConstant.ROLE, key = PermConstant.ID_KEY + "+ #p0")
    public List<Long> selectIdByUserId(Long uid) {
        Set<SysRole> roles = this.selectByUserId(uid);
        List<Long> roleIds = Lists.newArrayList();
        roles.forEach(role -> roleIds.add(role.getId()));
        return roleIds;
    }

    @Override
    @Cacheable(value = PermConstant.ROLE, key = PermConstant.CHAR_KEY + "+ #p0")
    public Set<String> selectRoleCharByUserId(Long uid) {
        Set<SysRole> roles = this.selectByUserId(uid);
        List<String> roleChars = Lists.newArrayList();
        roles.forEach(role -> roleChars.add(role.getRoleChar()));
        return Sets.newHashSet(roleChars);
    }

    @Override
    public List<SysRole> all() {
        SysRole role = new SysRole()
                .setDelFlag(false);
        return roleMapper.select(role);
    }

    @Override
    public PageInfo<SysRoleDTO> list(RoleQuery query) {
        query.setPageNum((query.getPageNum() - 1) * query.getPageSize());

        List<SysRoleDTO> data = roleMapper.selectList(query);
        long total = roleMapper.countList(query);

        PageInfo<SysRoleDTO> info = new PageInfo<>(data);
        info.setTotal(total);
        return info;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String add(SysRole role, List<SysRoleRes> sysRoleResList) {
        //校验角色名称是否存在，如果不存在添加角色
        SysRole example = new SysRole()
                .setName(role.getName())
                .setDelFlag(false);
        synchronized (this) {
            Assert.isNull(roleMapper.selectOne(example), "角色名称重复");
            roleMapper.insertSelective(role);
        }

        //添加角色关联资源的信息
        roleResMapper.insertList(sysRoleResList);
        return "角色添加成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(value = {PermConstant.ROLE, PermConstant.RES}, allEntries = true)
    public String update(SysRole role, List<SysRoleRes> sysRoleResList) {
        //校验角色名称是否存在，不存在则修改
        SysRole example = new SysRole()
                .setName(role.getName())
                .setDelFlag(false);
        synchronized (this) {
            SysRole sysRole = roleMapper.selectOne(example);
            Assert.isTrue(sysRole == null || sysRole.getId().equals(role.getId()), "角色名称重复");
            roleMapper.updateById(role);
            //删除之前关联的数据，添加更新的数据
            SysRoleRes sysRoleRes = new SysRoleRes()
                    .setRoleId(role.getId());
            roleResMapper.delete(sysRoleRes);
            roleResMapper.insertList(sysRoleResList);
        }
        return "角色修改成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(value = {PermConstant.ROLE, PermConstant.RES}, allEntries = true)
    public String delete(SysRole role) {
        //判断当前删除的角色是否关联用户，关联用户不能删除
        long userTotal = roleMapper.countUserByRoleId(role.getId());
        Assert.isTrue(userTotal == 0, "当前角色有用户关联，请先解除");

        //判断当前删除的角色是否关联用户组，关联用户组不能删除
        long groupTotal = roleMapper.countGroupByRoleId(role.getId());
        Assert.isTrue(groupTotal == 0, "当前角色有用户关联，请先解除");
        roleMapper.updateByPrimaryKeySelective(role);

        return "角色删除成功";
    }
}
