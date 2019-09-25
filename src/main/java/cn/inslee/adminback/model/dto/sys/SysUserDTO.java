package cn.inslee.adminback.model.dto.sys;

import cn.inslee.adminback.model.domain.sys.SysGroup;
import cn.inslee.adminback.model.domain.sys.SysRole;
import cn.inslee.adminback.model.domain.sys.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author dean.lee
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUserDTO extends SysUser {

	private List<SysRole> roles;

	private List<SysGroup> groups;

}
