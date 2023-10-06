package com.supermarket.pos.view.tm;

import java.util.Date;

public class OrderTm {
    private int orderId;
    private Date issuedDate;
    private double totalDiscount;
    private double totalCost;
    private String customerEmail;
    private String userEmail;

    public OrderTm(int orderId, Date issuedDate, double totalDiscount, double totalCost, String customerEmail, String userEmail) {
        this.orderId = orderId;
        this.issuedDate = issuedDate;
        this.totalDiscount = totalDiscount;
        this.totalCost = totalCost;
        this.customerEmail = customerEmail;
        this.userEmail = userEmail;
    }

    public OrderTm() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
