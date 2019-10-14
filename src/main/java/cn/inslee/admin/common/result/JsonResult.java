package cn.inslee.admin.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dean.lee
 * <p>
 * json返回结果集
 */
@Data
public class JsonResult implements Serializable {

    private static final long serialVersionUID = -4339131942017375853L;

    public static final boolean SUCCESS = true;
    public static final boolean FAIL = false;

    /**
     * 返回的状态
     */
    private boolean status;
    /**
     * 返回的代码
     */
    private int code;
    /**
     * 返回的信息
     */
    private String msg;
    /**
     * 返回的数据
     */
    private Object data;

    protected JsonResult() {
    }

    public JsonResult(boolean status, int code, String msg, Object data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResult success() {
        return new JsonResult(SUCCESS, ResultCode.ok.code(), ResultCode.ok.msg(), null);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(SUCCESS, ResultCode.ok.code(), ResultCode.ok.msg(), data);
    }

    public static JsonResult success(String msg) {
        return new JsonResult(SUCCESS, ResultCode.ok.code(), msg, null);
    }

    /**
     * 默认的错误代码是400
     *
     * @param msg
     * @return
     */
    public static JsonResult fail(String msg) {
        return fail(ResultCode.BAD_REQUEST.code(), msg);
    }

    public static JsonResult fail(ResultCode resultCode) {
        return fail(resultCode.code(), resultCode.msg());
    }

    public static JsonResult fail(int code, String msg) {
        return new JsonResult(FAIL, code, msg, null);
    }
}
