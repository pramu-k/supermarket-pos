package com.supermarket.pos.bo.custom;

import com.supermarket.pos.bo.SuperBo;
import com.supermarket.pos.dto.LoyaltyCardDto;

import java.sql.SQLException;

public interface LoyaltyCardBo extends SuperBo {
    public boolean saveLoyaltyCard(LoyaltyCardDto loyaltyCardDto) throws SQLException, ClassNotFoundException;

}
