package cn.inslee.admin.service.sys;

import cn.inslee.admin.model.domain.sys.SysUser;
import cn.inslee.admin.model.domain.sys.SysUserGroup;
import cn.inslee.admin.model.domain.sys.SysUserRole;
import cn.inslee.admin.model.dto.sys.SysUserDTO;
import cn.inslee.admin.model.query.sys.UserQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author dean.lee
 * <p>
 */
public interface SysUserService {
    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    SysUser selectByUsername(String username);

    /**
     * 查询用户列表
     * @param query
     * @return
     */
    PageInfo<SysUserDTO> list(UserQuery query);

    /**
     * 添加用户及关联的角色和用户组
     * @param user
     * @param sysUserRoleList
     * @param sysUserGroupList
     * @return
     */
    String add(SysUser user, List<SysUserRole> sysUserRoleList, List<SysUserGroup> sysUserGroupList);

    /**
     * 修改用户信息
     * @param user
     * @param sysUserRoleList
     * @param sysUserGroupList
     * @return
     */
    String update(SysUser user, List<SysUserRole> sysUserRoleList, List<SysUserGroup> sysUserGroupList);

    /**
     * 删除用户
     * @param user
     * @return
     */
    String delete(SysUser user);

    /**
     * 用户修改锁定状态
     * @param user
     * @return
     */
    String status(SysUser user);

    /**
     * 用户修改自己的信息
     * @param user
     * @return
     */
    String updateSelf(SysUser user);

    /**
     * 重置密码
     * @param user
     * @return
     */
    String resetPwd(SysUser user);
}
