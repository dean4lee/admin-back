package cn.inslee.admin.model.dto.sys;

import cn.inslee.admin.model.domain.sys.SysGroup;
import cn.inslee.admin.model.domain.sys.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author dean.lee
 * <p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysGroupDTO extends SysGroup {

    private List<SysRole> roles;
}
