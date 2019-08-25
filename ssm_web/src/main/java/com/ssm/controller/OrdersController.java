package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.domain.Orders;
import com.ssm.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

//    //查询全部订单未分页
//    @RequestMapping("/findAll.do")
//    public ModelAndView findAll() throws Exception {
//        List<Orders> orders = ordersService.findAll();
//        ModelAndView mv=new ModelAndView();
//        mv.addObject("ordersList",orders);
//        mv.setViewName("orders-list");
//        return mv;
//    }

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,
                                @RequestParam(name = "size",required = true,defaultValue = "4") Integer size) throws Exception {
        ModelAndView mv=new ModelAndView();
        List<Orders> orders = ordersService.findAll(page,size);
        //PageInfo就是一个分页Bean
        PageInfo pageInfo=new PageInfo(orders);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("orders-list");
        return mv;
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id",required = true) String id){
        Orders orders=ordersService.findById(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
        return mv;
    }
}
