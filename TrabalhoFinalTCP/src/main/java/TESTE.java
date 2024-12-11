import MusicMaker.MusicParser;
import javax.sound.midi.*;
import java.util.*;

public class TESTE {

    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);

        //System.out.println("Enter the input text to play (e.g., R+ABCDEFG BPM+): ");
        //String inputText = in.nextLine();
        String inputText = "o?oBPM+abcR+abcR+abcR-;abcBPM+abcoiu?";
        String inputText2 = "";
        MusicParser parser = new MusicParser(1);
        MusicParser parser2 = new MusicParser(2);
        ArrayList<MidiEvent> midiEvents = parser.listMidiEvents(inputText);
        ArrayList<MidiEvent> midiEvents2 = parser2.listMidiEvents(inputText2);

        try {
            // Get a Sequencer instance and open it
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Create a new Sequence with Pulse Per Tick (PPQ) set to 4
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

            // Set the BPM of the sequencer
            sequencer.setTempoInBPM(120);

            // Assign the sequence to the sequencer and start it
            sequencer.setSequence(sequence);
            sequencer.start();

            // Keep the program running until the sequencer stops
            while (sequencer.isRunning()) {
                int i = 0;
            }
            sequencer.stop();
            sequencer.close();

        } catch (Exception ex) {
            // TODO catch exception
        }
    }
}
