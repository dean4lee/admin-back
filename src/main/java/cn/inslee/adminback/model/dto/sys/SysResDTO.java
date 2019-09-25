package cn.inslee.adminback.model.dto.sys;

import cn.inslee.adminback.model.domain.sys.SysRes;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author dean.lee
 * <p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysResDTO extends SysRes {

    private List<SysResDTO> children;

}
