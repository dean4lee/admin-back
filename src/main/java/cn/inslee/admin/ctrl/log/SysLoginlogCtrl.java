package cn.inslee.admin.ctrl.log;

import cn.inslee.admin.common.annotation.Limiting;
import cn.inslee.admin.common.result.JsonResult;
import cn.inslee.admin.common.result.PageResult;
import cn.inslee.admin.model.domain.log.SysLoginLog;
import cn.inslee.admin.model.query.log.LoginLogQuery;
import cn.inslee.admin.service.log.SysLoginLogService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dean.lee
 * <p>
 */
@RestController
@RequestMapping("/log/login")
public class SysLoginlogCtrl {
    @Autowired
    private SysLoginLogService loginLogService;

    @Limiting
    @GetMapping("/list")
    @RequiresPermissions("log:login:list")
    public JsonResult list(@Validated LoginLogQuery query){
        PageInfo<SysLoginLog> pageInfo = loginLogService.list(query);
        return PageResult.success(pageInfo.getList(), pageInfo.getTotal());
    }
}
