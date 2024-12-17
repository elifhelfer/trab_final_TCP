package com.GUI.Main;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.swing.*;

import MusicConverter.MidiEventsToSequence;
import MusicFile.SaveAsMIDI;
import MusicMaker.MidiValues;
import MusicMaker.MusicParser;
import MusicMaker.TrackData;
import MusicPlayer.MusicPlayer;
import com.GUI.swing.Panel;
import com.GUI.form.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends javax.swing.JFrame {
    private Font customFont;
    private JPanel MainPanel;
    private Instruments[] Instrument;
    private JFrame frame;
    private JScrollPane scrollPlane;
    private JLabel title; // title

    private JButton playButton;
    private JButton stopButton;
    private JButton saveButton;
    private JTextArea saveFileName;

    private JPanel drumsetRules; // Panel contendo informacoes sobre o uso de drumset
    private JLabel drumsetTitle;
    private JLabel drumsetDescription; // Descricao em si

    private MusicPlayer musicPlayer;
    private ArrayList<ArrayList<MidiEvent>> midiEventsList;

    private final String FILE_SAVE_PATH = "./save/";

    public Main() {
        initExtras(); // inits the font
        initFrame(); //
        initMainPanel();
        initInstruments();
        initScrollPane();
        initButtons();

        handleActions();

        frame.add(scrollPlane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // inits every part of the GUI
    void initExtras(){
        File fontFile = new File("fonts/fonteManeira.ttf");  // Adjust path
        try {
            this.customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (Exception e) {
            System.out.println("Font not found");
        }

    }
    void initFrame(){
        frame = new JFrame();
        frame.setTitle("MUSGA");
        frame.setSize(1463,900);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }
    void initMainPanel(){
        MainPanel = new Panel();
        MainPanel.setLayout(null);
        MainPanel.setPreferredSize(new Dimension(1463, 3500));
        title = new JLabel();
        title.setText("Musga!");
        title.setBounds(400, 0, 1463, 310);
        customFont = customFont.deriveFont(Font.PLAIN, 144);
        title.setFont(this.customFont);
        title.setForeground(new Color(0, 0, 0));
        MainPanel.add(title);
    }
    void initButtons(){
        playButton = new JButton();
        playButton.setText("Play");
        playButton.setBounds(900,200,150,50);

        stopButton = new JButton();
        stopButton.setText("Stop");
        stopButton.setBounds(900,270,150,50);

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.setBounds(900,340,150,50);

        saveFileName = new JTextArea();
        saveFileName.setText("Enter Filename");
        saveFileName.setBounds(900, 400, 150, 20);

        MainPanel.add(saveFileName);
        MainPanel.add(playButton);
        MainPanel.add(stopButton);
        MainPanel.add(saveButton);
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


    void handleActions(){
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Play");
                if(musicPlayer == null || !musicPlayer.getIsPlaying().get()) {
                    playMusic();
                }
            }
        });

        // Configura o botão Save
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save");

                String fileName = saveFileName.getText();

                if (!fileName.isEmpty()) {
                    Sequence sequence = getSequenceFromData();
                    SaveAsMIDI.save(fileName, FILE_SAVE_PATH, sequence);
                }
            }
        });


        // Configura o botão Stop
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((musicPlayer != null) && (musicPlayer.getIsPlaying().get()))
                    musicPlayer.stopPlaying();
            }
        });
    }

    private void playMusic() {
        try {
            Sequence sequence = getSequenceFromData();
            musicPlayer = new MusicPlayer(sequence);
            musicPlayer.startPlaying();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addToMidiEventsList( String inputText, int channel, int inputTextDuration, String instrument){
        TrackData trackdata = new TrackData(inputText, channel, instrument, inputTextDuration);
        midiEventsList.add(MusicParser.listMidiEvents(trackdata));
    }

    private Sequence getSequenceFromData(){        //Gets info from all text boxes, converts them with the parser, and makes them into a sequence
        midiEventsList = new ArrayList<>();

        for(int i = 0; i < Instrument.length; i++){
            addToMidiEventsList(Instrument[i].getInstrumentBox(), i+1, Instrument[i].getNoteDuration(), Instrument[i].getCurrentInstrument());
        }

        Sequence sequence = MidiEventsToSequence.convert(midiEventsList);
        return sequence;
    }

    public static void main(String[] args) {
        Main mainApp = new Main();
    }

}
