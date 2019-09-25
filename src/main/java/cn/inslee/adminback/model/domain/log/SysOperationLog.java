package cn.inslee.adminback.model.domain.log;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SysOperationLog implements Serializable {

    private static final long serialVersionUID = 1619011569376246421L;

    @Id
    private Long id;

    private Long userId;

    private String username;

    private String ip;

    private String method;

    private Long execTime;

    private String value;

    private Date createTime;

    private Boolean status;

    private String url;

    private String params;

    private String result;

    private String exception;
}