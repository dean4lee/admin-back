package cn.inslee.admin.service.log.impl;

import cn.inslee.admin.model.dao.log.SysLoginLogMapper;
import cn.inslee.admin.model.domain.log.SysLoginLog;
import cn.inslee.admin.model.query.log.LoginLogQuery;
import cn.inslee.admin.service.log.SysLoginLogService;
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
public class SysLoginLogServiceImpl implements SysLoginLogService {
    @Autowired
    private SysLoginLogMapper loginLogMapper;

    @Override
    public void add(SysLoginLog loginLog) {
        loginLogMapper.insertSelective(loginLog);
    }

    @Override
    public PageInfo<SysLoginLog> list(LoginLogQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize(), "id desc");

        Example example = new Example(SysLoginLog.class);
        example.createCriteria().andEqualTo(query);
        List<SysLoginLog> logs = loginLogMapper.selectByExample(example);
        PageInfo<SysLoginLog> pageInfo = new PageInfo<>(logs);
        return pageInfo;
    }
}
