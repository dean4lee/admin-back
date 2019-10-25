package cn.inslee.admin.model.domain.sys;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author dean.lee
 * <p>
 */
@Data
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = -5307591870458088890L;

    @Id
    private Long id;

    private String username;

    private String password;

    private String salt;

    private Long deptId;

    private String nickname;

    private String phone;

    private String email;

    private Integer age;

    private Integer sex;

    private String remark;

    private Boolean status;

    private Boolean delFlag;

    private Long creator;

    private Long creationTime;

    private Long modifier;

    private Long modifyTime;
}
