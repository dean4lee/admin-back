package cn.inslee.admin.service.log;

import cn.inslee.admin.model.domain.log.SysLoginLog;
import cn.inslee.admin.model.query.log.LoginLogQuery;
import com.github.pagehelper.PageInfo;

/**
 * @author dean.lee
 * <p>
 */
public interface SysLoginLogService {
    /**
     * 添加登录日志
     *
     * @param loginLog
     */
    void add(SysLoginLog loginLog);

    /**
     * 查询用户登录记录列表
     * @param query
     * @return
     */
    PageInfo<SysLoginLog> list(LoginLogQuery query);
}
