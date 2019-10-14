package cn.inslee.admin.model.from.sys;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author dean.lee
 * <p>
 */
@Data
public class UserLoginFrom {
    @NotNull(message = "用户名不能为空")
    @Pattern(regexp = "[a-zA-z][a-zA-Z0-9_]{3,11}", message = "非法的用户名")
    private String username;

    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "非法的密码")
    private String password;

    @NotNull(message = "记住状态不能为空")
    private Boolean rememberMe;

    @NotNull(message = "验证码不能为空")
    private String code;
}
