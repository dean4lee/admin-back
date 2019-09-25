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
public class SysGroupRole implements Serializable {

    private static final long serialVersionUID = -5666888690938201866L;

    private Long roleId;

    private Long groupId;
}
