package com.states.util;

import javax.swing.*;
import java.io.File;

/**
 * Created by e604845 on 6/29/2017.
 */
public class Constants {
    public static final Boolean isLocalTest = Boolean.TRUE; //本地测试
    public static final String currentProjectPath = System.getProperty("user.dir");
    public static final String resourcePath = currentProjectPath+File.separator+"src"+File.separator+"resources";
    public static final ImageIcon ISADDICON = new ImageIcon(Constants.currentProjectPath+ File.separator+"src"+File.separator+"resources"+File.separator+"array_right.png");

}
