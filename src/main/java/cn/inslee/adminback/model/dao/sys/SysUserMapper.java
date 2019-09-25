package cn.inslee.adminback.model.dao.sys;

import cn.inslee.adminback.model.base.BaseMapper;
import cn.inslee.adminback.model.domain.sys.SysUser;
import cn.inslee.adminback.model.dto.sys.SysUserDTO;
import cn.inslee.adminback.model.query.sys.UserQuery;

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
}