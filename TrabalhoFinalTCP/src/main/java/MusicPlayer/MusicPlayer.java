package MusicPlayer;

import javax.sound.midi.*;
import java.util.*;

public class MusicPlayer {
    private ArrayList<Track> tracks = null;
    private Sequencer sequence = null;

    public MusicPlayer(){
        this.sequence = new Sequence(Sequence.PPQ, 4);

        for (Track track : tracks){
            this.sequence.
        }

    }

    private void addTrack(ArrayList<MidiEvent> song){
        Track track = null;
        for (MidiEvent event : song){
            track.add(event);
        }
        this.tracks.add(track);
    }



}
