package com.supermarket.pos.dao.custom.impl;

import com.supermarket.pos.dao.CrudUtil;
import com.supermarket.pos.dao.custom.OrderDao;
import com.supermarket.pos.entity.SuperEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(SuperEntity superEntity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(SuperEntity superEntity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object o) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public SuperEntity find(Object o) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List findAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getLastProductId() throws SQLException, ClassNotFoundException {
        String sql="SELECT code FROM order_details ORDER BY code DESC LIMIT 1";
        ResultSet resultSet= CrudUtil.execute(sql);
        if(resultSet.next()){
            return resultSet.getInt(1);
        }else {
            return 0;
        }
    }
}
