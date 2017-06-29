package com.states.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */
public class ProductEntity extends AbstractEntity {
    private String title;
    private String url;
    private String picList;
    private List<ProductDetailEntity> productDetEntityList;
    private String detail;
    private String content;
    private String create_time;
    private String isAdd;


    public ProductEntity() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicList() {
        return picList;
    }

    public void setPicList(String picList) {
        this.picList = picList;
    }

    public List<ProductDetailEntity> getProductDetEntityList() {
        return productDetEntityList;
    }

    public void setProductDetEntityList(List<ProductDetailEntity> productDetEntityList) {
        this.productDetEntityList = productDetEntityList;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
