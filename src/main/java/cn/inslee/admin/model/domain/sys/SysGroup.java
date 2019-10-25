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
public class SysGroup implements Serializable {

    private static final long serialVersionUID = 1950851973507568987L;

    @Id
    private Long id;

    private String name;

    private String remark;

    private Boolean delFlag;

    private Long creator;

    private Long creationTime;

    private Long modifier;

    private Long modifyTime;
}
