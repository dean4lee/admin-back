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
public class SysUserGroup implements Serializable {

    private static final long serialVersionUID = -8994349029192313827L;

    private Long uid;

    private Long groupId;
}
