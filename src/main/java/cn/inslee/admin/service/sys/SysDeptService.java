package cn.inslee.admin.service.sys;

import cn.inslee.admin.model.domain.sys.SysDept;
import cn.inslee.admin.model.query.sys.DeptQuery;

import java.util.List;

/**
 * @author dean.lee
 * <p>
 */
public interface SysDeptService {

    /**
     * 查询部门列表
     * @param query
     * @return
     */
    List<SysDept> list(DeptQuery query);

    /**
     * 添加部门
     * @param dept
     * @return
     */
    String add(SysDept dept);

    /**
     * 修改部门
     * @param dept
     * @return
     */
    String update(SysDept dept);

    /**
     * 删除部门
     * @param dept
     * @return
     */
    String delete(SysDept dept);
}
