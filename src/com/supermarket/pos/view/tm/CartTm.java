package com.supermarket.pos.view.tm;

import javafx.scene.control.Button;

public class CartTm {
    private String code;
    private String description;
    private double sellingPrice;
    private double showPrice;
    private int qty;
    private double totalCost;
    private Button deleteButton;
    private double discount;


    public CartTm(String code, String description, double sellingPrice, double showPrice, int qty, double totalCost, Button deleteButton, double discount) {
        this.code = code;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.showPrice = showPrice;
        this.qty = qty;
        this.totalCost = totalCost;
        this.deleteButton = deleteButton;
        this.discount = discount;
    }

    public CartTm() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "CartTm{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", showPrice=" + showPrice +
                ", qty=" + qty +
                ", totalCost=" + totalCost +
                ", deleteButton=" + deleteButton +
                ", discount=" + discount +
                '}';
    }
}
