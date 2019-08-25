package com.ssm.service.Impl;

import com.github.pagehelper.PageHelper;
import com.ssm.dao.OrdersDao;
import com.ssm.domain.Orders;
import com.ssm.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements com.ssm.service.OrdersService {

    @Autowired
    private  OrdersDao ordersDao;

    @Override
    public List<Orders> findAll(int page,int size) throws Exception {
        //参数pageNum是页码值，pageSize是每页显示条数。
        PageHelper.startPage(page,size);
        return ordersDao.finddAll();
    }

    @Override
    public Orders findById(String id) {
        Orders orders=ordersDao.findById(id);
        return orders;
    }
}
