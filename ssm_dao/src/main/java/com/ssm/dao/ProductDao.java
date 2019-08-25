package com.ssm.dao;

import com.ssm.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductDao {

    @Select("select * from product")
    public List<Product> findAll() throws Exception;

    @Insert("insert into product(productNum,productName,cityName,departureTime,productDesc,productStatus,)" +
            "values(#{productNum},#{productName},#{cityName},#{departureTime},#{productDesc},#{productStatus})")
    void save(Product product);


    //根据id查询产品
    @Select("select * from product where id=#{id}")
    public Product findById(String id);

}
