package cn.inslee.admin.model.dao.sys;

import cn.inslee.admin.model.base.BaseMapper;
import cn.inslee.admin.model.domain.sys.SysDept;
import cn.inslee.admin.model.query.sys.DeptQuery;

import java.util.List;

public interface SysDeptMapper extends BaseMapper<SysDept> {

    List<SysDept> selectBySelective(DeptQuery query);

    int updateById(SysDept dept);
}