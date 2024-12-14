import MusicMaker.*;
import MusicConverter.*;
import MusicPlayer.*;
import javax.sound.midi.*;
import java.util.*;

public class newTest {

    public static void main(String[] args) {

        ArrayList<ArrayList<MidiEvent>> midiEvents = new ArrayList<>();
        String inputText = "aaaaaaa";
        String inputText2 = "accccccc";

        MusicParser parser = new MusicParser(1,160);

        midiEvents.add(parser.listMidiEvents(inputText));
        midiEvents.add(parser.listMidiEvents(inputText2));

        Sequence sequence = MidiEventsToSequence.convert(midiEvents);

        MusicPlayer player = new MusicPlayer(sequence);
        player.startPlaying();
    }
}