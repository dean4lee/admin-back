package cn.inslee.admin.model.from.sys;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

/**
 * @author dean.lee
 * <p>
 */
@Data
public class UserUpdateFrom {

    @NotNull(message = "id不能为空")
    private Long id;

    @Length(min = 1, max = 20, message = "非法的昵称")
    private String nickname;

    @Range(max = 2, message = "非法的性别")
    private Integer sex;

    @Range(max = 99, message = "非法的年龄")
    private Integer age;

    @Email(message = "非法的邮箱")
    private String email;

    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "非法的手机号")
    private String phone;

    @Length(max = 500, message = "备注过长")
    private String remark;

    @NotNull(message = "部门不能为空")
    private Long deptId;

    private Set<Long> roleIds;

    private Set<Long> groupIds;
}
