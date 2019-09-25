package cn.inslee.adminback.model.dao.sys;

import cn.inslee.adminback.model.base.BaseMapper;
import cn.inslee.adminback.model.domain.sys.SysRes;
import cn.inslee.adminback.model.dto.sys.SysResDTO;

import java.util.List;

public interface SysResMapper extends BaseMapper<SysRes> {
    /**
     * 根据角色的id查询关联的资源
     * @param roleIds
     * @return
     */
    List<SysRes> selectByRoleIds(List<Long> roleIds);

    /**
     * 根据角色id结合查询关联菜单资源
     * @param roleIds
     * @return
     */
    List<SysResDTO> selectMenuByRoleIds(List<Long> roleIds);

    /**
     * 根据id修改资源信息
     * @param res
     * @return
     */
    int updateById(SysRes res);

    /**
     * 根据资源id查询关联角色的数量
     * @param resId
     * @return
     */
    long countRoleByResId(Long resId);
}