package com.GUI.form;

import MusicFile.FileReaderFromExplorer;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
            "ORGAN",
            "DRUMSET"
    };

    public Instruments() {}

    public Instruments(int x, int y) {
        setPanelAndElements();
        setInstruments();
        setInstrumentBox();

        handleActions();

    }
    private void setPanelAndElements(){
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
        this.noteDuration.setText("40");

        // Set bounds for components
        this.currentInstrument.setBounds(20, 25, 150, 25); // ComboBox with all instruments
        this.InstrumentBox.setBounds(20, 50, 800, 80); // TextBox
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
        InstrumentPanel.setBounds(0, 0, 840, 150);
        InstrumentPanel.setBorder(new LineBorder(Color.lightGray   , 2, true));
        InstrumentPanel.setBackground(new Color(240, 245, 250));
        this.add(InstrumentPanel);
        this.setSize(800, 100);
    }

    private void setInstruments(){
        currentInstrument.setModel(new DefaultComboBoxModel<>(instruments));
        currentInstrument.setBackground(new Color(181, 212, 244, 255));
        currentInstrument.setFont(new Font("sansserif", Font.BOLD, 12));
    }

    private void setInstrumentBox(){
        InstrumentBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(94, 91, 91)),
                BorderFactory.createEmptyBorder(2, 2, 0, 0) // Top, left, bottom, right padding
        ));
        InstrumentBox.setLineWrap(true);
        InstrumentBox.setWrapStyleWord(true);
    }

    private void handleActions(){
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Upload");
                try {
                    String inputFromExplorer = FileReaderFromExplorer.readFileFromExplorer();
                    InstrumentBox.setText(inputFromExplorer);
                }
                catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        });

    }

    public String getInstrumentBox(){
        return InstrumentBox.getText();
    }

    public int getNoteDuration(){
        return Integer.parseInt(noteDuration.getText());
    }

    public String getCurrentInstrument(){
        return currentInstrument.getSelectedItem().toString();
    }
}
