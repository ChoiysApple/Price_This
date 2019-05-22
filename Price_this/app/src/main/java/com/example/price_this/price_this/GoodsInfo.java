package com.example.price_this.price_this;

import java.util.ArrayList;

public class GoodsInfo {

    public int goodsPicture;
    public String goodsPrice;
    public String goodsName;
    public String[] goodsTag;

    public GoodsInfo(int goodsPicture, String goodsPrice, String goodsName) {
        this.goodsPicture = goodsPicture;
        this.goodsPrice = goodsPrice;
        this.goodsName = goodsName;
    }

    public GoodsInfo(int goodsPicture, String goodsPrice, String goodsName, String[] goodsTag) {
        this.goodsPicture = goodsPicture;
        this.goodsPrice = goodsPrice;
        this.goodsName = goodsName;
        this.goodsTag = goodsTag;
    }

    public int getGoodsPicture(){
        return goodsPicture;
    }
}
