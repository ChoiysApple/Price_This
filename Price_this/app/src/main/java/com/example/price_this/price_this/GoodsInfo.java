package com.example.price_this.price_this;

import java.util.ArrayList;

public class GoodsInfo {

    public String id;
    public String goodsPicture;
    public String crrtgoodsPrice;
    public String rgstgoodsPrice;
    public String goodsRank;
    public String goodsName;
    public ArrayList goodsTag;
    //public ArrayList<String> goodsTag;

    public GoodsInfo(String id, String goodsName, String goodsPicture, String crrtgoodsPrice) {
        this.id = id;
        this.goodsName = goodsName;
        this.goodsPicture = goodsPicture;
        this.crrtgoodsPrice = crrtgoodsPrice;
    }

    public GoodsInfo(String id, String goodsPicture, String crrtgoodsPrice, String rgstgoodsPrice, String goodsRank, String goodsName) {
        this.id = id;
        this.goodsPicture = goodsPicture;
        this.crrtgoodsPrice = crrtgoodsPrice;
        this.rgstgoodsPrice = rgstgoodsPrice;
        this.goodsRank = goodsRank;
        this.goodsName = goodsName;
    }

    public GoodsInfo(String id, String goodsPicture, String crrtgoodsPrice, String rgstgoodsPrice, String goodsRank, String goodsName, ArrayList goodsTag) {
        this.id = id;
        this.goodsPicture = goodsPicture;
        this.crrtgoodsPrice = crrtgoodsPrice;
        this.rgstgoodsPrice = rgstgoodsPrice;
        this.goodsRank = goodsRank;
        this.goodsName = goodsName;
        this.goodsTag = goodsTag;
    }

    /*public int getGoodsPicture(){
        return goodsPicture;
    }*/
}