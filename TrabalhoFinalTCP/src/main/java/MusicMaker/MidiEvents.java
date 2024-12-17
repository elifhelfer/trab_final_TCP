package MusicMaker;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.*;

public class MidiEvents {

    private static MidiEvent makeEvent(int command, int channel, int note, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage message = new ShortMessage();
            message.setMessage(command, channel, note, MidiValues.VELOCITY);
            event = new MidiEvent(message, tick);
        } catch (Exception ex) {
            // TODO catch exception
        }
        return event;
    }

    public static void addNoteEvent(ArrayList<MidiEvent> midiList, TrackData track_info, ParserState state) {
        MidiEvent noteOn = makeEvent(MidiValues.NOTE_ON, track_info.getChannel(), state.getNote(), state.getTick());
        MidiEvent noteOff = makeEvent(MidiValues.NOTE_OFF, track_info.getChannel(), state.getNote(), state.getTick() + track_info.getNote_duration()); // 128 = NOTE_OFF
        midiList.add(noteOn);
        midiList.add(noteOff);
    }

    public static MidiEvent changeBpm(ParserState state) {
        int mpq = 60000000 / state.getBpm();
        byte[] data = {(byte) (mpq >> 16), (byte) (mpq >> 8), (byte) mpq};
        MetaMessage tempoChange = new MetaMessage();
        MidiEvent event = null;
        try {
            tempoChange.setMessage(0x51, data, data.length);
            event = new MidiEvent(tempoChange, state.getTick());
        } catch (Exception ex) {
            // TODO catch exception
        }
        return event;
    }

    public static MidiEvent changeVolume(ParserState state, TrackData track_info, int default_volume, boolean doubleVolume) {
        int newVolume = doubleVolume ? Math.min(state.getVolume() * 2, MidiValues.MAX_VOLUME) : default_volume;
        state.setVolume(newVolume);
        MidiEvent event = null;
        try {
            ShortMessage volumeChange = new ShortMessage();
            volumeChange.setMessage(MidiValues.CONTROL_CHANGE + track_info.getChannel(), 7, newVolume);
            event = new MidiEvent(volumeChange, state.getTick());
        } catch (Exception ex) {
            // TODO catch exception
        }
        return event;
    }

    public static MidiEvent changeInstrument(ParserState state, TrackData track_info) {
        MidiEvent event = null;
        try {
            ShortMessage programChange = new ShortMessage();
            programChange.setMessage(MidiValues.PROGRAM_CHANGE, track_info.getChannel(), state.getInstrument(), 0); // 192 = Program Change
            event = new MidiEvent(programChange, state.getTick());
        } catch (Exception ex) {
            // TODO implement exception
        }
        return event;
    }
}