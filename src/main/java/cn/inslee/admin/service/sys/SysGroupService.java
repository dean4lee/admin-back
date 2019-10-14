package cn.inslee.admin.service.sys;

import cn.inslee.admin.model.domain.sys.SysGroup;
import cn.inslee.admin.model.domain.sys.SysGroupRole;
import cn.inslee.admin.model.dto.sys.SysGroupDTO;
import cn.inslee.admin.model.query.sys.GroupQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author dean.lee
 * <p>
 */
public interface SysGroupService {
    /**
     * 查询所有的用户组信息，只包含id和名称
     * @return
     */
    List<SysGroup> all();

    /**
     * 查询用户组列表
     * @param query
     * @return
     */
    PageInfo<SysGroupDTO> list(GroupQuery query);

    /**
     * 添加用户组
     * @param group
     * @param sysGroupRoleList
     * @return
     */
    String add(SysGroup group, List<SysGroupRole> sysGroupRoleList);

    /**
     * 用户组修改
     * @param group
     * @param sysGroupRoleList
     * @return
     */
    String update(SysGroup group, List<SysGroupRole> sysGroupRoleList);

    /**
     * 用户组删除
     * @param group
     * @return
     */
    String delete(SysGroup group);
}
