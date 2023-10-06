package com.supermarket.pos.dao;

import com.supermarket.pos.dao.custom.impl.*;
import com.supermarket.pos.enums.DaoType;


public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return (daoFactory==null)? daoFactory=new DaoFactory():daoFactory;
    }
    public <T> T getDao(DaoType daoType){
        switch (daoType){
            case USER:
                return (T) new UserDaoImpl();
            case CUSTOMER:
                return (T) new CustomerDaoImpl();
            case PRODUCT:
                return (T) new ProductDaoImpl();
            case PRODUCT_DETAILS:
                return (T) new ProductDetailDaoImpl();
            case ITEM_DETAIL:
                return (T) new ItemDetailDaoImpl();
            case ORDER_DETAILS:
                return (T) new OrderDetailDaoImpl();
            case LOYALTY_CARD:
                return (T) new LoyaltyCardDaoImpl();
            default:
                return null;
        }
    }
}
