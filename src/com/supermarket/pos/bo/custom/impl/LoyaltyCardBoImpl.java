package com.supermarket.pos.bo.custom.impl;

import com.supermarket.pos.bo.custom.LoyaltyCardBo;
import com.supermarket.pos.dao.DaoFactory;
import com.supermarket.pos.dao.custom.LoyaltyCardDao;
import com.supermarket.pos.dto.LoyaltyCardDto;
import com.supermarket.pos.entity.LoyaltyCard;
import com.supermarket.pos.enums.DaoType;

import java.sql.SQLException;

public class LoyaltyCardBoImpl implements LoyaltyCardBo {
    private LoyaltyCardDao loyaltyCardDao= DaoFactory.getInstance().getDao(DaoType.LOYALTY_CARD);
    @Override
    public boolean saveLoyaltyCard(LoyaltyCardDto loyaltyCardDto) throws SQLException, ClassNotFoundException {
        return loyaltyCardDao.save(new LoyaltyCard(
                loyaltyCardDto.getLoyaltyCardCode(),
                loyaltyCardDto.getCardType(),
                loyaltyCardDto.getLoyaltyCardBarcode(),
                loyaltyCardDto.getCustomerEmail()));
    }


}
