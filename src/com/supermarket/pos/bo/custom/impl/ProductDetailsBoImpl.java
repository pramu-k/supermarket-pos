package com.supermarket.pos.bo.custom.impl;

import com.supermarket.pos.bo.custom.ProductDetailsBo;
import com.supermarket.pos.dao.DaoFactory;
import com.supermarket.pos.dao.custom.ProductDetailDao;
import com.supermarket.pos.dto.ProductDetailDto;
import com.supermarket.pos.dto.ProductDetailJoinDto;
import com.supermarket.pos.entity.ProductDetail;
import com.supermarket.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsBoImpl implements ProductDetailsBo {
    ProductDetailDao dao= DaoFactory.getInstance().getDao(DaoType.PRODUCT_DETAILS);
    @Override
    public boolean saveProductDetails(ProductDetailDto dto) throws SQLException, ClassNotFoundException {
        return dao.save(new ProductDetail(
                dto.getCode(),
                dto.getQtyOnHand(),
                dto.getSellingPrice(),
                dto.getShowPrice(),
                dto.getBuyingPrice(),
                dto.getBarcode(),
                dto.getProductCode(),
                dto.isDiscountAvailability()
        ));
    }

    @Override
    public List<ProductDetailDto> findAllProductDetails(int productCode) throws SQLException, ClassNotFoundException {
        List<ProductDetailDto> dtos=new ArrayList<>();
        for(ProductDetail d: dao.findAllProductDetails(productCode)){
            dtos.add(new ProductDetailDto(
                    d.getCode(),
                    d.getQtyOnHand(),
                    d.getSellingPrice(),
                    d.getShowPrice(),
                    d.getBuyingPrice(),
                    d.getBarcode(),
                    d.getProductCode(),
                    d.isDiscountAvailability()
            ));
        }
        return dtos;
    }

    @Override
    public ProductDetailDto findProductDetails(String code) throws SQLException, ClassNotFoundException {
        ProductDetail productDetail=dao.findProductDetails(code);
        if(productDetail!=null){
            return new ProductDetailDto(
                    productDetail.getCode(),
                    productDetail.getQtyOnHand(),
                    productDetail.getSellingPrice(),
                    productDetail.getShowPrice(),
                    productDetail.getBuyingPrice(),
                    productDetail.getBarcode(),
                    productDetail.getProductCode(),
                    productDetail.isDiscountAvailability());
        }
        return null;

    }

    @Override
    public boolean deleteProductDetails(String code) throws SQLException, ClassNotFoundException {
        return dao.delete(code);
    }

    @Override
    public ProductDetailJoinDto findProductJoinDetails(String code) throws SQLException, ClassNotFoundException {
        return dao.findProductDetailsJoinData(code);
    }
}
