package cn.inslee.adminback.model.query.log;

import cn.inslee.adminback.model.base.BaseQuery;
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
