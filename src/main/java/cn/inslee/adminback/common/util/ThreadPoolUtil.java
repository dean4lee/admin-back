package cn.inslee.adminback.common.util;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dean.lee
 */
public class ThreadPoolUtil {

    private ThreadPoolUtil() {
    }

    /**
     * 线程池的线程工厂
     *
     * @return
     */
    public static ThreadFactory getThreadFactory() {
        AtomicInteger count = new AtomicInteger(0);
        ThreadFactory factory = (r) -> {
            Thread thread = new Thread(r);
            thread.setName("thread-pool-" + count);
            count.addAndGet(1);
            return thread;
        };
        return factory;
    }

    /**
     * 线程池的异常处理
     *
     * @return
     */
    public static RejectedExecutionHandler getExecutionHandler() {
        RejectedExecutionHandler handler = (r, executor) -> {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        return handler;
    }
}
