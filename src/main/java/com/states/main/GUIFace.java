package com.states.main;

import com.states.db.ProductDBRunner;
import com.states.entity.ProductEntity;
import com.states.util.Constants;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by e604845 on 7/5/2017.
 */
public class GUIFace {

    JFrame mainFrame;
    ProductDBRunner productDBRunner = new ProductDBRunner();
    public GUIFace(){}

    public void initAndShow(){
        initGUI();
        this.mainFrame.pack();
        this.mainFrame.setVisible(true);
    }
    public void initGUI(){
        mainFrame = new JFrame("AliCrawler");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container mainPanel = mainFrame.getContentPane();
        mainFrame.setSize(new Dimension(500, 700));
        JPanel detailPanel = createDetailPanel();
        JPanel prodSelectPanel = createProdSelectPanel();
        JPanel commandPanel = createCommandPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();

        mainPanel.setLayout(gridBagLayout);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        mainPanel.add(detailPanel,gridBagConstraints);


        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        mainPanel.add(prodSelectPanel,gridBagConstraints);


        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth=2;
        mainPanel.add(commandPanel,gridBagConstraints);
    }
    private JPanel createDetailPanel(){
        JPanel dPanel = new JPanel();
        dPanel.setPreferredSize(new Dimension(500, 600));
        dPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        dPanel.add(new JLabel("DetailPanel"));
        return  dPanel;
    }

    private JPanel createProdSelectPanel(){
        JPanel pPanel = new JPanel();
        pPanel.setPreferredSize(new Dimension(200, 600));
        pPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        pPanel.setLayout(new BorderLayout());
        List<ProductEntity> productNameList = productDBRunner.getProdNameList();

        Collections.sort(productNameList, new Comparator<ProductEntity>() {
            @Override
            public int compare(ProductEntity o1, ProductEntity o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        DefaultListModel<ProductEntity> l1 = new DefaultListModel<>();
        for(ProductEntity item : productNameList) {
            l1.addElement(item);
        }
        JList prodList = new JList(l1);

        prodList.setCellRenderer(new ProductCellListRenderer());
//
       pPanel.add(prodList, BorderLayout.CENTER);
        return  pPanel;
    }

    private JPanel createCommandPanel(){
        JPanel cPanel = new JPanel();
        cPanel.setPreferredSize(new Dimension(500, 100));
        cPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel s = new JLabel("CommandPanel");
        s.setBackground(Color.green);
        s.setForeground(Color.red);

        s.setIcon(Constants.ISADDICON);
        cPanel.add(s);
        return  cPanel;
    }


    private Component getCompByName(Container container ,String name){
        if(container == null ) return null;
        for(Component comp : container.getComponents()){
            if(comp.getName().equals(name)){
                return  comp;
            }
        }
        return null;
    }
}
