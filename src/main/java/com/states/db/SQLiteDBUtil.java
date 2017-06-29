package com.states.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.states.util.Constants;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by e604845 on 6/29/2017.
 */
public class SQLiteDBUtil {
    private static final String driver = "org.sqlite.JDBC";
    private static final String url = "jdbc:sqlite:"+ Constants.currentProjectPath+ File.separator+"db"+File.separator+"ali_product.db";
    private static ComboPooledDataSource dataSource = null;

    static {
        initDataSource();
    }

    public static void initDataSource(){
        if(dataSource == null) {
            dataSource = new ComboPooledDataSource();
            dataSource.setJdbcUrl(url);
        }
    }

    private SQLiteDBUtil(){

    }
   public static DataSource getDataSource(){
       if(dataSource == null){
           initDataSource();
       }
       return dataSource;
   }
    /**
     * 打印当前运行方法名称
     */
    public void printCurrentMethodName()
    {
        System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
        System.out.println("==================================================");
    }


    public static void release(){
        try {
            DbUtils.closeQuietly(dataSource.getConnection());
            dataSource.close();
            dataSource = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /****/

}
