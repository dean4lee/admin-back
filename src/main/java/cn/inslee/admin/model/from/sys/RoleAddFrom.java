package cn.inslee.admin.model.from.sys;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author dean.lee
 * <p>
 */
@Data
public class RoleAddFrom {

    @NotNull(message = "角色名不能为空")
    @Length(min = 1, max = 12, message = "非法的角色名")
    private String name;

    @Length(min = 1, max = 20, message = "非法的角色字符")
    private String roleChar;

    @Length(max = 500, message = "备注过长")
    private String remark;

    @NotEmpty(message = "未绑定资源")
    private Set<Long> resIds;
}
