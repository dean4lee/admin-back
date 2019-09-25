package cn.inslee.adminback.service.log;

import cn.inslee.adminback.model.domain.log.SysOperationLog;
import cn.inslee.adminback.model.query.log.OperationLogQuery;
import com.github.pagehelper.PageInfo;

/**
 * @author dean.lee
 * <p>
 */
public interface SysOperationLogService {
    /**
     * 添加操作日志
     * @param log
     */
    void add(SysOperationLog log);

    /**
     * 查询操作日志列表
     * @param query
     * @return
     */
    PageInfo<SysOperationLog> list(OperationLogQuery query);
}
