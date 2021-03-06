package cn.inslee.admin.model.form.sys;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author dean.lee
 * <p>
 */
@Data
public class DeptAddForm {

    @NotNull(message = "部门名称不能为空")
    @Length(min = 1, max = 12, message = "非法的部门名称")
    private String name;

    @NotNull(message = "父级部门不能为空")
    private Long pid;

    @NotNull(message = "排序不能为空")
    private Integer seq;

    @Length(max = 500, message = "备注过长")
    private String remark;
}
