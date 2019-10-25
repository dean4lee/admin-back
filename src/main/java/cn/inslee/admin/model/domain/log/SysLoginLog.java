package cn.inslee.admin.model.domain.log;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SysLoginLog implements Serializable {

    private static final long serialVersionUID = -6480253917865391485L;

    @Id
    private Long id;

    private String username;

    private String osName;

    private String deviceType;

    private String ip;

    private Long loginTime;

    private Boolean status;

    private String msg;
}