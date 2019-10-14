package cn.inslee.admin.model.dao.sys;

import cn.inslee.admin.model.base.BaseMapper;
import cn.inslee.admin.model.domain.sys.SysRole;
import cn.inslee.admin.model.dto.sys.SysRoleDTO;
import cn.inslee.admin.model.query.sys.RoleQuery;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户id查询所有关联的角色
     * @param uid
     * @return
     */
    List<SysRole> selectByUserId(Long uid);

    /**
     * 根据用户id关联用户组查询所有关联的角色
     * @param uid
     * @return
     */
    List<SysRole> selectByUserIdAssociateGroup(Long uid);

    /**
     * 条件查询角色列表
     * @param query
     * @return
     */
    List<SysRoleDTO> selectList(RoleQuery query);

    /**
     * 统计条件查询角色列表的条数
     * @param query
     * @return
     */
    long countList(RoleQuery query);

    /**
     * 根据id修改角色信息
     * @param role
     * @return
     */
    int updateById(SysRole role);

    /**
     * 根据角色id查询关联的用户数量
     * @param roleId
     * @return
     */
    long countUserByRoleId(Long roleId);

    /**
     * 根据角色id查询关联的用户组数量
     * @param roleId
     * @return
     */
    long countGroupByRoleId(Long roleId);
}