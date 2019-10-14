package cn.inslee.admin.model.from.sys;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author dean.lee
 * <p>
 */
@Data
public class ResUpdateFrom {

    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "资源名称不能为空")
    @Length(min = 1, max = 12, message = "非法的资源名称")
    private String name;

    private String url;

    private Long parentId;

    private String permChar;

    private String icon;

    private Integer seq;
}
