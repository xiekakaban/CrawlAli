package com.states.main;

import com.states.entity.ProductEntity;
import com.states.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by e604845 on 7/5/2017.
 */
public class ProductCellListRenderer extends JLabel implements ListCellRenderer{
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ProductEntity prodCell = (ProductEntity)(value);
        this.setText(prodCell.getTitle().substring(0,10));
        this.setName(prodCell.getId());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        if("0".equals(prodCell.getIsAdd())){
            setIcon(Constants.ISADDICON);
        }
        if(cellHasFocus){
            setForeground(Color.red);
        }else{
            setForeground(Color.black);
        }
        return this;
    }
}
