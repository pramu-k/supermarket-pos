package com.supermarket.pos.dao.custom.impl;

import com.supermarket.pos.dao.CrudUtil;
import com.supermarket.pos.dao.custom.LoyaltyCardDao;
import com.supermarket.pos.entity.LoyaltyCard;

import java.sql.SQLException;
import java.util.List;

public class LoyaltyCardDaoImpl implements LoyaltyCardDao {
    @Override
    public boolean save(LoyaltyCard loyaltyCard) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO loyalty_card VALUES(?,?,?,?)",
                loyaltyCard.getLoyaltyCardCode(),
                loyaltyCard.getCardType().name(),
                loyaltyCard.getLoyaltyCardBarcode(),
                loyaltyCard.getCustomerEmail());
    }

    @Override
    public boolean update(LoyaltyCard loyaltyCard) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public LoyaltyCard find(Integer integer) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<LoyaltyCard> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
