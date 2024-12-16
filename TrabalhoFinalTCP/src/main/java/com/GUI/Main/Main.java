package com.GUI.Main;

import javax.swing.*;

import com.GUI.swing.Panel;
import com.GUI.form.*;

import java.awt.*;

public class Main extends javax.swing.JFrame {
    private int height;
    private int width;
    private JPanel MainPanel;
    private Instruments Instrument_1;


    public Main() {
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        this.MainPanel = new Panel();
        this.MainPanel.setLayout(null);

        this.Instrument_1 = new Instruments(10, 30);
        Instrument_1.setBounds(100,100,340,300);

        this.MainPanel.add(Instrument_1);

        initComponents();
        setContentPane(MainPanel);
        pack();
        setVisible(true);
    }

    private void initComponents() {

        setResizable(false);


        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1168, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 605, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setSize(900, 600);
    }

}
