package com.states.main;


import com.states.db.ProductDBRunner;
import com.states.entity.ProductEntity;
import com.states.util.Constants;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * Created by e604845 on 7/5/2017.
 */
public class GUIFace extends Application{

    public GUIFace() {

    }

    public void initAndShow(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Ali Crawler");
        BorderPane mainBorderlayout = new BorderPane();
        mainBorderlayout.setBottom(initBottom());
        mainBorderlayout.setCenter(initCenter());
        mainBorderlayout.setRight(initRight());
        Scene scene = new Scene(mainBorderlayout,800,750);
        scene.getStylesheets().add(Constants.resourcePath+ File.separator+"css"+File.separator+"default.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane initCenter(){
        Label centerL = new Label("Center");
        GridPane gridPane = new GridPane();
        gridPane.add(centerL, 1, 0);
        gridPane.setPrefHeight(600);
        gridPane.setPrefWidth(500);
        gridPane.setStyle("-fx-background-color: #891111;");
        return gridPane;
    }

    private Pane initBottom(){
        HBox hBox = new HBox();
        Button fetchBth = new Button("Bottom");
        hBox.getChildren().add(fetchBth);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(hBox);
        anchorPane.setBottomAnchor(hBox,8.0);
        anchorPane.setRightAnchor(hBox, 5.0);
        anchorPane.setPrefHeight(150);
        anchorPane.setStyle("-fx-background-color: #88ff99;");

        return anchorPane;
    }
    private Pane initRight(){
        VBox vBox = new VBox();
        ListView<ProductEntity> list = new ListView<ProductEntity>();
        list.setItems(getProductItems());

        list.setCellFactory(new Callback<ListView<ProductEntity>, ListCell<ProductEntity>>() {
            @Override
            public ListCell<ProductEntity> call(ListView<ProductEntity> param) {
                ProductListCell cell = new ProductListCell();

                return cell;
            }
        });

        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickListItem(list.getSelectionModel().getSelectedItem().getId());
                System.out.println(list.getSelectionModel().getSelectedItem().getTitle()+" has been selected!" + System.currentTimeMillis());
            }
        });

        list.setPrefHeight(600);
        vBox.getChildren().add(list);
        vBox.setPrefHeight(600);
        vBox.setPrefWidth(300);
        vBox.setStyle("-fx-background-color: #336699;");
        return vBox;
    }

    private ObservableList<ProductEntity> getProductItems(){
        ObservableList<ProductEntity> items = FXCollections.observableArrayList();
        ProductDBRunner productDBRunner = new ProductDBRunner();
        for(ProductEntity pItem : productDBRunner.getProdNameList()){
            items.add(pItem);
        }
        return items;
    }

    private void onClickListItem(String clickId){
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        //title

        //picList

        //prodDetEntity

        //detail

        //content

        //createTime


    }

    class ProductListCell extends ListCell<ProductEntity>{

        @Override
        protected void updateItem(ProductEntity item, boolean empty) {
            super.updateItem(item, empty);
            if(item != null) {
                HBox listHBox = new HBox();
                String title = item.getTitle();
                if(title.length()>40){
                    title = title.substring(40);
                }
                Label prodTitle = new Label(title);
                prodTitle.setPrefWidth(250);
                prodTitle.setWrapText(true);
                Image isAddIcon = null;
                try {
                    isAddIcon = new Image(new FileInputStream(Constants.resourcePath+ File.separator+"images"+File.separator+"arrow_right.png"),20,20,false,false);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ImageView imageView = new ImageView(isAddIcon);

                Label isAddLabel = new Label();
                isAddLabel.setStyle("-fx-padding: 5 0 0 10");
                if(item.getIsAdd().equals("1")) {
                    isAddLabel.setGraphic(new ImageView(isAddIcon));
                }

                listHBox.getChildren().addAll(prodTitle, isAddLabel);

                setGraphic(listHBox);
            }
        }
    }


}
