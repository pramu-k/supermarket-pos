package com.supermarket.pos.dao.custom;

import com.supermarket.pos.dao.CrudDao;
import com.supermarket.pos.dto.ProductDetailJoinDto;
import com.supermarket.pos.entity.ProductDetail;

import java.sql.SQLException;
import java.util.List;

public interface ProductDetailDao extends CrudDao<ProductDetail, String> {
    public List<ProductDetail> findAllProductDetails(int productCode) throws SQLException, ClassNotFoundException;
    public ProductDetail findProductDetails(String productCode) throws SQLException, ClassNotFoundException;
    public ProductDetailJoinDto findProductDetailsJoinData(String code) throws SQLException, ClassNotFoundException;
    public boolean manageQuantity(String barcode, int qty) throws SQLException, ClassNotFoundException;

}
