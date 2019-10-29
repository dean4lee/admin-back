package cn.inslee.admin.service.sys.impl;

import cn.inslee.admin.model.dao.sys.SysDeptMapper;
import cn.inslee.admin.model.dao.sys.SysUserMapper;
import cn.inslee.admin.model.domain.sys.SysDept;
import cn.inslee.admin.model.query.sys.DeptQuery;
import cn.inslee.admin.service.sys.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author dean.lee
 * <p>
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private SysUserMapper userMapper;


    @Override
    public List<SysDept> list(DeptQuery query) {
        return deptMapper.selectBySelective(query);
    }

    @Override
    public String add(SysDept dept) {
        //校验名称是否重复
        SysDept example = new SysDept()
                .setName(dept.getName())
                .setDelFlag(false);
        synchronized (this) {
            Assert.isNull(deptMapper.selectOne(example), "部门名称重复");
            //获取上级部门，添加层级字段level
            this.addLevel(dept);
            deptMapper.insertSelective(dept);
        }

        return "部门添加成功";
    }

    @Override
    public String update(SysDept dept) {
        SysDept example = new SysDept()
                .setName(dept.getName())
                .setDelFlag(false);

        synchronized (this) {
            SysDept sysDept = deptMapper.selectOne(example);
            Assert.isTrue(sysDept == null || sysDept.getId().equals(dept.getId()), "部门名称重复");
            //获取上级部门，添加层级字段level
            this.addLevel(dept);
            deptMapper.updateById(dept);
        }
        return "部门修改成功";
    }

    @Override
    public String delete(SysDept dept) {
        //判断当前删除的资源是否关联角色，关联角色则不能删除
        long userTotal = userMapper.countUserBydeptId(dept.getId());
        Assert.isTrue(userTotal == 0, "当前部门或下级部门有用户关联，请先解除");

        deptMapper.updateByPrimaryKeySelective(dept);

        return "部门删除成功";
    }

    /**
     * 添加level层级字段
     * @param dept
     */
    private void addLevel(SysDept dept){
        //获取上级部门，添加层级字段level
        if(dept.getPid().equals(0L)){
            dept.setLevel("0");
        }else {
            SysDept example = new SysDept()
                    .setDelFlag(false)
                    .setId(dept.getPid());
            SysDept parentDept = deptMapper.selectOne(example);
            //校验父级部门是否存在
            Assert.notNull(parentDept, "请选择正确的父级部门");
            //校验父级资源是否原本是自己的子级
            Assert.isTrue(!parentDept.getLevel().contains(dept.getId().toString()), "父级部门不能向下级修改");
            dept.setLevel(parentDept.getLevel() + "," + dept.getPid());
        }
    }
}
