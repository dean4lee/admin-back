package cn.inslee.adminback.shiro.realm;

import cn.inslee.adminback.common.constant.RedisConstant;
import cn.inslee.adminback.model.domain.sys.SysRes;
import cn.inslee.adminback.model.domain.sys.SysRole;
import cn.inslee.adminback.model.domain.sys.SysUser;
import cn.inslee.adminback.service.sys.SysResService;
import cn.inslee.adminback.service.sys.SysRoleService;
import cn.inslee.adminback.service.sys.SysUserService;
import com.google.common.collect.Sets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author dean.lee
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysResService resService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 执行授权逻辑
     *
     * 使用redis缓存存储用户的角色字符串和权限字符串，
     * 如果修改用户、删除用户、锁定用户，需要将这个用户的权限从缓存中删除
     * 如果修改角色、删除角色、修改用户组、删除用户组、修改资源、删除资源，需要从缓存中删除所有的角色字符串和权限字符串
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();

        //获取用户所拥有的角色
        //功能中暂时没有使用角色字符
        String permKey = RedisConstant.PERM_KEY + user.getId();
        Set<String> roleChars = (Set<String>) redisTemplate.opsForHash().get(permKey, RedisConstant.ROLE_KEY);
        if (roleChars == null) {
            roleChars = Sets.newHashSet();
            Set<SysRole> roles = roleService.selectAllByUserId(user.getId());
            for (SysRole role : roles) {
                roleChars.add(role.getRoleChar());
            }
            redisTemplate.opsForHash().put(permKey, RedisConstant.ROLE_KEY, roleChars);
        }

        //获取用户所拥有的资源
        Set<String> resChars = (Set<String>) redisTemplate.opsForHash().get(permKey, RedisConstant.RES_KEY);
        if (resChars == null) {
            resChars = Sets.newHashSet();
            List<Long> roleIds = new ArrayList<>();
            Set<SysRole> roles = roleService.selectAllByUserId(user.getId());
            if(roles != null && !roles.isEmpty()) {
                roles.forEach(role -> roleIds.add(role.getId()));

                Set<SysRes> ress = resService.selectByRoleIds(roleIds);
                for (SysRes res : ress) {
                    resChars.add(res.getPermChar());
                }
                redisTemplate.opsForHash().put(permKey, RedisConstant.RES_KEY, resChars);
            }
        }

        //将用户所拥有的的角色字符和权限字符添加到SimpleAuthorizationInfo中
        info.addRoles(roleChars);
        info.addStringPermissions(resChars);

        return info;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();

        SysUser user = userService.selectByUsername(username);

        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getStatus()) {
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

}
