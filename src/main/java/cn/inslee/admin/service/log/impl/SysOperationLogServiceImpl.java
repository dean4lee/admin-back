package cn.inslee.admin.service.log.impl;

import cn.inslee.admin.model.dao.log.SysOperationLogMapper;
import cn.inslee.admin.model.domain.log.SysOperationLog;
import cn.inslee.admin.model.query.log.OperationLogQuery;
import cn.inslee.admin.service.log.SysOperationLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author dean.lee
 * <p>
 */
@Service
public class SysOperationLogServiceImpl implements SysOperationLogService {
    @Autowired
    private SysOperationLogMapper operationLogMapper;

    @Override
    public void add(SysOperationLog log) {
        operationLogMapper.insertSelective(log);
    }

    @Override
    public PageInfo<SysOperationLog> list(OperationLogQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize(), "id desc");

        Example example = new Example(SysOperationLog.class);
        example.createCriteria().andEqualTo(query);
        List<SysOperationLog> logs = operationLogMapper.selectByExample(example);
        PageInfo<SysOperationLog> pageInfo = new PageInfo<>(logs);
        return pageInfo;
    }
}
