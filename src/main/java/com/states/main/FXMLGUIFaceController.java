package com.states.main;

import com.states.entity.ProductEntity;
import com.states.util.Constants;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;

/**
 * Created by e604845 on 7/12/2017.
 */
public class FXMLGUIFaceController{

    private MainPanel mainPanel;
    @FXML public Label titleDisplay;
    @FXML public ListView<ProductEntity> prodListDisplay;

    public FXMLGUIFaceController() {

    }

    @FXML
    private void initialize(){
        prodListDisplay.setItems(mainPanel.getObservableProductList());
        prodListDisplay.setCellFactory(new Callback<ListView<ProductEntity>, ListCell<ProductEntity>>() {
            @Override
            public ListCell<ProductEntity> call(ListView<ProductEntity> param) {
                return null;
            }
        });
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



    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

    }
}
