package com.supermarket.pos.bo.custom;

import com.supermarket.pos.bo.SuperBo;
import com.supermarket.pos.dto.ProductDetailDto;
import com.supermarket.pos.dto.ProductDetailJoinDto;
import com.supermarket.pos.entity.ProductDetail;

import java.sql.SQLException;
import java.util.List;

public interface ProductDetailsBo extends SuperBo {

    public boolean saveProductDetails(ProductDetailDto dto) throws SQLException, ClassNotFoundException;
    public List<ProductDetailDto> findAllProductDetails(int productCode) throws SQLException, ClassNotFoundException;
    public ProductDetailDto findProductDetails(String productCode) throws SQLException, ClassNotFoundException;
    public boolean deleteProductDetails(String code) throws SQLException, ClassNotFoundException;
    public ProductDetailJoinDto findProductJoinDetails(String code) throws SQLException, ClassNotFoundException;

}
