package com.supermarket.pos.dao.custom.impl;

import com.supermarket.pos.dao.CrudUtil;
import com.supermarket.pos.dao.custom.ProductDetailDao;
import com.supermarket.pos.dto.ProductDetailDto;
import com.supermarket.pos.dto.ProductDetailJoinDto;
import com.supermarket.pos.entity.ProductDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailDaoImpl implements ProductDetailDao {
    @Override
    public boolean save(ProductDetail productDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO product_details VALUES(?,?,?,?,?,?,?,?)",
                productDetail.getCode(),
                productDetail.getBarcode(),
                productDetail.getQtyOnHand(),
                productDetail.getSellingPrice(),
                productDetail.isDiscountAvailability(),
                productDetail.getShowPrice(),
                productDetail.getProductCode(),
                productDetail.getBuyingPrice());
    }

    @Override
    public boolean update(ProductDetail productDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM product_details WHERE code=?",s);
    }

    @Override
    public ProductDetail find(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ProductDetail> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ProductDetail> findAllProductDetails(int productCode) throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM product_details where product_code=?",productCode);
        List<ProductDetail> list=new ArrayList<>();
        while (resultSet.next()){
            list.add(new ProductDetail(resultSet.getString(1),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(6),
                    resultSet.getDouble(8),
                    resultSet.getString(2),
                    resultSet.getInt(7),
                    resultSet.getBoolean(5)));
        }
        return list;
    }

    @Override
    public ProductDetail findProductDetails(String productCode) throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM product_details WHERE code=?", productCode);
        if(resultSet.next()){
            return new ProductDetail(resultSet.getString(1),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(6),
                    resultSet.getDouble(8),
                    resultSet.getString(2),
                    resultSet.getInt(7),
                    resultSet.getBoolean(5));
        }
        return null;
    }

    @Override
    public ProductDetailJoinDto findProductDetailsJoinData(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM product_details pd JOIN product p ON pd.code=? AND pd.product_code=p.code", code);
        if(resultSet.next()){
            return new ProductDetailJoinDto(
                    resultSet.getInt(9),
                    resultSet.getString(10),
                    new ProductDetailDto(
                            resultSet.getString(1),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(6),
                    resultSet.getDouble(8),
                    resultSet.getString(2),
                    resultSet.getInt(7),
                    resultSet.getBoolean(5)));

        }
        return null;
    }

    @Override
    public boolean manageQuantity(String barcode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE product_details SET qty_on_hand=(qty_on_hand-?) WHERE code=?",qty,barcode);
    }
    @Override
    public double findBuyingPriceByBatchCode(String batchCode) throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT buying_price FROM product_details WHERE code=?", batchCode);
        if(resultSet.next()){
           double buyingPrice=resultSet.getInt("buying_price");
           return buyingPrice;
        }else {
            return 0;
        }
    }
}
