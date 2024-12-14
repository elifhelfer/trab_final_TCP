package MusicPlayer;

import MusicMaker.MusicParser;

import javax.sound.midi.*;
import java.util.*;

import static java.lang.Thread.sleep;

public class MusicPlayer implements Runnable{
    private boolean isPlaying;
    private Sequence song;
    private Sequencer sequencer;

    public MusicPlayer(Sequence newSong){
        this.song = newSong;
        this.isPlaying = false;
    }

    public void startPlaying() {
        if (isPlaying){
            return;
        }
        isPlaying = true;
        new Thread(this).start(); // Start the thread
    }

    public void stopPlaying() {
        isPlaying = false; // Stop playback

        if (sequencer != null && sequencer.isOpen()){
            sequencer.stop();
            sequencer.close();
        }
    }

    private void PlayMusic(){
        try{
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setTempoInBPM(120);
            sequencer.setSequence(song);

            sequencer.start();
            isPlaying = true;

            while(sequencer.isRunning()){
                sleep(600);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            if (sequencer != null && sequencer.isOpen()) {
                sequencer.close(); // Ensure resources are released
            }
            isPlaying = false; // Reset the flag
        }
    }

    @Override
    public void run() {
        PlayMusic();
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

            MusicPlayer musicPlayer = new MusicPlayer(sequence);
            musicPlayer.startPlaying();
            sleep(2000);
            musicPlayer.stopPlaying();

        }catch(Exception ex){
            //nothing here yet
        }
    }
}


