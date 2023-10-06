package com.supermarket.pos.bo.custom.impl;

import com.supermarket.pos.bo.custom.OrderDetailBo;
import com.supermarket.pos.dao.DaoFactory;
import com.supermarket.pos.dao.custom.ItemDetailDao;
import com.supermarket.pos.dao.custom.OrderDetailDao;
import com.supermarket.pos.dao.custom.ProductDetailDao;
import com.supermarket.pos.db.DbConnection;
import com.supermarket.pos.dto.ItemDetailDto;
import com.supermarket.pos.dto.OrderDetailDto;
import com.supermarket.pos.entity.ItemDetail;
import com.supermarket.pos.entity.OrderDetail;
import com.supermarket.pos.enums.DaoType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo {
    OrderDetailDao orderDetailDao= DaoFactory.getInstance().getDao(DaoType.ORDER_DETAILS);
    ItemDetailDao itemDetailDao= DaoFactory.getInstance().getDao(DaoType.ITEM_DETAIL);
    ProductDetailDao productDetailDao= DaoFactory.getInstance().getDao(DaoType.PRODUCT_DETAILS);

    @Override
    public boolean makeOrder(OrderDetailDto dto) throws SQLException {
        //transaction
        //set autocommit = false
        //save the order
        // item list -> one by one
        // update product qty
        // return true
        // commit
        // set autocommit =false
        Connection connection=null;
        try{
            connection= DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            if(saveOrder(dto)){
                //item details
                boolean isItemsSaved = saveItemDetails(dto.getItemDetailDto(), dto.getCode());
                if(isItemsSaved){
                    connection.commit();
                    return true;
                }else{
                    connection.rollback();
                    return false;
                }
            }else{
                connection.rollback();
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }
    private  boolean saveOrder(OrderDetailDto dto) throws SQLException, ClassNotFoundException {
        return orderDetailDao.save(new OrderDetail(
                dto.getCode(),
                dto.getIssuedDate(),
                dto.getTotalCost(),
                dto.getCustomerEmail(),
                dto.getDiscount(),
                dto.getOperatorEmail()));
    }
    private boolean saveItemDetails(List<ItemDetailDto> list,int orderCode) throws SQLException, ClassNotFoundException {
        for (ItemDetailDto dto: list) {
            boolean isItemSaved= itemDetailDao.save(new ItemDetail(
                    dto.getDetailCode(),
                    orderCode,
                    dto.getQty(),
                    dto.getDiscount(),
                    dto.getAmount()));
            if(isItemSaved){
                //update qty
                if(!updateQty(dto.getDetailCode(), dto.getQty())){
                    return false;
                }
            }else {
                return false;
            }
        }
        return true;
    }
    public boolean updateQty(String productCode,int qty) throws SQLException, ClassNotFoundException {
        return productDetailDao.manageQuantity(productCode,qty);
    }
}
