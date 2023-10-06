package com.supermarket.pos.bo.custom.impl;

import com.supermarket.pos.bo.custom.OrderBo;
import com.supermarket.pos.dao.DaoFactory;
import com.supermarket.pos.dao.custom.OrderDao;
import com.supermarket.pos.dto.OrderDto;
import com.supermarket.pos.entity.OrderDetail;
import com.supermarket.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);

    @Override
    public List<OrderDto> getAllOrders() throws SQLException, ClassNotFoundException {
        List<OrderDto> orderDtos=new ArrayList<>();
        List<OrderDetail> newList=orderDao.findAll();
        for(OrderDetail element:newList){
            orderDtos.add(new OrderDto(
                    element.getCode(),
                    element.getIssuedDate(),
                    element.getTotalCost(),
                    element.getDiscount(),
                    element.getCustomerEmail(),
                    element.getOperatorEmail()));
        }
        return orderDtos;
    }
}
