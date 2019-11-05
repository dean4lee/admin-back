package cn.inslee.admin.ctrl.sys;

import cn.inslee.admin.common.annotation.Limiting;
import cn.inslee.admin.common.annotation.SystemLog;
import cn.inslee.admin.common.result.JsonResult;
import cn.inslee.admin.common.result.PageResult;
import cn.inslee.admin.common.util.Key;
import cn.inslee.admin.model.domain.sys.SysGroup;
import cn.inslee.admin.model.domain.sys.SysGroupRole;
import cn.inslee.admin.model.domain.sys.SysUser;
import cn.inslee.admin.model.dto.sys.SysGroupDTO;
import cn.inslee.admin.model.form.sys.GroupAddForm;
import cn.inslee.admin.model.form.sys.GroupUpdateForm;
import cn.inslee.admin.model.query.sys.GroupQuery;
import cn.inslee.admin.service.sys.SysGroupService;
import cn.inslee.admin.shiro.util.ShiroUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
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
import java.util.List;
import java.util.Set;

/**
 * @author dean.lee
 * <p>
 */
@RestController
@RequestMapping("/sys/group")
@Validated
public class SysGroupCtrl {

    @Autowired
    private SysGroupService groupService;

    /**
     * 用户检索条件，添加修改选择的用户组时使用
     *
     * 只返回用户组id和名称
     * @return
     */
    @Limiting
    @GetMapping("/all")
    @RequiresPermissions(value = { "sys:user:list", "sys:user:add", "sys:user.update", "sys:user:delete", "sys:user:status"}, logical = Logical.OR)
    public JsonResult all(){
        return JsonResult.success(groupService.all());
    }


    /**
     * 用户组列表
     * @param query
     * @return
     */
    @Limiting
    @GetMapping("/list")
    @RequiresPermissions(value = {"sys:group:page", "sys:group:list", "sys:group:add", "sys:group:update", "sys:group:delete"}, logical = Logical.OR)
    public JsonResult list(@Validated GroupQuery query) {
        PageInfo<SysGroupDTO> list = groupService.list(query);
        return PageResult.success(list.getList(), list.getTotal());
    }

    /**
     * 用户组添加
     * @return
     */
    @SystemLog("用户组添加")
    @Limiting
    @PostMapping("/add")
    @RequiresPermissions("sys:group:add")
    public JsonResult add(@Validated @RequestBody GroupAddForm groupForm){
        //copy角色属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysGroup group = new SysGroup()
                .setId(Key.nextKey())
                .setCreator(admin.getId())
                .setCreationTime(System.currentTimeMillis());
        BeanUtils.copyProperties(groupForm, group);

        //copy关联的角色信息
        List<SysGroupRole> sysGroupRoleList = this.copyGroupRole(groupForm.getRoleIds(), group.getId());
        return JsonResult.success(groupService.add(group, sysGroupRoleList));
    }

    /**
     * 用户组修改
     * @param groupForm
     * @return
     */
    @SystemLog("用户组修改")
    @Limiting
    @PutMapping("/update")
    @RequiresPermissions("sys:group:update")
    public JsonResult update(@Validated @RequestBody GroupUpdateForm groupForm){
        //copy用户组属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysGroup group = new SysGroup()
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());
        BeanUtils.copyProperties(groupForm, group);

        //copy关联角色的属性
        List<SysGroupRole> sysGroupRoleList = this.copyGroupRole(groupForm.getRoleIds(), group.getId());

        return JsonResult.success(groupService.update(group, sysGroupRoleList));
    }

    /**
     * 用户组删除
     *
     * @return
     */
    @SystemLog("用户组删除")
    @Limiting
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:group:delete")
    public JsonResult delete(@NotNull(message = "用户组id不能为空") Long id) {
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysGroup group = new SysGroup()
                .setId(id)
                .setDelFlag(true)
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());

        return JsonResult.success(groupService.delete(group));
    }

    /**
     * copy用户组关联角色的属性到SysGroupRole实体类中
     * @param roleIds
     * @param groupId
     * @return
     */
    private List<SysGroupRole> copyGroupRole(Set<Long> roleIds, Long groupId) {
        List<SysGroupRole> sysGroupRoleList = Lists.newArrayList();
        roleIds.forEach(roleId -> {
            SysGroupRole sysGroupRole = new SysGroupRole()
                    .setRoleId(roleId)
                    .setGroupId(groupId);
            sysGroupRoleList.add(sysGroupRole);
        });

        return sysGroupRoleList;
    }

}
