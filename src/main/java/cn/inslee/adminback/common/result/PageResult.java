package cn.inslee.adminback.common.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;

/**
 * @author dean.lee
 * <p>
 * 分页返回结果集
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PageResult extends JsonResult {
    /**
     * 数据的总条数
     */
    private Long total;

    public PageResult(boolean status, int code, String msg, Object data, long total) {
        super(status, code, msg, data);
        this.total = total;
    }

    public static PageResult success(Collection<?> data, long total) {
        return new PageResult(SUCCESS, ResultCode.ok.code(), ResultCode.ok.msg(), data, total);
    }

}
