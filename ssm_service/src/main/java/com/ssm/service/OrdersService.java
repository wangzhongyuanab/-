package com.ssm.service;

import com.ssm.domain.Orders;

import java.util.List;

public interface OrdersService {
    List<Orders> findAll(int page,int size) throws Exception;

    Orders findById(String id);
}
