package com.supermarket.pos.dto;

import com.supermarket.pos.enums.CardType;

public class LoyaltyCardDto {
    private int loyaltyCardCode;
    private CardType cardType;
    private String loyaltyCardBarcode;
    private String customerEmail;

    public LoyaltyCardDto(int loyaltyCardCode, CardType cardType, String loyaltyCardBarcode, String customerEmail) {
        this.loyaltyCardCode = loyaltyCardCode;
        this.cardType = cardType;
        this.loyaltyCardBarcode = loyaltyCardBarcode;
        this.customerEmail = customerEmail;
    }

    public LoyaltyCardDto() {
    }

    public int getLoyaltyCardCode() {
        return loyaltyCardCode;
    }

    public void setLoyaltyCardCode(int loyaltyCardCode) {
        this.loyaltyCardCode = loyaltyCardCode;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getLoyaltyCardBarcode() {
        return loyaltyCardBarcode;
    }

    public void setLoyaltyCardBarcode(String loyaltyCardBarcode) {
        this.loyaltyCardBarcode = loyaltyCardBarcode;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public String toString() {
        return "LoyaltyCardDto{" +
                "loyaltyCardCode=" + loyaltyCardCode +
                ", cardType=" + cardType +
                ", loyaltyCardBarcode='" + loyaltyCardBarcode + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                '}';
    }
}
