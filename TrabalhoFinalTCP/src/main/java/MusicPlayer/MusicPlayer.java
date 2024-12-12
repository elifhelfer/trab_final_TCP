package MusicPlayer;

import MusicMaker.MusicParser;

import javax.sound.midi.*;
import java.util.*;

import static java.lang.Thread.sleep;

public class MusicPlayer implements Runnable{
    private boolean isPlaying;
    private Sequence song;

    public MusicPlayer(Sequence newSong){
        this.song = newSong;
        this.isPlaying = false;
    }

    public void startPlaying() {
        isPlaying = true;
        new Thread(this).start(); // Start the thread
    }

    public void stopPlaying() {
        isPlaying = false; // Stop playback
    }

    public static void PlayMusic(){
        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Set the BPM of the sequencer
            sequencer.setTempoInBPM(120);

            // Assign the sequence to the sequencer and start it
            sequencer.setSequence(sequence);
            sequencer.start();

            while(sequencer.isRunning()){
                sleep(600)
            }

            sequencer.stop();
        }
        catch(Exception ex){
            //to be added
        }
    }

    @Override
    public void run() {
        for (String soundFile : soundFiles) {
            if (!isPlaying) {
                break; // Exit if playback is stopped
            }
            playSound(soundFile);
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


