package com.ssm.service.Impl;

import com.ssm.dao.SysLogDao;
import com.ssm.domain.SysLog;
import com.ssm.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public void save(SysLog sysLog) {
        sysLogDao.save(sysLog);
    }

    @Override
    public List<SysLog> findAll() {
        List<SysLog> sysLogs = sysLogDao.findAll();
        return sysLogs;
    }
}
