package cn.inslee.adminback.model.from.sys;

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
public class UserAddFrom {

    @NotNull(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{3,11}$", message = "非法的用户名")
    private String username;

    @Length(min = 1, max = 12, message = "非法的昵称")
    private String nickname;

    @Range(max = 2, message = "非法的性别")
    private Integer sex;

    @Range(max = 99, message = "非法的年龄")
    private Integer age;

    @NotNull(message = "邮箱不能为空")
    @Email(message = "非法的邮箱")
    private String email;

    //    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "非法的手机号")
    private String phone;

    @Length(max = 500, message = "备注过长")
    private String remark;

    private Set<Long> roleIds;

    private Set<Long> groupIds;
}
