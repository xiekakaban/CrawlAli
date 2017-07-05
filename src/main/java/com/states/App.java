package com.states;

import com.states.db.ProductDBRunner;
import com.states.db.SQLiteDBUtil;
import com.states.entity.ProductEntity;
import com.states.main.Crawler;
import com.states.main.GUIFace;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        GUIFace guiFace = new GUIFace();
        guiFace.initAndShow();

//        Crawler crawler = new Crawler();
        //crawler.setFetchUrlList(Arrays.asList("https://detail.1688.com/offer/536685049415.html?spm=a2615.2177701.0.0.VqHpf2"));
//        crawler.setFetchUrlList(Arrays.asList("chopsticks.html"));
//        crawler.fetch();
//        System.out.println("failure list:"+crawler.storeProduct().size());
//
//        ProductDBRunner productDBRunner = new ProductDBRunner();
//        List<ProductEntity> pList = productDBRunner.getAllProduct();
//        for(ProductEntity p : pList){
//            System.out.println(p.getProductDetEntityList());
//        }
       SQLiteDBUtil.release();
    }
}
