package com.states;

import com.states.db.SQLiteDBUtil;
import com.states.main.MainPanel;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        //GUIFace guiFace = new GUIFace();
        //guiFace.initAndShow();
        MainPanel mainPanel = new MainPanel();
        mainPanel.initAndShow();
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
