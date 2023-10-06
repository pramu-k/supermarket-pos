package com.supermarket.pos.dao.custom.impl;

import com.supermarket.pos.dao.CrudUtil;
import com.supermarket.pos.dao.custom.ProductDao;
import com.supermarket.pos.entity.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean save(Product product) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("INSERT INTO product VALUES (?,?)",product.getCode(),product.getDescription());
    }

    @Override
    public boolean update(Product product) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("UPDATE product SET description=? WHERE code=?",product.getDescription(),product.getCode());
    }

    @Override
    public boolean delete(Integer code) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("DELETE FROM product WHERE code=?",code);
    }

    @Override
    public Product find(Integer code) throws SQLException, ClassNotFoundException {

        ResultSet set =CrudUtil.execute("SELECT * FROM product WHERE code=?",code);
        if(set.next()){
            return new Product(set.getInt(1),
                    set.getString(2));
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet= CrudUtil.execute("SELECT * FROM product");
        List<Product> productList = new ArrayList<>();

        while (resultSet.next()) {
            productList.add(new Product(
                    resultSet.getInt(1),
                    resultSet.getString(2))
            );

        }
        return productList;
    }
    public int getLastProductId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet=CrudUtil.execute("SELECT code FROM product ORDER BY code DESC LIMIT 1");

        if(resultSet.next()){
            return resultSet.getInt(1);
        }else {
            return 0;
        }
    }


}
