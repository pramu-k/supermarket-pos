package com.supermarket.pos.dao.custom.impl;

import com.supermarket.pos.dao.CrudUtil;
import com.supermarket.pos.dao.custom.ItemDetailDao;
import com.supermarket.pos.entity.Customer;
import com.supermarket.pos.entity.ItemDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDetailDaoImpl implements ItemDetailDao {
    @Override
    public boolean save(ItemDetail itemDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO order_details_has_product_details VALUES(?,?,?,?,?)",
                itemDetail.getOrder(),
                itemDetail.getDetailCode(),
                itemDetail.getQty(),
                itemDetail.getDiscount(),
                itemDetail.getAmount());
    }

    @Override
    public boolean update(ItemDetail itemDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ItemDetail find(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ItemDetail> findAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM order_details_has_product_details");
        List<ItemDetail> itemDetailList = new ArrayList<>();

        while (resultSet.next()) {
            itemDetailList.add(new ItemDetail(
                    resultSet.getString(2),
                    resultSet.getInt(1),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
                    )
            );

        }
        return itemDetailList;
    }
}
