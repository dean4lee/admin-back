package cn.inslee.adminback.model.from.sys;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dean.lee
 * <p>
 */
@Data
public class UserStatusFrom {

    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "锁定状态不能为空")
    private Boolean status;
}
