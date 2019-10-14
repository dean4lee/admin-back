package cn.inslee.admin.shiro.filter;

import cn.inslee.admin.common.result.JsonResult;
import cn.inslee.admin.common.result.ResultCode;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dean.lee
 * <p>
 * 重写shiro用户过滤器
 */
public class UserFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            return subject.getPrincipal() != null;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //session失效拒绝访问时设置返回状态码为403
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(ResultCode.FORBIDDEN.code());
        String result = JSONObject.toJSONString(JsonResult.fail(ResultCode.FORBIDDEN));
        res.getWriter().print(result);
        return false;
    }
}
