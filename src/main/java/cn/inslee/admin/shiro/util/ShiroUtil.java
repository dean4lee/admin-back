package cn.inslee.admin.shiro.util;

import org.apache.shiro.SecurityUtils;

/**
 * @author dean.lee
 */
public class ShiroUtil {

    public static <T> T getPrincipal(Class<T> clazz) {
        return (T) SecurityUtils.getSubject().getPrincipal();
    }
}
