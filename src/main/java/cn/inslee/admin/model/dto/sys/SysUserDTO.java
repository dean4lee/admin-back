package cn.inslee.admin.model.dto.sys;

import cn.inslee.admin.model.domain.sys.SysGroup;
import cn.inslee.admin.model.domain.sys.SysRole;
import cn.inslee.admin.model.domain.sys.SysUser;
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

	private String deptName;

}
