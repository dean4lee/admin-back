package cn.inslee.admin.model.query.log;

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
public class LoginLogQuery extends BaseQuery {

    private String username;

    private String ip;

    private Boolean status;
}
