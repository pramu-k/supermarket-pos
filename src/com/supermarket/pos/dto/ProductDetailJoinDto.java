package com.supermarket.pos.dto;

public class ProductDetailJoinDto {
    private int code;
    private String description;
    private ProductDetailDto productDetailDto;

    public ProductDetailJoinDto(int code, String description, ProductDetailDto productDetailDto) {
        this.code = code;
        this.description = description;
        this.productDetailDto = productDetailDto;
    }

    public ProductDetailJoinDto() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductDetailDto getProductDetailDto() {
        return productDetailDto;
    }

    public void setProductDetailDto(ProductDetailDto productDetailDto) {
        this.productDetailDto = productDetailDto;
    }

    @Override
    public String toString() {
        return "ProductDetailJoinDto{" +
                "code=" + code +
                ", description='" + description + '\'' +
                ", productDetailDto=" + productDetailDto +
                '}';
    }
}
