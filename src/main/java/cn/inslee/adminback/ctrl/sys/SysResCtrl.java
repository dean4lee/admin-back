package cn.inslee.adminback.ctrl.sys;

import cn.inslee.adminback.common.annotation.Limiting;
import cn.inslee.adminback.common.annotation.SystemLog;
import cn.inslee.adminback.common.constant.RedisConstant;
import cn.inslee.adminback.common.result.JsonResult;
import cn.inslee.adminback.common.util.Key;
import cn.inslee.adminback.model.domain.sys.SysRes;
import cn.inslee.adminback.model.domain.sys.SysRole;
import cn.inslee.adminback.model.domain.sys.SysUser;
import cn.inslee.adminback.model.dto.sys.SysResDTO;
import cn.inslee.adminback.model.from.sys.ResAddFrom;
import cn.inslee.adminback.model.from.sys.ResUpdateFrom;
import cn.inslee.adminback.service.sys.SysResService;
import cn.inslee.adminback.service.sys.SysRoleService;
import cn.inslee.adminback.shiro.util.ShiroUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate<String, Object> redisTemplate;
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
        //先从缓存中获取菜单列表
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        String permKey = RedisConstant.PERM_KEY + admin.getId();
        List<SysResDTO> result = (List<SysResDTO>) redisTemplate.opsForHash().get(permKey, RedisConstant.MENU_KEY);

        //如果缓存中没有，则从数据库中获取
        if(result == null || result.isEmpty()) {
            Set<SysRole> sysRoles = roleService.selectAllByUserId(admin.getId());
            List<Long> roleIds = Lists.newArrayList();
            sysRoles.forEach(role -> roleIds.add(role.getId()));
            result = resService.resources(roleIds);
            //存储到缓存中
            redisTemplate.opsForHash().put(permKey, RedisConstant.MENU_KEY, result);
        }
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
        String permKey = RedisConstant.PERM_KEY + admin.getId();
        Set<String> resChars = (Set<String>) redisTemplate.opsForHash().get(permKey, RedisConstant.RES_KEY);
        //如果缓存中没有，则从数据库中获取
        if(resChars == null || resChars.isEmpty()){
            Set<SysRole> sysRoles = roleService.selectAllByUserId(admin.getId());
            List<Long> roleIds = Lists.newArrayList();
            sysRoles.forEach(role -> roleIds.add(role.getId()));

            Set<SysRes> sysRes = resService.selectByRoleIds(roleIds);
            resChars = Sets.newHashSet();
            for (SysRes res : sysRes) {
                resChars.add(res.getPermChar());
            }
            redisTemplate.opsForHash().put(permKey, RedisConstant.RES_KEY, resChars);
        }
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
     * @return
     */
    @Limiting
    @GetMapping("/menuList")
    @RequiresPermissions(value = {"sys:res:add", "sys:res:update"}, logical = Logical.OR)
    public JsonResult menuList(){
        return JsonResult.success(resService.menuList());
    }

    /**
     * 资源添加
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
                .setCreationTime(new Date());
        BeanUtils.copyProperties(resFrom, res);

        return JsonResult.success(resService.add(res));
    }

    /**
     * 资源修改
     * @param resFrom
     * @return
     */
    @SystemLog("资源修改")
    @Limiting
    @PutMapping("/update")
    @RequiresPermissions("sys:res:update")
    public JsonResult update(@Validated @RequestBody ResUpdateFrom resFrom) {
        //copy 资源属性
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        SysRes res = new SysRes()
                .setModifier(admin.getId())
                .setModifyTime(new Date());
        BeanUtils.copyProperties(resFrom, res);

        return JsonResult.success(resService.update(res));
    }

    /**
     * 资源删除
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
                .setModifyTime(new Date());

        return JsonResult.success(resService.delete(res));
    }
}
