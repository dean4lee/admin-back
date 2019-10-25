package cn.inslee.admin.ctrl.sys;

import cn.inslee.admin.common.annotation.Limiting;
import cn.inslee.admin.common.annotation.SystemLog;
import cn.inslee.admin.common.result.JsonResult;
import cn.inslee.admin.common.test.TestUtil;
import cn.inslee.admin.common.util.Key;
import cn.inslee.admin.model.domain.sys.SysRes;
import cn.inslee.admin.model.domain.sys.SysUser;
import cn.inslee.admin.model.from.sys.ResAddFrom;
import cn.inslee.admin.model.from.sys.ResUpdateFrom;
import cn.inslee.admin.service.sys.SysResService;
import cn.inslee.admin.service.sys.SysRoleService;
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
import java.util.List;
import java.util.Set;

/**
 * @author dean.lee
 * <p>
 */
@RestController
@RequestMapping("/sys/res")
public class SysResCtrl {

    @Autowired
    private SysResService resService;
    @Autowired
    private SysRoleService roleService;

    /**
     * 用户获取自己拥有的菜单树结构列表。
     * 对应主页的菜单项
     *
     * @return
     */
    @Limiting
    @GetMapping("/resources")
    public JsonResult resources() {
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        List<SysRes> result = resService.resources(admin.getId());
        return JsonResult.success(result);
    }

    /**
     * 获取拥有的权限字符(控制页面增删改按钮的显示)
     *
     * @return
     */
    @Limiting
    @GetMapping("getPerms")
    public JsonResult getPerms() {
        //获取缓存中的权限字符
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        Set<String> resChars = resService.selectPremCharByUserId(admin.getId());
        return JsonResult.success(resChars);
    }

    /**
     * 角色页面查询资源列表，资源页面查询列表，返回树结构
     *
     * @return
     */
    @Limiting
    @GetMapping("/list")
    @RequiresPermissions(value = {"sys:role:list", "sys:role:add", "sys:role:update", "sys:role:delete",
            "sys:res:list", "sys:res:add", "sys:res:update", "sys:res:delete"}, logical = Logical.OR)
    public JsonResult list() {
        return JsonResult.success(resService.list());
    }

    /**
     * 添加修改资源时获取的菜单列表
     *
     * @return
     */
    @Limiting
    @GetMapping("/menuList")
    @RequiresPermissions(value = {"sys:res:add", "sys:res:update"}, logical = Logical.OR)
    public JsonResult menuList() {
        return JsonResult.success(resService.menuList());
    }

    /**
     * 资源添加
     *
     * @param resFrom
     * @return
     */
    @SystemLog("资源添加")
    @Limiting
    @PostMapping("/add")
    @RequiresPermissions("sys:res:add")
    public JsonResult add(@Validated @RequestBody ResAddFrom resFrom) {
        //copy资源属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysRes res = new SysRes()
                .setId(Key.nextKey())
                .setCreator(admin.getId())
                .setCreationTime(System.currentTimeMillis());
        BeanUtils.copyProperties(resFrom, res);

        return JsonResult.success(resService.add(res));
    }

    /**
     * 资源修改
     *
     * @param resFrom
     * @return
     */
    @SystemLog("资源修改")
    @Limiting
    @PutMapping("/update")
    @RequiresPermissions("sys:res:update")
    public JsonResult update(@Validated @RequestBody ResUpdateFrom resFrom) {
        //线上演示使用
        TestUtil.isAdminRes(resFrom.getId());
        //copy 资源属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysRes res = new SysRes()
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());
        BeanUtils.copyProperties(resFrom, res);

        return JsonResult.success(resService.update(res));
    }

    /**
     * 资源删除
     *
     * @param id
     * @return
     */
    @SystemLog("资源删除")
    @Limiting
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:res:delete")
    public JsonResult delete(@NotNull(message = "资源id不能为空") Long id) {
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysRes res = new SysRes()
                .setId(id)
                .setDelFlag(true)
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());

        return JsonResult.success(resService.delete(res));
    }
}
