package com.supermarket.pos.bo;

import com.supermarket.pos.bo.custom.impl.*;
import com.supermarket.pos.dao.custom.impl.CustomerDaoImpl;
import com.supermarket.pos.dao.custom.impl.ProductDaoImpl;
import com.supermarket.pos.dao.custom.impl.UserDaoImpl;
import com.supermarket.pos.enums.BoType;
import com.supermarket.pos.enums.DaoType;


public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){}

    public static BoFactory getInstance(){
        return (boFactory==null)? boFactory=new BoFactory():boFactory;
    }
    public <T> T getBo(BoType boType){
        switch (boType){
            case USER:
                return (T) new UserBoImpl();
            case CUSTOMER:
                return (T) new CustomerBoImpl();
            case PRODUCT:
                return (T) new ProductBoImpl();
            case PRODUCT_DETAILS:
                return (T) new ProductDetailsBoImpl();
            case ITEM_DETAIL:
                return (T) new ItemDetailBoImpl();
            case ORDER_DETAIL:
                return (T) new OrderDetailBoImpl();
            case LOYALTY_CARD:
                return (T) new LoyaltyCardBoImpl();
            case ORDER:
                return (T) new OrderBoImpl();
            default:
                return null;
        }
    }
}
