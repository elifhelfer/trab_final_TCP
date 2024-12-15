package MusicConverter;
import javax.sound.midi.*;
import java.util.*;

public class MidiEventsToSequence {     //holds a sequence
    public static Sequence convert(ArrayList<ArrayList<MidiEvent>> midiEvents) { // adds track to sequence from list of midiEvents
        Sequence sequence = null; // Initialize sequence to null
        try {
            sequence = new Sequence(Sequence.PPQ, 4);
            for (ArrayList<MidiEvent> trackList : midiEvents) {
                Track track = sequence.createTrack();
                for (MidiEvent event : trackList) {
                    track.add(event);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return sequence; // Return the sequence (could be null if an exception occurred)
    }
}
