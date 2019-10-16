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
     * 根据用户id查询所有关联的资源的权限字符
     *
     * @param uid
     * @return
     */
    Set<String> selectPremCharByUserId(Long uid);

    /**
     * 根据用户id查询拥有的菜单列表
     * @param uid
     * @return
     */
    List<SysRes> resources(Long uid);

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
