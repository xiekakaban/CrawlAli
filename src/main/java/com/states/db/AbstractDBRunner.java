package com.states.db;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;

import java.util.Map;

/**
 * Created by e604845 on 7/4/2017.
 */
public class AbstractDBRunner<T>{

    protected  BeanProcessor processor = new BeanProcessor();

    public AbstractDBRunner(){

    }
    public AbstractDBRunner(Map<String,String> dbCloumnProject){
        processor = new BeanProcessor(dbCloumnProject);
    }

}
