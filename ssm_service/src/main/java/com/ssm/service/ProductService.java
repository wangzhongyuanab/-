package com.ssm.service;

import com.ssm.domain.Product;

import java.util.List;

public interface ProductService {

    public List<Product> findAll() throws  Exception;

    void save(Product product);
}
