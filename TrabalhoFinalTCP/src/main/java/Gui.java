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
import FileReaderFromExplorer.FileReaderFromExplorer;

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

    private Sequence getSequenceFromData(){        //Gets info from all text boxes, converts them with the parser, and makes them into a sequence
        ArrayList<ArrayList<MidiEvent>> midiEventsList = new ArrayList<>();

        String inputText1 = textField1.getText();
        String inputText2 = textField2.getText();
        String inputText3 = textField3.getText();
        String inputText4 = textField4.getText();

        int inputTextDuration1 = Integer.parseInt(textFieldDuration1.getText());
        int inputTextDuration2 = Integer.parseInt(textFieldDuration2.getText());
        int inputTextDuration3 = Integer.parseInt(textFieldDuration3.getText());
        int inputTextDuration4 = Integer.parseInt(textFieldDuration4.getText());

        String intrument1 = comboBox1.getSelectedItem().toString();
        String intrument2 = comboBox2.getSelectedItem().toString();
        String intrument3 = comboBox3.getSelectedItem().toString();
        String intrument4 = comboBox4.getSelectedItem().toString();

        TrackData trackdata1 = new TrackData(inputText1, 1, intrument1, inputTextDuration1);
        TrackData trackdata2 = new TrackData(inputText2, 2, intrument2, inputTextDuration2);
        TrackData trackdata3 = new TrackData(inputText3, 3, intrument3, inputTextDuration3);
        TrackData trackdata4 = new TrackData(inputText4, MidiValues.PERCUSSION_CHANNEL, intrument4, inputTextDuration4); // Bateria

        midiEventsList.add(MusicParser.listMidiEvents(trackdata1));
        midiEventsList.add(MusicParser.listMidiEvents(trackdata2));
        midiEventsList.add(MusicParser.listMidiEvents(trackdata3));
        midiEventsList.add(MusicParser.listMidiEvents(trackdata4));

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
