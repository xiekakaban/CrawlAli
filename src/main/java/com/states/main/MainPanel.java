package com.states.main;

import com.states.db.ProductDBRunner;
import com.states.entity.ProductEntity;
import com.states.util.Constants;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Observable;

/**
 * Created by e604845 on 7/12/2017.
 */
public class MainPanel extends Application {

    private ObservableList<ProductEntity> ObservableProductList = FXCollections.observableArrayList();
    private ProductDBRunner productDBRunner;

    public MainPanel(){
        productDBRunner = new ProductDBRunner();
        freshProductList(ObservableProductList);
    }
    public void initAndShow(){
        launch();
    }



    public void freshProductList(ObservableList<ProductEntity> ObservableProductList){
        ObservableProductList.clear();
        List<ProductEntity> productEntityList = productDBRunner.getProdList();
        ObservableProductList.addAll(productEntityList);

    }
    public void freshProductList(ObservableList<ProductEntity> ObservableProductList , int start, int end){
        ObservableProductList.clear();
        List<ProductEntity> productEntityList = productDBRunner.getProdListSeg(start,end);
        ObservableProductList.addAll(productEntityList);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Alibaba Crawler");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///"+ Constants.currentProjectPath+ File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+File.separator+"states"+File.separator+"main"+File.separator+"GUIFace.fxml"));
        Parent root  = loader.load();
        Scene scene = new Scene(root);
        FXMLGUIFaceController fxmlGUIFaceController = loader.getController();
        fxmlGUIFaceController.setMainPanel(this);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public ObservableList<ProductEntity> getObservableProductList() {
        return ObservableProductList;
    }

    public void setObservableProductList(ObservableList<ProductEntity> observableProductList) {
        ObservableProductList = observableProductList;
    }
}
