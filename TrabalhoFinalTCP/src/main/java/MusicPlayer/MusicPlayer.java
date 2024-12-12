package MusicPlayer;

import MusicMaker.MusicParser;

import javax.sound.midi.*;
import java.util.*;

import static java.lang.Thread.sleep;

public class MusicPlayer {

    public static void PlayMusic(Sequence sequence){
        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Set the BPM of the sequencer
            sequencer.setTempoInBPM(120);

            // Assign the sequence to the sequencer and start it
            sequencer.setSequence(sequence);
            sequencer.start();

            while (sequencer.isRunning()) {
                sleep(1000);
            }
            sequencer.stop();
        }
        catch(Exception ex){
            //to be added
        }
    }

    public static void main(String[] args){
        String inputText = "ao?oBPM+abcR+abcR+abcR-;abcBPM+abcoiu?";
        String inputText2 = "";
        MusicParser parser = new MusicParser(1,160);
        MusicParser parser2 = new MusicParser(2,60);
        ArrayList<MidiEvent> midiEvents = parser.listMidiEvents(inputText);
        ArrayList<MidiEvent> midiEvents2 = parser2.listMidiEvents(inputText2);

        try{
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();
            Track track2 = sequence.createTrack();
            // Add parsed MIDI events to the track
            for (MidiEvent event : midiEvents) {
                track.add(event);
            }
            for (MidiEvent event : midiEvents2) {
                track2.add(event);
            }

            MusicPlayer.PlayMusic(sequence);

        }catch(Exception ex){
            //nothing here yet
        }
    }
}


