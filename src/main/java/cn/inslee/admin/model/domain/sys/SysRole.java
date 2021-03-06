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
public class SysRole implements Serializable {

    private static final long serialVersionUID = 5314483727700770700L;

    @Id
    private Long id;

    private String name;

    private String roleChar;

    private String remark;

    private Boolean delFlag;

    private Long creator;

    private Long creationTime;

    private Long modifier;

    private Long modifyTime;
}
