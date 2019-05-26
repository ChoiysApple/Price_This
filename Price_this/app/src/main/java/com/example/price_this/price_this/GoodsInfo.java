package com.example.price_this.price_this;

import java.util.ArrayList;

public class GoodsInfo {

    public int goodsPicture;
    public String crrtgoodsPrice;
    public String rgstgoodsPrice;
    public String goodsRank;
    public String goodsName;
    public ArrayList<String> goodsTag;

    public GoodsInfo(int goodsPicture, String crrtgoodsPrice, String rgstgoodsPrice, String goodsRank, String goodsName) {
        this.goodsPicture = goodsPicture;
        this.crrtgoodsPrice = crrtgoodsPrice;
        this.rgstgoodsPrice = rgstgoodsPrice;
        this.goodsRank = goodsRank;
        this.goodsName = goodsName;
    }

    public GoodsInfo(int goodsPicture, String crrtgoodsPrice, String rgstgoodsPrice, String goodsRank, String goodsName, ArrayList<String> goodsTag) {
        this.goodsPicture = goodsPicture;
        this.crrtgoodsPrice = crrtgoodsPrice;
        this.rgstgoodsPrice = rgstgoodsPrice;
        this.goodsRank = goodsRank;
        this.goodsName = goodsName;
        this.goodsTag = goodsTag;
    }

    public int getGoodsPicture(){
        return goodsPicture;
    }
}
