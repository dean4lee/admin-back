package cn.inslee.admin.model.domain.sys;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author dean.lee
 * <p>
 */
@Data
@Accessors(chain = true)
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 20102593654967106L;

    private Long uid;

    private Long roleId;
}
