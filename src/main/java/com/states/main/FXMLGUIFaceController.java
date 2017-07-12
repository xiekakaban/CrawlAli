package com.states.main;

import com.states.util.Constants;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

/**
 * Created by e604845 on 7/12/2017.
 */
public class FXMLGUIFaceController{

    @FXML public Label titleDispaly;

    public FXMLGUIFaceController() {
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Alibaba Crawler");

        Parent root = FXMLLoader.load(new URL("file:///"+Constants.currentProjectPath+File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+File.separator+"states"+File.separator+"main"+File.separator+"GUIFace.fxml"));
        titleDispaly.setText("RTB");
        Scene scene = new Scene(root,800,750);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
