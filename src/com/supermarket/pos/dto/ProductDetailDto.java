package com.supermarket.pos.dto;

import com.supermarket.pos.entity.SuperEntity;

public class ProductDetailDto{
    private String code;
    private int qtyOnHand;
    private double sellingPrice;
    private double showPrice;
    private double buyingPrice;
    private String barcode;
    private int productCode;
    private boolean discountAvailability;

    public ProductDetailDto(String code, int qtyOnHand, double sellingPrice, double showPrice, double buyingPrice, String barcode, int productCode, boolean discountAvailability) {
        this.code = code;
        this.qtyOnHand = qtyOnHand;
        this.sellingPrice = sellingPrice;
        this.showPrice = showPrice;
        this.buyingPrice = buyingPrice;
        this.barcode = barcode;
        this.productCode = productCode;
        this.discountAvailability = discountAvailability;
    }

    public ProductDetailDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(double showPrice) {
        this.showPrice = showPrice;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public boolean isDiscountAvailability() {
        return discountAvailability;
    }

    public void setDiscountAvailability(boolean discountAvailability) {
        this.discountAvailability = discountAvailability;
    }
}
