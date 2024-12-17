import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

import MusicConverter.MidiEventsToSequence;
import MusicMaker.*;
import MusicMaker.TrackData;
import MusicPlayer.MusicPlayer;
import MusicFile.FileReaderFromExplorer;

import java.nio.file.*;

public class Gui {
    private JPanel mainPanel;
    private JTextField textField1;
    private JComboBox<String> comboBox1;
    private JTextField textField2;
    private JComboBox<String> comboBox2;
    private JTextField textField3;
    private JComboBox<String> comboBox3;
    private JTextField textField4;
    private JComboBox<String> comboBox4;
    private JButton playButton;
    private JButton pauseButton;
    private JButton uploadButton1;
    private JButton uploadButton2;
    private JButton uploadButton3;
    private JButton uploadButton4;
    private JButton saveButton;
    private JTextArea drumsetBASSDRUMXTextArea;
    private JTextField textFieldDuration1;
    private JTextField textFieldDuration2;
    private JTextField textFieldDuration3;
    private JTextField textFieldDuration4;
    private JTextField textFieldSaveFile;

    private final String FILE_SAVE_PATH = "./save/";

    private Sequencer sequencer;

    private MusicPlayer musicPlayer;

    public Gui() {
        // Adiciona opções aos dropdowns
        String[] instruments = {
                "PIANO",
                "GUITAR",
                "ELECTRIC_GUITAR",
                "VIOLIN",
                "FLUTE",
                "TRUMPET",
                "SAXOPHONE",
                "CELLO",
                "CLARINET",
                "ORGAN"
        };
        String[] instruments_drumset = {
                "DRUMSET"
        };

        comboBox1.setModel(new DefaultComboBoxModel<>(instruments));
        comboBox2.setModel(new DefaultComboBoxModel<>(instruments));
        comboBox3.setModel(new DefaultComboBoxModel<>(instruments));
        comboBox4.setModel(new DefaultComboBoxModel<>(instruments_drumset));

        // Configura o botão Play
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Play");
                if(musicPlayer == null || !musicPlayer.getIsPlaying().get()) {
                    playMusic();
                }
            }
        });

        // Configura o botão Stop
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((musicPlayer != null) && (musicPlayer.getIsPlaying().get()))
                    musicPlayer.stopPlaying();
            }
        });

        // Configura o botão Save
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //PUT ALL OF THIS IN A SEPARATE CLASS!!!! REFACTOR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
                // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                System.out.println("Save");

                String fileName = textFieldSaveFile.getText();

                if (!fileName.isEmpty()) {
                    Sequence sequence = getSequenceFromData();
                    Path save_dir_path = Paths.get(FILE_SAVE_PATH);
                    File save_file_path = new File(FILE_SAVE_PATH + fileName + ".midi");
                    try{
                        if(!Files.exists(save_dir_path)){
                            Files.createDirectories(save_dir_path);
                            System.out.println("Directory created: " + save_dir_path);
                        }
                        MidiSystem.write(sequence,1,save_file_path);

                    }catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Configura o upload de arquivo .txt
        uploadButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Upload 1");
                try {
                    String inputFromExplorer = FileReaderFromExplorer.readFileFromExplorer();
                    textField1.setText(inputFromExplorer);
                }
                catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        });

        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Upload 2");
                try {
                    String inputFromExplorer = FileReaderFromExplorer.readFileFromExplorer();
                    textField2.setText(inputFromExplorer);
                }
                catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        });

        uploadButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Upload 3");
                try {
                    String inputFromExplorer = FileReaderFromExplorer.readFileFromExplorer();
                    textField3.setText(inputFromExplorer);
                }
                catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        });

        uploadButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Upload 4");
                try {
                    String inputFromExplorer = FileReaderFromExplorer.readFileFromExplorer();
                    textField4.setText(inputFromExplorer);
                }
                catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
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

    private static void addToMidiEventsList(ArrayList<ArrayList<MidiEvent>> midiEventsList, String inputText, int channel, int inputTextDuration, String instrument){
        TrackData trackdata = new TrackData(inputText, channel, instrument, inputTextDuration);
        midiEventsList.add(MusicParser.listMidiEvents(trackdata));
    }

    private Sequence getSequenceFromData(){        //Gets info from all text boxes, converts them with the parser, and makes them into a sequence
        ArrayList<ArrayList<MidiEvent>> midiEventsList = new ArrayList<>();

        Gui.addToMidiEventsList(midiEventsList, textField1.getText(), 1, Integer.parseInt(textFieldDuration1.getText()), comboBox1.getSelectedItem().toString());
        Gui.addToMidiEventsList(midiEventsList, textField2.getText(), 2, Integer.parseInt(textFieldDuration2.getText()), comboBox2.getSelectedItem().toString());
        Gui.addToMidiEventsList(midiEventsList, textField3.getText(), 3, Integer.parseInt(textFieldDuration3.getText()), comboBox3.getSelectedItem().toString());
        Gui.addToMidiEventsList(midiEventsList, textField4.getText(), MidiValues.PERCUSSION_CHANNEL, Integer.parseInt(textFieldDuration4.getText()), comboBox4.getSelectedItem().toString());

        Sequence sequence = MidiEventsToSequence.convert(midiEventsList);

        return sequence;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Music Player");
        frame.setContentPane(new Gui().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
