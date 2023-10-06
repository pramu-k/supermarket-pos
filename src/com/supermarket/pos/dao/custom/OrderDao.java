package com.supermarket.pos.dao.custom;

import com.supermarket.pos.dao.CrudDao;

import java.sql.SQLException;

public interface OrderDao extends CrudDao {
    int getLastProductId() throws SQLException, ClassNotFoundException;
}
