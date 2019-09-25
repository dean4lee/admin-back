package cn.inslee.adminback.model.domain.sys;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author dean.lee
 * <p>
 */
@Data
@Accessors(chain = true)
public class SysRoleRes implements Serializable {

    private static final long serialVersionUID = -5452434413121143989L;

    private Long roleId;

    private Long resId;
}
