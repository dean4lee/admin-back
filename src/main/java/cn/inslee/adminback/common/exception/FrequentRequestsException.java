package cn.inslee.adminback.common.exception;

/**
 * 请求频繁异常
 *
 * @author dean.lee
 * <p>
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
