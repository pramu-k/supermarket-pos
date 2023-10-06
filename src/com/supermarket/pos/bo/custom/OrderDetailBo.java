package com.supermarket.pos.bo.custom;

import com.supermarket.pos.bo.SuperBo;
import com.supermarket.pos.dto.OrderDetailDto;

import java.sql.SQLException;

public interface OrderDetailBo extends SuperBo {
    public boolean makeOrder(OrderDetailDto dto) throws SQLException;
}
