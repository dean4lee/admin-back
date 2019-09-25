package cn.inslee.adminback.model.domain.sys;

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
public class SysRes implements Serializable {

    private static final long serialVersionUID = 127966756807406332L;

    @Id
    private Long id;

    private String name;

    private Integer type;

    private String url;

    private Long parentId;

    private String permChar;

    private String icon;

    private Integer seq;

    private Boolean delFlag;

    private Long creator;

    private Date creationTime;

    private Long modifier;

    private Date modifyTime;
}
