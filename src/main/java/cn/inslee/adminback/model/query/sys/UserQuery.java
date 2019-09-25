package cn.inslee.adminback.model.query.sys;

import cn.inslee.adminback.model.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author dean.lee
 * <p>
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends BaseQuery {

    /**
     * 用户锁定状态
     */
    private Boolean status;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 用户组id
     */
    private Long groupId;

    /**
     * 用户名
     */
    private String username;
}
