package cn.inslee.adminback.shiro.credential;

import cn.inslee.adminback.model.domain.sys.SysUser;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.util.DigestUtils;

/**
 * @author dean.lee
 * <p>
 * 自定义密码验证，使用md5+盐的方式
 */
public class Md5CredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String password = String.valueOf(usernamePasswordToken.getPassword());

        SysUser user = (SysUser) info.getPrincipals().getPrimaryPrincipal();

        String md5Password = DigestUtils.md5DigestAsHex((password + user.getSalt()).getBytes());

        return user.getPassword().equals(md5Password);
    }
}
