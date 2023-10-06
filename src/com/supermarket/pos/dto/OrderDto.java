package com.supermarket.pos.dto;

import java.util.Date;

public class OrderDto {
    private int orderCode;
    private Date issuedDate;
    private double totalCost;
    private double discount;
    private String customerEmail;
    private String operatorEmail;

    public OrderDto(int orderCode, Date issuedDate, double totalCost, double discount, String customerEmail,String operatorEmail) {
        this.orderCode = orderCode;
        this.issuedDate = issuedDate;
        this.totalCost = totalCost;
        this.discount = discount;
        this.customerEmail = customerEmail;
        this.operatorEmail = operatorEmail;
    }

    public OrderDto() {
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    public void setOperatorEmail(String customerEmail) {
        this.operatorEmail = customerEmail;
    }
}
