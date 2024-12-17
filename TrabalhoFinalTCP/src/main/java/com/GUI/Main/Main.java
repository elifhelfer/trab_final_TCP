package com.GUI.Main;

import javax.swing.*;

import com.GUI.swing.Panel;
import com.GUI.form.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main extends javax.swing.JFrame {
    private Font customFont;
    private JPanel MainPanel;
    private Instruments[] Instrument;
    private JFrame frame;
    private JScrollPane scrollPlane;
    private JLabel title; // title

    private JPanel drumsetRules; // Panel contendo informacoes sobre o uso de drumset
    private JLabel drumsetTitle;
    private JLabel drumsetDescription; // Descricao em si


    public Main() {
        initExtras(); // inits the font
        initFrame(); //
        initMainPanel();
        initInstruments();
        initScrollPane();

        //handleActions();

        frame.add(scrollPlane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // inits every part of the GUI
    void initExtras(){
        File fontFile = new File("fonts/Minecraft.ttf");  // Adjust path
        try {
            this.customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (Exception e) {
            System.out.println("Font not found");
        }

    }
    void initFrame(){
        frame = new JFrame();
        frame.setTitle("TCP*QuePariu");
        frame.setSize(1463,900);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }
    void initMainPanel(){
        MainPanel = new Panel();
        MainPanel.setLayout(null);
        MainPanel.setPreferredSize(new Dimension(1463, 3500));
        title = new JLabel();
        title.setText("TCP(um tapa amais que pariu)");
        title.setBounds(400, 0, 1463, 310);
        customFont = customFont.deriveFont(Font.PLAIN, 46);
        title.setFont(this.customFont);
        title.setForeground(Color.GREEN);
        MainPanel.add(title);
    }
    void initInstruments(){
        Instrument = new Instruments[10];
        for(int i = 0; i < 10 ; i++){
            Instrument[i] = new Instruments(10, 30);
            Instrument[i].setBounds(50,200 + i*300,840,300);
            MainPanel.add(Instrument[i]);
        }
    }
    void initScrollPane(){
        scrollPlane = new JScrollPane(MainPanel);

        scrollPlane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPlane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar scrollBar = scrollPlane.getVerticalScrollBar();
        scrollBar.setBackground(Color.ORANGE);

        JScrollBar verticalBar = scrollPlane.getVerticalScrollBar();

        verticalBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(228, 164, 135); // Thumb color
                this.trackColor = new Color(205, 248, 182);        // Track color
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button; // Remove arrows for cleaner look
            }
        });
    }


    public static void main(String[] args) {
        Main mainApp = new Main();
    }

}
