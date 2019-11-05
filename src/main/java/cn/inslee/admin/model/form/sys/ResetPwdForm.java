package cn.inslee.admin.model.form.sys;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author dean.lee
 * <p>
 */
@Data
public class ResetPwdForm {

    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "非法的密码")
    private String password;

    @NotNull(message = "验证码不能为空")
    private String code;
}
