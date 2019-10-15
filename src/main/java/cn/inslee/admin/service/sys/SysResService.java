package cn.inslee.admin.service.sys;

import cn.inslee.admin.model.domain.sys.SysRes;

import java.util.List;
import java.util.Set;

/**
 * @author dean.lee
 * <p>
 */
public interface SysResService {
    /**
     * 根据角色id查询所有关联的资源
     *
     * @param roleIds
     * @return
     */
    Set<SysRes> selectByRoleIds(List<Long> roleIds);

    /**
     * 根据角色id集合查询拥有的菜单列表
     * @param roleIds
     * @return
     */
    List<SysRes> resources(List<Long> roleIds);

    /**
     * 查询资源列表
     * @return
     */
    List<SysRes> list();

    /**
     * 查询资源菜单列表
     * @return
     */
    List<SysRes> menuList();

    /**
     * 资源添加
     * @param res
     * @return
     */
    String add(SysRes res);

    /**
     * 修改资源
     * @param res
     * @return
     */
    String update(SysRes res);

    /**
     * 资源删除
     * @param res
     * @return
     */
    String delete(SysRes res);
}