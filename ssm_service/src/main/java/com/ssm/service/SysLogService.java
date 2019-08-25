package com.ssm.service;

import com.ssm.domain.SysLog;

import java.util.List;

public interface SysLogService {


    public void save(SysLog sysLog);

    public List<SysLog> findAll();
}
