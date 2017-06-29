package com.states.Entity;

/**
 * Created by Administrator on 2017/6/28.
 */
public class ProductDetailEntity extends AbstractEntity{
    private String name;
    private String imgUrl;
    private String price;
    private String count;

    public ProductDetailEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
