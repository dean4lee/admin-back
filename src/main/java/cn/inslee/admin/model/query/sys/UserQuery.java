package cn.inslee.admin.model.query.sys;

import cn.inslee.admin.model.base.BaseQuery;
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

    private Long deptId;

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
