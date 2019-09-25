package cn.inslee.adminback.model.from.sys;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author dean.lee
 * <p>
 */
@Data
public class ResAddFrom {

    @NotNull(message = "资源名称不能为空")
    @Length(min = 1, max = 12, message = "非法的资源名称")
    private String name;

    @NotNull(message = "资源类型不能为空")
    @Range(min = 1, max = 2, message = "非法的资源类型")
    private Integer type;

    private String url;

    private Long parentId;

    private String permChar;

    private String icon;

    private Integer seq;
}
