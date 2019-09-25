package cn.inslee.adminback.model.dao.sys;

import cn.inslee.adminback.model.base.BaseMapper;
import cn.inslee.adminback.model.domain.sys.SysGroup;
import cn.inslee.adminback.model.dto.sys.SysGroupDTO;
import cn.inslee.adminback.model.query.sys.GroupQuery;

import java.util.List;

public interface SysGroupMapper extends BaseMapper<SysGroup> {
    /**
     * 条件查询用户组列表
     * @param query
     * @return
     */
    List<SysGroupDTO> selectList(GroupQuery query);

    /**
     * 统计条件查询用户组列表的条数
     * @param query
     * @return
     */
    long countList(GroupQuery query);

    /**
     * 根据用户组id修改用户组信息
     * @param group
     * @return
     */
    int updateById(SysGroup group);

    /**
     * 根据用户组id统计关联用户的数量
     * @param groupId
     * @return
     */
    long countUserByGroupId(Long groupId);
}