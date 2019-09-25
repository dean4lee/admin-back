package cn.inslee.adminback.common.exception;

/**
 * @author dean.lee
 * <p>
 * 请求频繁异常
 */
public class FrequentRequestsException extends RuntimeException {
    public FrequentRequestsException() {
        super();
    }

    public FrequentRequestsException(String message) {
        super(message);
    }

    public FrequentRequestsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrequentRequestsException(Throwable cause) {
        super(cause);
    }

    protected FrequentRequestsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
