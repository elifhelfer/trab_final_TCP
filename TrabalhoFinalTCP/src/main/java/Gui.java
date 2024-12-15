import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.*;

import MusicConverter.MidiEventsToSequence;
import MusicMaker.MusicParser;
import MusicMaker.TrackData;
import MusicPlayer.MusicPlayer;

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

    private Sequencer sequencer;

    private MusicPlayer musicPlayer;

    public Gui() {
        // Adiciona opções aos dropdowns
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
        comboBox1.setModel(new DefaultComboBoxModel<>(instruments));
        comboBox2.setModel(new DefaultComboBoxModel<>(instruments));
        comboBox3.setModel(new DefaultComboBoxModel<>(instruments));
        comboBox4.setModel(new DefaultComboBoxModel<>(instruments));

        // Configura o botão Play
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Play");
                playMusic();
            }
        });

        // Configura o botão Pause
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (sequencer != null && sequencer.isRunning()) {
//                    System.out.println("Pause");
//                    sequencer.stop();
//                }
                musicPlayer.stopPlaying();
            }
        });

        // Configura o botão Save
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save");
            }
        });

        // Configura o upload de arquivo .txt
        uploadButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Upload 1");
                try {

                }
                catch (Exception ex) {

                }
            }
        });
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Upload 2");
            }
        });
        uploadButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Upload 3");
            }
        });
        uploadButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Upload 4");
            }
        });
    }

    private void playMusic() {
        try {
            ArrayList<ArrayList<MidiEvent>> midiEventsList = new ArrayList<>();

            String inputText1 = textField1.getText();
            String inputText2 = textField2.getText();
            String inputText3 = textField3.getText();
            String inputText4 = textField4.getText();

            String intrument1 = comboBox1.getSelectedItem().toString();
            String intrument2 = comboBox2.getSelectedItem().toString();
            String intrument3 = comboBox3.getSelectedItem().toString();
            String intrument4 = comboBox4.getSelectedItem().toString();

            TrackData trackdata1 = new TrackData(inputText1, 1, intrument1);
            TrackData trackdata2 = new TrackData(inputText2, 2, intrument2);
            TrackData trackdata3 = new TrackData(inputText3, 3, intrument3);
            TrackData trackdata4 = new TrackData(inputText4, 4, intrument4);

            midiEventsList.add(MusicParser.listMidiEvents(trackdata1));
            midiEventsList.add(MusicParser.listMidiEvents(trackdata2));
            midiEventsList.add(MusicParser.listMidiEvents(trackdata3));
            midiEventsList.add(MusicParser.listMidiEvents(trackdata4));

            Sequence sequence = MidiEventsToSequence.convert(midiEventsList);

            musicPlayer = new MusicPlayer(sequence);

            musicPlayer.startPlaying();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Music Player");
        frame.setContentPane(new Gui().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
