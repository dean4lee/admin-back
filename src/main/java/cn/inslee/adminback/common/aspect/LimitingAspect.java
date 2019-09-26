package cn.inslee.adminback.common.aspect;

import cn.inslee.adminback.common.annotation.Limiting;
import cn.inslee.adminback.common.exception.FrequentRequestsException;
import cn.inslee.adminback.common.util.WebUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * ip防刷api功能实现。
 * 该功能使用redis作为存储，方便在集群中使用。
 * 如果是单项目部署，可以将redis换成本地缓存。
 *
 * @author dean.lee
 * <p>
 */
@Aspect
@Component
public class LimitingAspect {
    private static final String LIMITING_KEY = "limiting:%s:%s";
    private static final String LIMITING_BEGINTIME = "beginTime";
    private static final String LIMITING_EXFREQUENCY = "exFrequency";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(limiting)")
    public void pointcut(Limiting limiting) {
    }

    @Around("pointcut(limiting)")
    public Object around(ProceedingJoinPoint pjp, Limiting limiting) throws Throwable {
        //获取请求的ip和方法
        String ipAddress = WebUtil.getIpAddress();
        String methodName = pjp.getSignature().toLongString();

        //获取方法的访问周期和频率
        long cycle = limiting.cycle();
        int frequency = limiting.frequency();
        long currentTime = System.currentTimeMillis();

        //获取redis中周期内第一次访问方法的时间和执行的次数
        Long beginTime = (Long) redisTemplate.opsForHash().get(String.format(LIMITING_KEY, ipAddress, methodName), LIMITING_BEGINTIME);
        Integer exFrequency = (Integer) redisTemplate.opsForHash().get(String.format(LIMITING_KEY, ipAddress, methodName), LIMITING_EXFREQUENCY);

        beginTime = beginTime == null ? 0L : beginTime;
        exFrequency = exFrequency == null ? 0 : exFrequency;

        //如果当前时间减去周期内第一次访问方法的时间大于周期时间，则正常访问
        //并将周期内第一次访问方法的时间和执行次数初始化
        if (currentTime - beginTime > cycle) {
            redisTemplate.opsForHash().put(String.format(LIMITING_KEY, ipAddress, methodName), LIMITING_BEGINTIME, currentTime);
            redisTemplate.opsForHash().put(String.format(LIMITING_KEY, ipAddress, methodName), LIMITING_EXFREQUENCY, 1);
            redisTemplate.expire(String.format(LIMITING_KEY, ipAddress, methodName), limiting.expireTime(), TimeUnit.SECONDS);
            return pjp.proceed();
        } else {
            //如果在周期时间内，执行次数小于频率，则正常访问
            //并将执行次数加一
            if (exFrequency < frequency) {
                redisTemplate.opsForHash().put(String.format(LIMITING_KEY, ipAddress, methodName), LIMITING_EXFREQUENCY, exFrequency + 1);
                redisTemplate.expire(String.format(LIMITING_KEY, ipAddress, methodName), limiting.expireTime(), TimeUnit.SECONDS);
                return pjp.proceed();
            } else {
                //否则抛出访问频繁异常
                throw new FrequentRequestsException(limiting.message());
            }
        }
    }
}
