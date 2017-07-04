package com.states.main;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.states.db.ProductDBRunner;
import com.states.entity.ProductDetailEntity;
import com.states.entity.ProductEntity;
import com.states.util.Constants;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.http.util.Asserts;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */
public class Crawler {
    private List<String> fetchUrlList; //also will store Filename
    private Gson gson = new GsonBuilder().serializeNulls().create();
    private List<String> successUrlList = new ArrayList<>();
    private List<String> failureUrlList = new ArrayList<>();
    private List<ProductEntity> productList = new ArrayList<>();


    public Crawler() {

    }

    public void clear(){
        this.successUrlList.clear();
        this.failureUrlList.clear();
        this.productList.clear();
    }


    private void getSourceByUrl(WebDriver chromeDriver){
        for (String fetchUrl : fetchUrlList) {
            try {
                chromeDriver.get(fetchUrl);
                if (chromeDriver instanceof JavascriptExecutor) {
                    JavascriptExecutor js = (JavascriptExecutor) chromeDriver;
                    js.executeScript("var q=document.body.scrollTop=100000");
                    Thread.sleep(5000);
                    ProductEntity p = structureProduct(chromeDriver.getPageSource());
                    p.setUrl(fetchUrl);
                    Asserts.check(!Strings.isNullOrEmpty(p.getTitle()),"Can not structure Product");
                    successUrlList.add(fetchUrl);
                    productList.add(p);
                }
            }catch (Exception e) {
                failureUrlList.add(fetchUrl);
            }
        }
    }


    private void getSourceByFile(){
        for (String fetchUrl : fetchUrlList) {
            try {
                File f = new File(Constants.currentProjectPath + File.separator + "doc" + File.separator + "data" +File.separator+fetchUrl);
                Asserts.check(f.isFile(),"Can not found "+fetchUrl);
                ProductEntity p = structureProduct(Files.toString(f, Charsets.UTF_8));
                p.setUrl(fetchUrl);
                Asserts.check(!Strings.isNullOrEmpty(p.getTitle()),"Can not structure Product");
                successUrlList.add(fetchUrl);
                productList.add(p);
            }catch (Exception e){
                failureUrlList.add(fetchUrl);
            }

        }
    }


    private ProductEntity structureProduct(String sourceHtml){
        Document doc = Jsoup.parse(sourceHtml);
        ProductEntity currentProduct = initProductEntity(doc);
        return currentProduct;
    }

    public List<ProductEntity> storeProduct(){
        ProductDBRunner productDBRunner = new ProductDBRunner();
        List<ProductEntity> failurList = new ArrayList<>();
        for(ProductEntity p : productList){
            if(!productDBRunner.insert(p)){
                failurList.add(p);
            }
        }
        return failurList;
    }



    public void fetch(){
        if(Constants.isLocalTest){
            getSourceByFile();
        }else{
            System.setProperty("webdriver.chrome.driver","D:\\Software\\chromedriver.exe");
            WebDriver chromeDriver = new ChromeDriver();
            getSourceByUrl(chromeDriver);
            chromeDriver.quit();
        }
    }

    public List<String> getSuccessUrlList() {
        return successUrlList;
    }

    public void setSuccessUrlList(List<String> successUrlList) {
        this.successUrlList = successUrlList;
    }

    public List<String> getFailureUrlList() {
        return failureUrlList;
    }

    public void setFailureUrlList(List<String> failureUrlList) {
        this.failureUrlList = failureUrlList;
    }

    public List<ProductEntity> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductEntity> productList) {
        this.productList = productList;
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
