package cn.inslee.admin.shiro.credential;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.DigestUtils;

/**
 * @author dean.lee
 * <p>
 * 自定义密码验证，使用md5+盐的方式
 */
public class Md5CredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //获取登录from表单的密码
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String password = String.valueOf(usernamePasswordToken.getPassword());

        //获取shiro认证的密码和盐
        SimpleAuthenticationInfo simpleInfo = (SimpleAuthenticationInfo) info;
        String credentials = (String) simpleInfo.getCredentials();
        ByteSource credentialsSalt = simpleInfo.getCredentialsSalt();
        String salt = Base64.decodeToString(credentialsSalt.toString());

        String md5Password = DigestUtils.md5DigestAsHex((password + salt).getBytes());

        return credentials.equals(md5Password);
    }
}
