package cn.inslee.adminback.common.util;

/**
 * @author dean.lee
 * <p>
 * 主键生成
 */
public class Key {
    /**
     * 最后一次生成key的时间
     */
    private static volatile long lastTimestamp = -1L;
    /**
     * 序列号最多生成1000个
     */
    private static final long MAX_SEQUENCE = 1000L;
    /**
     * 序列号初始参数
     */
    private static volatile long sequence = 0L;

    private Key() {
    }

    /**
     * 获取16位key，时间戳加序列号（线程同步的）
     * 1毫秒内默认最多生成1000个不同的key，可修改maxSequence更改最多生成的key的个数
     *
     * @return
     */
    public static long nextKey() {
        //获取当前时间
        long timestamp = System.currentTimeMillis();
        //避免程序运行中，回退系统时间
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("System time cannot be rollback!");
        }
        //如果当前时间=最后生成key的时间，则sequence+1，如果sequence>=maxSequence则等待下一个毫秒
        synchronized (Key.class) {
            if (lastTimestamp == timestamp) {
                sequence += 1;
                if (sequence >= MAX_SEQUENCE) {
                    timestamp = System.currentTimeMillis();
                    sequence = 0L;
                    while (timestamp <= lastTimestamp) {
                        timestamp = System.currentTimeMillis();
                    }
                }
            } else {
                sequence = 0L;
            }
            //更新最后生成key的时间为当前调用的时间
            lastTimestamp = timestamp;
        }
        return timestamp * MAX_SEQUENCE + sequence;
    }
}
