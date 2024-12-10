import MusicMaker.MusicParser;
import javax.sound.midi.*;
import java.util.*;

public class TESTE {

    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);

        //System.out.println("Enter the input text to play (e.g., R+ABCDEFG BPM+): ");
        //String inputText = in.nextLine();
        String inputText = "aR+aR-aBPM+aR+aR-aBPM+aR+aR-aR+aR-a";
        MusicParser parser = new MusicParser();
        ArrayList<MidiEvent> midiEvents = parser.listMidiEvents(inputText);

        try {
            // Get a Sequencer instance and open it
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Create a new Sequence with Pulse Per Tick (PPQ) set to 4
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            // Add parsed MIDI events to the track
            for (MidiEvent event : midiEvents) {
                track.add(event);
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
