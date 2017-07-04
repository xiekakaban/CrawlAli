package com.states.db;

import com.mchange.util.AssertException;
import com.states.entity.ProductEntity;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.http.util.Asserts;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by e604845 on 7/4/2017.
 */
public class ProductDBRunner extends AbstractDBRunner<ProductEntity>{

        public static Map<String,String> ROWMAP  = new HashMap<String,String>();
        static {
            ROWMAP.put("id","id");
            ROWMAP.put("title","title");
            ROWMAP.put("url","url");
            ROWMAP.put("picList","picList");
            ROWMAP.put("productDetENtityList","productDetENtityList");
            ROWMAP.put("detail","detail");
            ROWMAP.put("content","content");
            ROWMAP.put("isAdd","isAdd");
            ROWMAP.put("create_time","createTime");
         }

        public ProductDBRunner(){
            super(ROWMAP);

        }

        public Boolean insert(ProductEntity p){
            try {
                Asserts.check(p != null, "ProductEntity is null when insert");
                QueryRunner queryRunner = new QueryRunner(SQLiteDBUtil.getDataSource());
                p.setId(UUID.randomUUID().toString());
                if (p != null) {
                    queryRunner.update("insert into ali_prod (id,title,url,picList,ProductDetEntityList,detail,content)VALUES(?,?,?,?,?,?,?)", p.getId()
                            , p.getTitle(), p.getUrl(), p.getPicList(), p.getProductDetEntityList(), p.getDetail(), p.getContent());
                }
            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
            return true;
        }

        public ProductEntity getProductById(String id){
            return null;
        }
        public List<ProductEntity> getAllProduct(){
            try{
                QueryRunner queryRunner = new QueryRunner(SQLiteDBUtil.getDataSource());
                RowProcessor rowProcessor = new BasicRowProcessor(this.processor);

                List<ProductEntity> productEntityList = queryRunner.query("select * from ali_prod", new BeanListHandler<ProductEntity>(ProductEntity.class, rowProcessor));
                return productEntityList;
            }catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }



}
