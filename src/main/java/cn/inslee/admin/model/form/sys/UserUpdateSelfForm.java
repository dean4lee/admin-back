package cn.inslee.admin.model.form.sys;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

/**
 * @author dean.lee
 * <p>
 */
@Data
public class UserUpdateSelfForm {

    @Length(min = 1, max = 12, message = "非法的昵称")
    private String nickname;

    @Range(max = 2, message = "非法的性别")
    private Integer sex;

    @Range(max = 99, message = "非法的年龄")
    private Integer age;

    @Email(message = "非法的邮箱")
    private String email;

    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "非法的手机号")
    private String phone;
}
