package cn.inslee.admin.model.query.log;

import cn.inslee.admin.model.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author dean.lee
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OperationLogQuery extends BaseQuery {

	private String username;
	private String ip;
	private String value;
	private Boolean status;
}
