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
public class GroupUpdateFrom {

    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "用户组名称不能为空")
    @Length(min = 1, max = 12, message = "非法的用户组名称")
    private String name;

    @Length(max = 500, message = "备注过长")
    private String remark;

    @NotEmpty(message = "未绑定角色")
    private Set<Long> roleIds;
}
