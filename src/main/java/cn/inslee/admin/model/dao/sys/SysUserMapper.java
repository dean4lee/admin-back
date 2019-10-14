package cn.inslee.admin.model.dao.sys;

import cn.inslee.admin.model.base.BaseMapper;
import cn.inslee.admin.model.domain.sys.SysUser;
import cn.inslee.admin.model.dto.sys.SysUserDTO;
import cn.inslee.admin.model.query.sys.UserQuery;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 查询用户列表
     * @param query
     * @return
     */
    List<SysUserDTO> selectList(UserQuery query);

    /**
     * 统计用户列表的条数
     * @param query
     * @return
     */
    long countList(UserQuery query);

    /**
     * 根据用户id修改用户
     * @param user
     * @return
     */
    int updateById(SysUser user);

    /**
     * 用户修改自己的信息
     * @param user
     * @return
     */
    int updateBySelf(SysUser user);

    /**
     * 根据部门id统计关联用户的条数
     * @param deptId
     * @return
     */
    long countUserBydeptId(Long deptId);
}