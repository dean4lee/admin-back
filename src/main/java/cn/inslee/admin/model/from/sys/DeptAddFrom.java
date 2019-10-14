package cn.inslee.admin.model.from.sys;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author dean.lee
 * <p>
 */
@Data
public class DeptAddFrom {

    @NotNull(message = "部门名称不能为空")
    @Length(min = 1, max = 12, message = "非法的部门名称")
    private String name;

    private Long parentId;

    private Integer seq;

    @Length(max = 500, message = "备注过长")
    private String remark;
}
