package cn.inslee.adminback.service.sys;

import cn.inslee.adminback.model.domain.sys.SysRole;
import cn.inslee.adminback.model.domain.sys.SysRoleRes;
import cn.inslee.adminback.model.dto.sys.SysRoleDTO;
import cn.inslee.adminback.model.query.sys.RoleQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

/**
 * @author dean.lee
 * <p>
 */
public interface SysRoleService {
    /**
     * 根据用户id查询所有关联的角色
     *
     * @param uid
     * @return
     */
    Set<SysRole> selectAllByUserId(Long uid);

    /**
     * 查询所有的角色信息，只包含id和名称
     * @return
     */
    List<SysRole> all();

    /**
     * 查询角色列表，包含关联资源的id
     * @param query
     * @return
     */
    PageInfo<SysRoleDTO> list(RoleQuery query);

    /**
     * 添加角色及关联的资源
     * @param role
     * @param sysRoleResList
     * @return
     */
    String add(SysRole role, List<SysRoleRes> sysRoleResList);

    /**
     * 角色修改
     * @param role
     * @param sysRoleResList
     * @return
     */
    String update(SysRole role, List<SysRoleRes> sysRoleResList);

    /**
     * 角色删除
     * @param role
     * @return
     */
    String delete(SysRole role);
}
