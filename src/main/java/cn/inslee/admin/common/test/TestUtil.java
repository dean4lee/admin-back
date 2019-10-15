package cn.inslee.admin.common.test;

/**
 * @author dean.lee
 * <p>
 */
public class TestUtil {
    public static Long[] resIds = {
            1568792652982000L,
            1568792740183000L,
            1568792818721000L,
            1568792934828000L,
            1568793034295000L,
            1568793053657000L,
            1568793417548000L,
            1568793473396000L,
            1568862344386000L,
            1568876389002000L,
            1568881517753000L,
            1568881776302000L,
            1568881786217000L,
            1568942634425000L,
            1568944480258000L,
            1568968597143000L,
            1568968652358000L,
            1569025948597000L,
            1569031805602000L,
            1569033417472000L,
            1569033453895000L,
            1569035253007000L,
            1569036597841001L,
            1569036746501000L,
            1569036891266001L,
            1569039944773001L,
            1569039988688001L,
            1571019039453001L,
            1571019065711001L,
            1571021728820001L,
            1571021754612001L,
            1571021786935001L,
            1571021817630001L,
            1571021835594001L
    };

    public static void isAdmin(Long id) {
        if(id.equals(1L)){
            throw new IllegalArgumentException("演示项目，无法操作admin");
        }
    }

    public static void isAdminRole(Long id){
        if(id.equals(1568772939750000L)){
            throw new IllegalArgumentException("演示项目，无法操作admin关联的角色");
        }
    }

    public static void isAdminRes(Long id){
        for (Long resId : resIds) {
            if(id.equals(resId)){
                throw new IllegalArgumentException("演示项目，无法操作admin关联的资源");
            }
        }
    }
}
