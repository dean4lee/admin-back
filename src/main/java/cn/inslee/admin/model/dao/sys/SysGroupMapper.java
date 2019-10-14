package cn.inslee.admin.model.dao.sys;

import cn.inslee.admin.model.base.BaseMapper;
import cn.inslee.admin.model.domain.sys.SysGroup;
import cn.inslee.admin.model.dto.sys.SysGroupDTO;
import cn.inslee.admin.model.query.sys.GroupQuery;

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