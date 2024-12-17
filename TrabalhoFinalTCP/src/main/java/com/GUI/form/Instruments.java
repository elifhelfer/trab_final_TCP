package com.GUI.form;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instruments extends JPanel {
    private JTextArea InstrumentBox;
    private JComboBox currentInstrument;
    private JPanel InstrumentPanel;
    private JButton uploadButton;
    private JTextField noteDuration;
    private JLabel noteDurationLabel;

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

        this.uploadButton = new JButton("Upload");
        this.noteDuration = new JTextField();
        this.noteDurationLabel = new JLabel();
        this.noteDurationLabel.setText("Note Duration:");

        // Set bounds for components
        this.currentInstrument.setBounds(20, 25, 150, 25); // ComboBox with all instruments
        this.InstrumentBox.setBounds(20, 50, 800, 200); // TextBox
        this.uploadButton.setBounds(200, 25, 100, 25);
        this.noteDuration.setBounds(460, 25, 150, 25);
        this.noteDurationLabel.setBounds(360, 25, 150, 25);

        // Add components to the panel
        this.add(currentInstrument);
        this.add(InstrumentBox);
        this.add(noteDuration);
        this.add(noteDurationLabel);
        this.add(uploadButton);

        // Set size and add InstrumentPanel to Instruments
        this.setLayout(null);
        InstrumentPanel.setBounds(0, 0, 840, 300);
        InstrumentPanel.setBorder(new LineBorder(Color.black, 3, true));
        this.add(InstrumentPanel);
        this.setSize(800, 100);
        setInstruments();
        setInstrumentBox();

        handleActions();

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

    void handleActions(){
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Upload");
                try {

                }
                catch (Exception ex) {

                }
            }
        });
    }

}
