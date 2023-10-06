package com.supermarket.pos.dao.custom;

import com.supermarket.pos.dao.CrudDao;
import com.supermarket.pos.entity.Product;

import java.sql.SQLException;


public interface ProductDao extends CrudDao<Product,Integer> { //Here we cannot use int. We have to use the wrapper class Integer.

    int getLastProductId() throws SQLException, ClassNotFoundException;
}
