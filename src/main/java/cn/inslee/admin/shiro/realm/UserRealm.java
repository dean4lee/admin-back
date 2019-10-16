package cn.inslee.admin.shiro.realm;

import cn.inslee.admin.model.domain.sys.SysUser;
import cn.inslee.admin.service.sys.SysResService;
import cn.inslee.admin.service.sys.SysRoleService;
import cn.inslee.admin.service.sys.SysUserService;
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
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

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

    /**
     * 执行授权逻辑
     *
     * 使用redis缓存存储用户的角色字符串和权限字符串，
     * 如果修改用户、删除用户、锁定用户、修改角色、删除角色、修改用户组、删除用户组、修改资源、删除资源，
     * 需要从缓存中删除所有的角色字符串和权限字符串
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();

        Set<String> roleChars = roleService.selectRoleCharByUserId(user.getId());

        Set<String> resChars = resService.selectPremCharByUserId(user.getId());

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

        String password = user.getPassword();
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        user.setPassword(null)
                .setSalt(null)
                .setRemark(null)
                .setStatus(null)
                .setDelFlag(null)
                .setDeptId(null);
        return new SimpleAuthenticationInfo(user, password, salt, getName());
    }

}
