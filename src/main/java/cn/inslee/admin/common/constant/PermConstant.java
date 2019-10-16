package cn.inslee.admin.common.constant;

/**
 * @author dean.lee
 * <p>
 */
public class PermConstant {

    public static final String PERM = "cache:perm";

    /**
     * 存放资源的缓存名称
     */
    public static final String RES = PERM + ":res";

    /**
     * 存放角色的缓存名称
     */
    public static final String ROLE = PERM + ":role";

    /**
     * 存放用户所拥有资源或角色的字符
     */
    public static final String CHAR_KEY = "'char:'";

    /**
     * 存放用户所拥有资源和角色的id
     */
    public static final String ID_KEY = "'id:'";

    /**
     * 存放用户所拥有的菜单列表
     */
    public static final String MENU_KEY = "'menu:'";

    /**
     * 存放用户所拥有资源和角色的实体类对象
     */
    public static final String OBJ_KEY = "'obj:'";

}
