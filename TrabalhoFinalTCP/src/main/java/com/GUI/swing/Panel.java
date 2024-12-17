package com.GUI.swing;

import java.awt.*;
import javax.swing.JPanel;

public class Panel extends javax.swing.JPanel {
    public Panel() {
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D graphicsVar = (Graphics2D) graphics;
        graphicsVar.setColor(getBackground());
        graphicsVar.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0,0,Color.decode("#ACB6E5"),0,getHeight(),Color.decode("#74ebd5"));
        graphicsVar.setPaint(gp);
        graphicsVar.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        super.paintComponent(graphics);
    }


}
