package cn.inslee.admin.ctrl.sys;

import cn.inslee.admin.common.annotation.Limiting;
import cn.inslee.admin.common.annotation.SystemLog;
import cn.inslee.admin.common.constant.Constant;
import cn.inslee.admin.common.result.JsonResult;
import cn.inslee.admin.common.result.PageResult;
import cn.inslee.admin.common.test.TestUtil;
import cn.inslee.admin.common.util.Key;
import cn.inslee.admin.common.util.RandomUtil;
import cn.inslee.admin.model.domain.sys.SysUser;
import cn.inslee.admin.model.domain.sys.SysUserGroup;
import cn.inslee.admin.model.domain.sys.SysUserRole;
import cn.inslee.admin.model.dto.sys.SysUserDTO;
import cn.inslee.admin.model.form.sys.ResetPwdForm;
import cn.inslee.admin.model.form.sys.UserAddForm;
import cn.inslee.admin.model.form.sys.UserStatusForm;
import cn.inslee.admin.model.form.sys.UserUpdateForm;
import cn.inslee.admin.model.form.sys.UserUpdateSelfForm;
import cn.inslee.admin.model.query.sys.UserQuery;
import cn.inslee.admin.service.sys.SysUserService;
import cn.inslee.admin.shiro.util.ShiroUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
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
import java.util.UUID;

/**
 * @author dean.lee
 * <p>
 */
@RestController
@RequestMapping("/sys/user")
@Validated
public class SysUserCtrl {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SysUserService userService;

    /**
     * 查询用户列表
     *
     * @param query
     * @return
     */
    @Limiting
    @GetMapping("/list")
    @RequiresPermissions(value = {"sys:user:page", "sys:user:list", "sys:user:add", "sys:user:update", "sys:user:delete", "sys:user:status"}, logical = Logical.OR)
    public JsonResult list(@Validated UserQuery query) {
        PageInfo<SysUserDTO> info = userService.list(query);
        return PageResult.success(info.getList(), info.getTotal());
    }

    /**
     * 用户添加
     *
     * @param userForm
     * @return
     */
    @SystemLog("用户添加")
    @Limiting
    @PostMapping("/add")
    @RequiresPermissions("sys:user:add")
    public JsonResult add(@Validated @RequestBody UserAddForm userForm) {
        //判断用户关联的角色和用户组，如果两者都没关联，返回错误
        Assert.isTrue((userForm.getRoleIds() != null && !userForm.getRoleIds().isEmpty()) ||
                (userForm.getGroupIds() != null && !userForm.getGroupIds().isEmpty()), "角色和用户组必须选择");
        //生成随机盐
        String salt = UUID.randomUUID().toString();
        //生成6位随机密码
        String password = RandomUtil.getRandom(6);
        //copy用户属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysUser user = new SysUser().setId(Key.nextKey())
                .setCreator(admin.getId()).setCreationTime(System.currentTimeMillis())
                .setSalt(salt).setPassword(password);
        BeanUtils.copyProperties(userForm, user);

        //copy用户关联的角色属性
        List<SysUserRole> sysUserRoleList = this.copyUserRole(userForm.getRoleIds(), user.getId());

        //copy用户关联的用户组属性
        List<SysUserGroup> sysUserGroupList = this.copyUserGroup(userForm.getGroupIds(), user.getId());

        return JsonResult.success(userService.add(user, sysUserRoleList, sysUserGroupList));
    }

    /**
     * 修改用户信息
     *
     * @param userForm
     * @return
     */
    @SystemLog("用户修改")
    @Limiting
    @PutMapping("/update")
    @RequiresPermissions("sys:user:update")
    public JsonResult update(@Validated @RequestBody UserUpdateForm userForm) {
        //线上演示使用
        TestUtil.isAdmin(userForm.getId());
        //判断用户关联的角色和用户组，如果两者都没关联，返回错误
        Assert.isTrue((userForm.getRoleIds() != null && !userForm.getRoleIds().isEmpty()) ||
                (userForm.getGroupIds() != null && !userForm.getGroupIds().isEmpty()), "角色和用户组必须选择");

        //copy用户属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysUser user = new SysUser()
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());
        BeanUtils.copyProperties(userForm, user);

        //copy用户关联的角色属性
        List<SysUserRole> sysUserRoleList = this.copyUserRole(userForm.getRoleIds(), user.getId());

        //copy用户关联的用户组属性
        List<SysUserGroup> sysUserGroupList = this.copyUserGroup(userForm.getGroupIds(), user.getId());

        return JsonResult.success(userService.update(user, sysUserRoleList, sysUserGroupList));
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @SystemLog("用户删除")
    @Limiting
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public JsonResult delete(@NotNull(message = "用户id不能为空") Long id) {
        //线上演示使用
        TestUtil.isAdmin(id);
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysUser user = new SysUser().setId(id)
                .setDelFlag(true)
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());

        return JsonResult.success(userService.delete(user));
    }

    /**
     * 修改用户状态
     *
     * @param userForm
     * @return
     */
    @SystemLog("用户状态修改")
    @Limiting
    @PutMapping("/status")
    @RequiresPermissions("sys:user:status")
    public JsonResult status(@Validated @RequestBody UserStatusForm userForm) {
        //线上演示使用
        TestUtil.isAdmin(userForm.getId());
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysUser user = new SysUser()
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());
        BeanUtils.copyProperties(userForm, user);

        return JsonResult.success(userService.status(user));
    }

    /**
     * 修改自己的用户信息
     *
     * @param userForm
     * @return
     */
    @PutMapping("updateSelf")
    public JsonResult updateSelf(@RequestBody @Validated UserUpdateSelfForm userForm) {
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysUser user = new SysUser()
                .setId(admin.getId())
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());
        BeanUtils.copyProperties(userForm, user);

        return JsonResult.success(userService.updateSelf(user));
    }

    /**
     * 重置用户密码, 管理员给用户重置随机密码
     * @param id
     * @return
     */
    @SystemLog("重置用户密码")
    @Limiting(frequency = 1)
    @PutMapping("randomPwd")
    @RequiresPermissions("sys:user:update")
    public JsonResult randomPwd(@NotNull(message = "id不能为空") Long id) {
        //线上演示使用
        TestUtil.isAdmin(id);
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        //设置6位随机密码
        String password = RandomUtil.getRandom(6);
        SysUser user = new SysUser().setId(id).setPassword(password)
                .setModifier(admin.getId()).setModifyTime(System.currentTimeMillis());

        return JsonResult.success(userService.randomPwd(user));
    }

    /**
     * 修改密码，用户修改自己的密码
     * @param form
     * @return
     */
    @SystemLog("修改密码")
    @Limiting
    @PutMapping("resetPwd")
    public JsonResult resetPwd(@Validated @RequestBody ResetPwdForm form) {
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        //校验邮箱验证码
        String code = (String) redisTemplate.opsForHash().get(String.format(Constant.EMAIL_CODE, admin.getId()), Constant.CODE_KEY);
        Assert.isTrue(form.getCode().equals(code), "验证码错误");
        //copy bean
        SysUser user = new SysUser()
                .setId(admin.getId())
                .setModifier(admin.getId())
                .setModifyTime(System.currentTimeMillis());

        BeanUtils.copyProperties(form, user);
        return JsonResult.success(userService.resetPwd(user));
    }

    /**
     * copy用户关联的角色数据到SysUserRole实体类中
     * @param roleIds
     * @param userId
     * @return
     */
    private List<SysUserRole> copyUserRole(Set<Long> roleIds, Long userId){
        List<SysUserRole> sysUserRoleList = Lists.newArrayList();
        if (roleIds != null && !roleIds.isEmpty()) {
            roleIds.forEach(roleId -> {
                SysUserRole sysUserRole = new SysUserRole()
                        .setUid(userId)
                        .setRoleId(roleId);
                sysUserRoleList.add(sysUserRole);
            });
        }

        return sysUserRoleList;
    }

    /**
     * copy用户关联的用户组数据到SysUserGroup实体类中
     * @param groupIds
     * @param userId
     * @return
     */
    private List<SysUserGroup> copyUserGroup(Set<Long> groupIds, Long userId){
        List<SysUserGroup> sysUserGroupList = Lists.newArrayList();
        if (groupIds != null && !groupIds.isEmpty()) {
            groupIds.forEach(groupId -> {
                SysUserGroup sysUserGroup = new SysUserGroup()
                        .setUid(userId)
                        .setGroupId(groupId);
                sysUserGroupList.add(sysUserGroup);
            });
        }

        return sysUserGroupList;
    }
}
