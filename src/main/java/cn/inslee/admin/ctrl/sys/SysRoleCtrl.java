package cn.inslee.admin.ctrl.sys;

import cn.inslee.admin.common.annotation.Limiting;
import cn.inslee.admin.common.annotation.SystemLog;
import cn.inslee.admin.common.result.JsonResult;
import cn.inslee.admin.common.result.PageResult;
import cn.inslee.admin.common.test.TestUtil;
import cn.inslee.admin.common.util.Key;
import cn.inslee.admin.model.domain.sys.SysRole;
import cn.inslee.admin.model.domain.sys.SysRoleRes;
import cn.inslee.admin.model.domain.sys.SysUser;
import cn.inslee.admin.model.dto.sys.SysRoleDTO;
import cn.inslee.admin.model.from.sys.RoleAddFrom;
import cn.inslee.admin.model.from.sys.RoleUpdateFrom;
import cn.inslee.admin.model.query.sys.RoleQuery;
import cn.inslee.admin.service.sys.SysRoleService;
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
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author dean.lee
 * <p>
 */
@RestController
@RequestMapping("/sys/role")
@Validated
public class SysRoleCtrl {

    @Autowired
    private SysRoleService roleService;

    /**
     * 用户页面和用户组页面检索条件和添加修改选择时使用，
     * 只返回角色id和名称
     *
     * @return
     */
    @Limiting
    @GetMapping("all")
    @RequiresPermissions(value = {"sys:user:list", "sys:user:add", "sys:user.update", "sys:user:delete", "sys:user:status",
            "sys:group:list", "sys:group:add", "sys:group:update", "sys:group:delete"}, logical = Logical.OR)
    public JsonResult all() {
        return JsonResult.success(roleService.all());
    }

    /**
     * 查询角色列表
     *
     * @param query
     * @return
     */
    @Limiting
    @GetMapping("/list")
    @RequiresPermissions(value = {"sys:role:page", "sys:role:list", "sys:role:add", "sys:role:update", "sys:role:delete"}, logical = Logical.OR)
    public JsonResult list(@Validated RoleQuery query) {
        PageInfo<SysRoleDTO> list = roleService.list(query);
        return PageResult.success(list.getList(), list.getTotal());
    }

    /**
     * 角色添加
     *
     * @param roleFrom
     * @return
     */
    @SystemLog("角色添加")
    @Limiting
    @PostMapping("/add")
    @RequiresPermissions(value = "sys:role:add")
    public JsonResult add(@Validated @RequestBody RoleAddFrom roleFrom) {
        //copy角色属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysRole role = new SysRole()
                .setId(Key.nextKey())
                .setCreator(admin.getId())
                .setCreationTime(System.currentTimeMillis());
        BeanUtils.copyProperties(roleFrom, role);

        //copy角色关联的资源
        List<SysRoleRes> sysRoleResList = this.copyRoleRes(roleFrom.getResIds(), role.getId());

        return JsonResult.success(roleService.add(role, sysRoleResList));
    }

    @SystemLog("角色修改")
    @RequiresPermissions("sys:role:update")
    @PutMapping("update")
    @Limiting
    public JsonResult update(@Validated @RequestBody RoleUpdateFrom roleFrom) {
        //线上演示使用
        TestUtil.isAdminRole(roleFrom.getId());
        //copy角色属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysRole role = new SysRole()
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());
        BeanUtils.copyProperties(roleFrom, role);

        //copy角色关联资源的属性
        List<SysRoleRes> sysRoleResList = this.copyRoleRes(roleFrom.getResIds(), role.getId());

        return JsonResult.success(roleService.update(role, sysRoleResList));
    }

    /**
     * 角色删除
     *
     * @return
     */
    @SystemLog("角色删除")
    @Limiting
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public JsonResult delete(@NotNull(message = "角色id不能为空") Long id) {
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysRole role = new SysRole()
                .setId(id)
                .setDelFlag(true)
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());

        return JsonResult.success(roleService.delete(role));
    }

    /**
     * copy角色关联资源的数据到SysRoleRes实体类中
     *
     * @param resIds
     * @param roleId
     * @return
     */
    private List<SysRoleRes> copyRoleRes(Set<Long> resIds, Long roleId) {
        List<SysRoleRes> sysRoleResList = Lists.newArrayList();
        resIds.forEach(resId -> {
            SysRoleRes sysRoleRes = new SysRoleRes()
                    .setResId(resId)
                    .setRoleId(roleId);
            sysRoleResList.add(sysRoleRes);
        });

        return sysRoleResList;
    }
}
