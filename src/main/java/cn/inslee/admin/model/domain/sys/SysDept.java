package cn.inslee.admin.model.domain.sys;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SysDept implements Serializable {

    private static final long serialVersionUID = 5974147914844927407L;

    @Id
    private Long id;

    private String name;

    private Long parentId;

    private Integer seq;

    private String remark;

    private Boolean delFlag;

    private Long creator;

    private Date creationTime;

    private Long modifier;

    private Date modifyTime;

}
