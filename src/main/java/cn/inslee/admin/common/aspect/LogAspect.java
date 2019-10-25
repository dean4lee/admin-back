package cn.inslee.admin.common.aspect;

import cn.inslee.admin.common.annotation.SystemLog;
import cn.inslee.admin.common.util.Key;
import cn.inslee.admin.common.util.WebUtil;
import cn.inslee.admin.model.domain.log.SysOperationLog;
import cn.inslee.admin.model.domain.sys.SysUser;
import cn.inslee.admin.service.log.SysOperationLogService;
import cn.inslee.admin.shiro.util.ShiroUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 *
 * 操作日志记录
 *
 * @author raines.lee
 * <p>
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    @Autowired
    private SysOperationLogService operationLogService;

    @Pointcut("@annotation(syslog)")
    public void pointcut(SystemLog syslog) {
    }

    @Around("pointcut(sysLog)")
    public Object around(ProceedingJoinPoint pjp, SystemLog sysLog) throws Throwable {
        long beginTime = System.currentTimeMillis();

        Signature sig = pjp.getSignature();
        //获取执行的方法名
        String method = pjp.getTarget().getClass().getName() + "." + sig.getName();

        //获取日志信息
        String value = sysLog.value();

        HttpServletRequest request = WebUtil.getRequest();
        //获取url
        String url = request.getRequestURL().toString();

        //获取用户
        SysUser user = ShiroUtil.getPrincipal(SysUser.class);

        //获取ip
        String ip = WebUtil.getIpAddress();

        //获取参数
        Object[] args = pjp.getArgs();
        StringBuffer bufferParams = new StringBuffer();
        for (Object o : args) {
            String jsonStr = JSONObject.toJSONString(o);
            bufferParams.append("," + jsonStr);
        }
        String params = bufferParams.substring(1);
        log.debug("方法参数：" + method + " : " + params);

        SysOperationLog log = new SysOperationLog();
        log.setId(Key.nextKey())
                .setUid(user.getId())
                .setUsername(user.getUsername())
                .setIp(ip)
                .setUrl(url)
                .setMethod(method)
                .setParams(params)
                .setValue(value)
                .setCreationTime(System.currentTimeMillis());

        Object result;
        try {
            result = pjp.proceed();
            add(log, beginTime, JSONObject.toJSONString(result), null, true);
        } catch (Throwable e) {
            add(log, beginTime, null, e.getMessage(), false);
            throw e;
        }
        return result;
    }

    private void add(SysOperationLog log, Long beginTime, String result, String exception, boolean status) {

        long currentTime = System.currentTimeMillis();

        log.setExecTime(currentTime - beginTime)
                .setResult(result).setStatus(status)
                .setException(exception);

        operationLogService.add(log);
    }
}
