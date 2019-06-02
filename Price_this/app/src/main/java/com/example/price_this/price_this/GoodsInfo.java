package com.example.price_this.price_this;

import java.util.ArrayList;

public class GoodsInfo {

    public String id;
    public String goodsPicture;
    public String crrtgoodsPrice;
    public String avgPrice;
    public String goodsRank;
    public String goodsName;
    public ArrayList goodsTag;
    //public ArrayList<String> goodsTag;

    public GoodsInfo(String id, String goodsName, String crrtgoodsPrice){
        this.id = id;
        this.goodsName = goodsName;
        this.crrtgoodsPrice = crrtgoodsPrice;
    }

    public GoodsInfo(String avgPrice, String id, String goodsName, String goodsPicture, String crrtgoodsPrice) {
        this.id = id;
        this.goodsName = goodsName;
        this.goodsPicture = goodsPicture;
        this.crrtgoodsPrice = crrtgoodsPrice;
        this.avgPrice = avgPrice;
    }
}