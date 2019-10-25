package cn.inslee.admin.ctrl.sys;

import cn.inslee.admin.common.annotation.Limiting;
import cn.inslee.admin.common.annotation.SystemLog;
import cn.inslee.admin.common.result.JsonResult;
import cn.inslee.admin.common.util.Key;
import cn.inslee.admin.model.domain.sys.SysDept;
import cn.inslee.admin.model.domain.sys.SysUser;
import cn.inslee.admin.model.from.sys.DeptAddFrom;
import cn.inslee.admin.model.from.sys.DeptUpdateFrom;
import cn.inslee.admin.model.query.sys.DeptQuery;
import cn.inslee.admin.service.sys.SysDeptService;
import cn.inslee.admin.shiro.util.ShiroUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author dean.lee
 * <p>
 */
@RestController
@RequestMapping("/sys/dept")
@Validated
public class SysDeptCtrl {

    @Autowired
    private SysDeptService deptService;


    /**
     * 查询部门列表
     * @param query
     * @return
     */
    @Limiting
    @GetMapping("/list")
    @RequiresPermissions(value = {"sys:user:list", "sys:user:add", "sys:user:update", "sys:user:delete",
            "sys:dept:list", "sys:dept:add", "sys:dept:update", "sys:dept:delete"}, logical = Logical.OR)
    public JsonResult list(DeptQuery query){
        return JsonResult.success(deptService.list(query));
    }

    /**
     * 添加部门
     * @param deptFrom
     * @return
     */
    @SystemLog("部门添加")
    @Limiting
    @PostMapping("/add")
    @RequiresPermissions("sys:dept:add")
    public JsonResult add(@Validated @RequestBody DeptAddFrom deptFrom){
        //copy 部门属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysDept dept = new SysDept()
                .setId(Key.nextKey())
                .setCreator(admin.getId())
                .setCreationTime(System.currentTimeMillis());
        BeanUtils.copyProperties(deptFrom, dept);

        return JsonResult.success(deptService.add(dept));
    }


    @SystemLog("部门修改")
    @Limiting
    @PutMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public JsonResult update(@Validated @RequestBody DeptUpdateFrom deptFrom){
        //copy 部门属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysDept dept = new SysDept()
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());
        BeanUtils.copyProperties(deptFrom, dept);

        return JsonResult.success(deptService.update(dept));
    }

    @SystemLog("部门删除")
    @Limiting
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:dept:delete")
    public JsonResult delete(@NotNull(message = "部门id不能为空") Long id){
        //copy 部门属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysDept dept = new SysDept()
                .setId(id)
                .setDelFlag(true)
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());

        return JsonResult.success(deptService.delete(dept));
    }
}
