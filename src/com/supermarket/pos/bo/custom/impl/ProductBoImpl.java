package com.supermarket.pos.bo.custom.impl;

import com.supermarket.pos.bo.custom.ProductBo;
import com.supermarket.pos.dao.DaoFactory;
import com.supermarket.pos.dao.custom.ProductDao;
import com.supermarket.pos.dto.ProductDto;
import com.supermarket.pos.entity.Product;

import com.supermarket.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBoImpl implements ProductBo {
    ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
    @Override
    public boolean saveProduct(ProductDto dto) throws SQLException, ClassNotFoundException {
        return productDao.save(
                new Product(dto.getCode(), dto.getDescription())
        );
    }

    @Override
    public boolean updateProduct(ProductDto dto) {
        return false;
    }

    @Override
    public boolean deleteProduct(int code) throws SQLException, ClassNotFoundException {
        return productDao.delete(code);
    }

    @Override
    public ProductDto findProduct(int code) throws SQLException, ClassNotFoundException {
        Product product = productDao.find(code);
        if (product!=null) {
            return new ProductDto(product.getCode(), product.getDescription());
        }
        return null;
    }

    @Override
    public List<ProductDto> findAllProducts() throws SQLException, ClassNotFoundException {
        List<ProductDto> dtos = new ArrayList<>();

        for (Product c:productDao.findAll()) {
            dtos.add(new ProductDto(c.getCode(),c.getDescription()));
        }
        return dtos;
    }
    public  int getLastProductId() throws SQLException, ClassNotFoundException {
        return productDao.getLastProductId();
    }
}
