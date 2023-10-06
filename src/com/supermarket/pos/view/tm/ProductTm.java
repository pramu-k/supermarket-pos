package com.supermarket.pos.view.tm;

import javafx.scene.control.Button;

public class ProductTm {
    private int productId;
    private String productDescription;
    private Button showMore;
    private Button btnDeleteProduct;

    public ProductTm(int productId, String productDescription, Button showMore, Button btnDeleteProduct) {
        this.setProductId(productId);
        this.setProductDescription(productDescription);
        this.setShowMore(showMore);
        this.setBtnDeleteProduct(btnDeleteProduct);
    }

    public ProductTm() {
    }


    @Override
    public String toString() {
        return "ProductTm{" +
                "productId=" + getProductId() +
                ", productDescription='" + getProductDescription() + '\'' +
                ", showMore='" + getShowMore() + '\'' +
                ", btnDeleteProduct=" + getBtnDeleteProduct() +
                '}';
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Button getShowMore() {
        return showMore;
    }

    public void setShowMore(Button showMore) {
        this.showMore = showMore;
    }

    public Button getBtnDeleteProduct() {
        return btnDeleteProduct;
    }

    public void setBtnDeleteProduct(Button btnDeleteProduct) {
        this.btnDeleteProduct = btnDeleteProduct;
    }
}
