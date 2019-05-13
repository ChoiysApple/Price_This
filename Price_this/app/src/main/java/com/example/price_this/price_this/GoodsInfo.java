package com.example.price_this.price_this;

public class GoodsInfo {

    public int goodsPicture;
    public String goodsPrice;
    public String goodsName;

    public GoodsInfo(int goodsPicture, String goodsPrice, String goodsName) {
        this.goodsPicture = goodsPicture;
        this.goodsPrice = goodsPrice;
        this.goodsName = goodsName;
    }

    public int getGoodsPicture(){
        return goodsPicture;
    }
}
