package com.GUI.form;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;

public class Instruments extends JPanel {
    private JTextArea InstrumentBox;
    private JComboBox currentInstrument;
    private JPanel InstrumentPanel;


    String[] instruments = {
            "PIANO",
            "GUITAR",
            "VIOLIN",
            "FLUTE",
            "TRUMPET",
            "SAXOPHONE",
            "CELLO",
            "CLARINET",
            "ORGAN"
    };

    public Instruments() {}

    public Instruments(int x, int y) {
        // Initialize components
        this.InstrumentBox = new JTextArea();
        this.currentInstrument = new JComboBox<>();

        // Set JComboBox editable
        this.currentInstrument.setEditable(false);

        // Set layout to null for manual positioning
        InstrumentPanel = new JPanel();
        InstrumentPanel.setLayout(null);

        // Set bounds for components
        this.currentInstrument.setBounds(90, 25, 150, 25);
        this.InstrumentBox.setBounds(20, 50, 300, 150);

        // Add components to the panel
        this.add(currentInstrument);
        this.add(InstrumentBox);

        // Set size and add InstrumentPanel to Instruments
        this.setLayout(null);
        InstrumentPanel.setBounds(0, 0, 340, 300);
        InstrumentPanel.setBorder(new LineBorder(Color.black, 3, true));
        this.add(InstrumentPanel);
        this.setSize(200, 100);
        setInstruments();
        setInstrumentBox();
    }

    private void setInstruments(){
        currentInstrument.setModel(new DefaultComboBoxModel<>(instruments));
        currentInstrument.setBackground(new Color(254, 138, 128, 160));
        currentInstrument.setFont(new Font("sansserif", Font.BOLD, 12));
    }

    private void setInstrumentBox(){
        InstrumentBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.ORANGE),
                BorderFactory.createEmptyBorder(2, 2, 0, 0) // Top, left, bottom, right padding
        ));
        InstrumentBox.setLineWrap(true);
        InstrumentBox.setWrapStyleWord(true);
    }

}
