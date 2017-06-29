package com.states.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.states.Entity.ProductDetailEntity;
import com.states.Entity.ProductEntity;
import org.eclipse.jetty.util.ajax.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/6/28.
 */
public class Crawler {
    private List<String> fetchUrlList;
    private Gson gson = new GsonBuilder().serializeNulls().create();
    public Crawler() {
    }

    public void fetch(){
        System.setProperty("webdriver.chrome.driver","D:\\Software\\chromedriver.exe");
        WebDriver chromeDriver = new ChromeDriver();
        List<String> successUrlList = new ArrayList<>();
        List<String> failureUrlList = new ArrayList<>();
        List<ProductEntity> productList = new ArrayList<>();

        try {
            for (String fetchUrl : fetchUrlList) {
                chromeDriver.get(fetchUrl);
                if (chromeDriver instanceof JavascriptExecutor) {
                    JavascriptExecutor js = (JavascriptExecutor) chromeDriver;
                    js.executeScript("var q=document.body.scrollTop=100000");
                    Thread.sleep(5000);
                    Document doc = Jsoup.parse(chromeDriver.getPageSource());
                    ProductEntity currentProduct = initProductEntity(doc);
                    System.out.println(gson.toJson(currentProduct));
                    if(currentProduct != null){
                        successUrlList.add(fetchUrl);
                        productList.add(currentProduct);
                    }else{
                        failureUrlList.add(fetchUrl);
                    }
                } else {
                    throw new IllegalStateException("This Exeplorer is not support execute Javascript!");
                }
                chromeDriver.quit();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Can`t fetch url");
        }finally {
            chromeDriver.quit();
        }
    }

    private ProductEntity initProductEntity(Document doc){
        ProductEntity p = new ProductEntity();
        p.setTitle(getProductTitle(doc));
        p.setPicList(getProductPicList(doc));
        p.setDetail(getProductDetail(doc));
        p.setContent(getProductContent(doc));
        p.setProductDetEntityList(getProductSaleInfo(doc));
        return p;
    }
    private List<ProductDetailEntity> getProductSaleInfo(Document doc){
        List<ProductDetailEntity> prodDetList = new ArrayList<>();
        Elements proDetEles = doc.select("table.table-sku tr[data-sku-config]");
        JsonParser jParser = new JsonParser();
        for(Element e : proDetEles){
            ProductDetailEntity productDetailEntity = new ProductDetailEntity();
            Elements pic = e.select("td.name>span[data-imgs]");
            if(pic.size() != 0){
                // contains img
                productDetailEntity.setImgUrl(((JsonObject)jParser.parse(pic.first().attr("data-imgs"))).get("preview").getAsString());

            }
            productDetailEntity.setName(e.select("td.name").first().text());
            productDetailEntity.setPrice(e.select("td.price").first().text());
            productDetailEntity.setCount(e.select("td.count").first().text());
            prodDetList.add(productDetailEntity);
        }
        return prodDetList;
    }
    private String getProductContent(Document doc){
        return doc.select("div#desc-lazyload-container").first().html();
    }

    private String getProductDetail(Document doc){
        Map<String,String> picListResult = new HashMap<>();
        Elements labels = doc.select("div#mod-detail-attributes table tr>td:nth-child(2n+1)");
        Elements values = doc.select("div#mod-detail-attributes table tr>td:nth-child(2n)");

        for(int i=0;i<labels.size();i++){
            String key = labels.get(i).text();
            if(key!=null && key.length()>0) {
                String value = values.get(i).text();
                picListResult.put(key, value);
            }
        }
        return gson.toJson(picListResult);
    }
    private String getProductTitle(Document doc){
        Element titleEle = doc.select("h1.d-title").first();
        return titleEle.text();
    }

    private String getProductPicList(Document doc){
        List<String> picListResult = new ArrayList<>();
        JsonParser jParser = new JsonParser();
        Elements picEles = doc.select("div#dt-tab li.tab-trigger");
        String picUrlTemp = "";
        for(Element picEleItem : picEles){
            picUrlTemp = ((JsonObject)jParser.parse(picEleItem.attr("data-imgs"))).get("preview").getAsString();
            picListResult.add(picUrlTemp);
        }
        return gson.toJson(picListResult);
    }


    public List<String> getFetchUrlList() {
        return fetchUrlList;
    }

    public void setFetchUrlList(List<String> fetchUrlList) {
        this.fetchUrlList = fetchUrlList;
    }
}
