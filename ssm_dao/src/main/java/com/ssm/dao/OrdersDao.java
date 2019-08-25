package com.ssm.dao;

import com.ssm.domain.Member;
import com.ssm.domain.Orders;
import com.ssm.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrdersDao {
    /**
     * 选择所有的订单信息
     * @return
     * @throws Exception
     */
    @Select("select * from orders")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderNum" ,column = "orderNum"),
            @Result(property = "orderTime" ,column = "orderTime"),
            @Result(property = "orderStatus" ,column = "orderStatus"),
            @Result(property = "peopleCount" ,column = "peopleCount"),
            @Result(property = "payType" ,column = "payType"),
            @Result(property = "orderDesc" ,column = "orderDesc"),
            @Result(property = "product" ,column = "productId",javaType = Product.class,one = @One(select = "com.ssm.dao.ProductDao.findById")),

    })
    public List<Orders> finddAll() throws Exception;

    /**
     * 根据id值查询订单详情,多表操作
     * @return
     */
    @Select("select * from orders where id=#{id}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderNum" ,column = "orderNum"),
            @Result(property = "orderTime" ,column = "orderTime"),
            @Result(property = "orderStatus" ,column = "orderStatus"),
            @Result(property = "peopleCount" ,column = "peopleCount"),
            @Result(property = "payType" ,column = "payType"),
            @Result(property = "orderDesc" ,column = "orderDesc"),
            @Result(property = "product" ,column = "productId",javaType = Product.class,one = @One(select = "com.ssm.dao.ProductDao.findById")),
            @Result(property = "member",column = "memberId",javaType = Member.class,one = @One(select = "com.ssm.dao.MemberDao.findById")),
            @Result(property = "travellers",column = "id",javaType = List.class,many = @Many(select = "com.ssm.dao.TravellerDao.findByOrdersId")),

    })
    public Orders findById(String id);
}
