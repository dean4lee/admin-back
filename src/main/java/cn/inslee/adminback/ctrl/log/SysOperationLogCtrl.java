package cn.inslee.adminback.ctrl.log;

import cn.inslee.adminback.common.annotation.Limiting;
import cn.inslee.adminback.common.result.JsonResult;
import cn.inslee.adminback.common.result.PageResult;
import cn.inslee.adminback.model.domain.log.SysOperationLog;
import cn.inslee.adminback.model.query.log.OperationLogQuery;
import cn.inslee.adminback.service.log.SysOperationLogService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dean.lee
 */
@RestController
@RequestMapping("/log/operation")
public class SysOperationLogCtrl {
	@Autowired
	private SysOperationLogService operationLogService;

	/**
     * 操作日志列表
	 * @param query
     * @return
     */
	@Limiting
	@GetMapping("/list")
	@RequiresPermissions("log:operation:list")
	public JsonResult list(@Validated OperationLogQuery query){
		PageInfo<SysOperationLog> pageInfo = operationLogService.list(query);
		return PageResult.success(pageInfo.getList(), pageInfo.getTotal());
	}
}
