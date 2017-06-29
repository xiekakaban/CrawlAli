package com.states;

import com.states.db.SQLiteDBUtil;
import com.states.entity.ProductEntity;
import com.states.main.Crawler;
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
        Crawler crawler = new Crawler();
        //crawler.setFetchUrlList(Arrays.asList("https://detail.1688.com/offer/536685049415.html?spm=a2615.2177701.0.0.VqHpf2"));
        crawler.setFetchUrlList(Arrays.asList("chopsticks.html"));
        //crawler.fetch()
        QueryRunner runner = new QueryRunner(SQLiteDBUtil.getDataSource());
        try {
        List<ProductEntity> productEntityList = runner.query("select * from ali_prod", new BeanListHandler<ProductEntity>(ProductEntity.class));
        productEntityList.forEach((item)->{
            System.out.println(item.getProductDetEntityList());
        });
    } catch (SQLException e) {
        e.printStackTrace();
    }

        SQLiteDBUtil.release();

    }
}
