package cn.inslee.admin.ctrl.sys;

import cn.inslee.admin.common.annotation.Limiting;
import cn.inslee.admin.common.constant.Constant;
import cn.inslee.admin.common.result.JsonResult;
import cn.inslee.admin.common.util.Key;
import cn.inslee.admin.common.util.RandomUtil;
import cn.inslee.admin.common.util.WebUtil;
import cn.inslee.admin.model.domain.log.SysLoginLog;
import cn.inslee.admin.model.domain.sys.SysUser;
import cn.inslee.admin.model.form.sys.UserLoginForm;
import cn.inslee.admin.service.log.SysLoginLogService;
import cn.inslee.admin.shiro.util.ShiroUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author dean.lee
 * <p>
 */
@RestController
@Slf4j
public class LoginCtrl {
    @Autowired
    private SysLoginLogService loginLogService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Environment env;

    @PostMapping("/login")
    @Limiting(frequency = 5, cycle = 5 * 60 * 1000, expireTime = 30 * 60, message = "登录过于频繁，30分钟后解除限制")
    public JsonResult login(@RequestBody @Validated UserLoginForm user) {
        String code = "code";
        //记录错误信息
        String msg = null;
        if (!user.getCode().equals(WebUtil.getSessionAttribute(code))) {
            msg = "验证码错误";
            this.addLoginlog(user.getUsername(), false, msg);
            throw new IllegalArgumentException(msg);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(), user.getRememberMe());

        //记录登陆成功失败的状态
        boolean status = false;
        try {
            //登陆执行UserRealm的认证逻辑
            subject.login(token);
            status = true;

            SysUser principal = ShiroUtil.getPrincipal(SysUser.class);
            principal.setPassword(null)
                    .setStatus(null)
                    .setDelFlag(null)
                    .setSalt(null)
                    .setRemark(null);
            return JsonResult.success(principal);
        } catch (UnknownAccountException e) {
            msg = "账号错误";
            throw new IllegalArgumentException("账号或密码错误");
        } catch (CredentialsException e) {
            msg = "密码错误";
            throw new IllegalArgumentException("账号或密码错误");
        } catch (LockedAccountException e) {
            msg = "账号锁定";
            throw new IllegalArgumentException(msg);
        } catch (Exception e) {
            msg = "服务器异常";
            throw e;
        } finally {
            this.addLoginlog(user.getUsername(), status, msg);
        }
    }

    /**
     * 获取验证码
     */
    @Limiting
    @GetMapping("/code")
    public void getImageCode(HttpServletRequest request, HttpServletResponse response) {
        //生成算术验证码
        String createText = defaultKaptcha.createText();
        //将运算和结果分离
        String codeStr = createText.substring(0, createText.indexOf("?") + 1);
        String codeResult = createText.substring(createText.indexOf("?") + 1);
        //将结果存储到session
        request.getSession().setAttribute("code", codeResult);
        //生成图片
        BufferedImage image = defaultKaptcha.createImage(codeStr);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    @Limiting(frequency = 2)
    @GetMapping("/email/code")
    public void getEmailCode(HttpServletRequest request, HttpServletResponse response) {
        SysUser admin = ShiroUtil.getPrincipal(SysUser.class);
        String random = RandomUtil.getRandomNum(4);
        String content = "您的验证码：" + random + "\n 有效期为5分钟";

        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setFrom(env.getProperty("spring.mail.username"));
        mailMsg.setTo(admin.getEmail());
        mailMsg.setSubject(Constant.EMAIL_CODE_MSG);
        mailMsg.setText(content);
        mailSender.send(mailMsg);

        //验证码保存到redis中，有效时间为5分钟
        redisTemplate.opsForHash().put(String.format(Constant.EMAIL_CODE, admin.getId()), Constant.CODE_KEY, random);
        redisTemplate.expire(String.format(Constant.EMAIL_CODE, admin.getId()), 5, TimeUnit.MINUTES);
    }


    private void addLoginlog(String username, boolean status, String msg){
        HttpServletRequest request = WebUtil.getRequest();
        //获取设备型号（win，iphone）及类型
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        OperatingSystem os = userAgent.getOperatingSystem();
        String osName = os.getName();
        //设备类型（pc、手机）
        String deviceType = os.getDeviceType().getName();

        //获取ip
        String ip = WebUtil.getIpAddress();

        SysLoginLog log = new SysLoginLog()
                .setId(Key.nextKey())
                .setUsername(username)
                .setOsName(osName).setDeviceType(deviceType)
                .setIp(ip).setLoginTime(System.currentTimeMillis())
                .setStatus(status).setMsg(msg);
        loginLogService.add(log);
    }
}
