package com.supermarket.pos.dao.custom.impl;

import com.supermarket.pos.dao.CrudUtil;
import com.supermarket.pos.dao.custom.ItemDetailDao;
import com.supermarket.pos.entity.ItemDetail;

import java.sql.SQLException;
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
        return CrudUtil.execute("SELECT * FROM order_details_has_product_details");
    }
}
