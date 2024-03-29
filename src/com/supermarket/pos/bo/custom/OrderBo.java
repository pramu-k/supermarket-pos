package com.supermarket.pos.bo.custom;

import com.supermarket.pos.bo.SuperBo;
import com.supermarket.pos.dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderBo extends SuperBo {
    public List<OrderDto> getAllOrders() throws SQLException, ClassNotFoundException;
}
