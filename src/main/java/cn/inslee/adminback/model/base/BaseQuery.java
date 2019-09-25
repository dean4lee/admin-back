package cn.inslee.adminback.model.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author dean.lee
 * <p>
 * 查询参数基类
 */
@Data
@Accessors(chain = true)
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = -7793788641727288913L;

    @Min(value = 1, message = "非法的分页条件")
    private int pageNum;

    @Range(min = 1, max = 100, message = "非法的分页条件")
    private int pageSize;

    private String keyword;

}
