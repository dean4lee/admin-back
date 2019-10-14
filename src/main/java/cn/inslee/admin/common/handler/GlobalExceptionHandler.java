package cn.inslee.admin.common.handler;

import cn.inslee.admin.common.exception.FrequentRequestsException;
import cn.inslee.admin.common.result.JsonResult;
import cn.inslee.admin.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author dean.lee
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 非法参数捕获
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.debug("", e);
        return JsonResult.fail(e.getMessage());
    }

    /**
     * 请求频繁捕获
     *
     * @param e
     * @return
     */
    @ExceptionHandler(FrequentRequestsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult illegalArgumentExceptionHandler(FrequentRequestsException e) {
        log.debug("", e);
        return JsonResult.fail(e.getMessage());
    }

    /**
     * 参数类型异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        log.debug("", e);
        return JsonResult.fail("参数类型异常");
    }

    /**
     * 校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.debug("", e);
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return JsonResult.fail(msg);
    }

    /**
     * 校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult bindExceptionHandler(BindException e) {
        log.debug("", e);
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return JsonResult.fail(msg);
    }

    /**
     * shiro未授权异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public JsonResult authorizationExceptionHandler(AuthorizationException e) {
        log.debug("", e);
        return JsonResult.fail(ResultCode.UNAUTHORIZED);
    }

    /**
     * 全局异常捕获
     *
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public JsonResult globalHandler(Throwable e, HttpServletResponse response) {
        JsonResult result;
        if (e instanceof HttpRequestMethodNotSupportedException) {
            log.debug("", e);
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            result = JsonResult.fail(ResultCode.METHOD_NOT_ALLOWED);
        } else if (e instanceof HttpMessageNotReadableException) {
            log.debug("", e);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            result = JsonResult.fail(ResultCode.BAD_REQUEST);
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            log.debug("", e);
            response.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
            result = JsonResult.fail(ResultCode.UNSUPPORTED_MEDIA_TYPE);
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result = JsonResult.fail(ResultCode.INTERNAL_SERVER_ERROR);
            log.error(e.getMessage(), e);
        }

        return result;
    }
}
