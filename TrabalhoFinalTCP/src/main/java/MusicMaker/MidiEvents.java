package MusicMaker;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.ArrayList;
import java.util.Random;

public class MidiEvents {

    public static void addNoteEvent(ArrayList<MidiEvent> midiList, int note, int channel, int tick) {
        MidiEvent noteOn = makeEvent(MidiValues.NOTE_ON, channel, note, tick); // 144 = NOTE_ON
        MidiEvent noteOff = makeEvent(MidiValues.NOTE_OFF, channel, note, tick + MidiValues.NOTE_DURATION); // 128 = NOTE_OFF
        midiList.add(noteOn);
        midiList.add(noteOff);
    }

    public static MidiEvent makeEvent(int command, int channel, int note, int tick) {
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

    public static MidiEvent changeBpm(int tick, int incrementBpm, int currentBpm) {
        int newBpm = currentBpm + incrementBpm;
        int mpq = 60000000 / newBpm;
        byte[] data = {(byte) (mpq >> 16), (byte) (mpq >> 8), (byte) mpq};
        MetaMessage tempoChange = new MetaMessage();
        MidiEvent event = null;
        try {
            tempoChange.setMessage(0x51, data, data.length);
            event = new MidiEvent(tempoChange, tick);
        } catch (Exception ex) {
            // TODO catch exception
        }
        return event;
    }

    public static int getRandomBPM() {
        Random rand = new Random();
        return rand.nextInt(181) + 60; // Random BPM between 60 and 240
    }

    public static MidiEvent changeVolume(int tick, int channel, int currentVolume, int default_volume, boolean doubleVolume) {
        int newVolume = doubleVolume ? Math.min(currentVolume * 2, MidiValues.MAX_VOLUME) : default_volume;
        MidiEvent event = null;
        try {
            ShortMessage volumeChange = new ShortMessage();
            volumeChange.setMessage(MidiValues.CONTROL_CHANGE + channel, 7, newVolume);
            event = new MidiEvent(volumeChange, tick);
        } catch (Exception ex) {
            // TODO catch exception
        }
        return event;
    }

    public static MidiEvent changeInstrument(int tick, int channel ,int instrument) {
        MidiEvent event = null;
        try {
            ShortMessage programChange = new ShortMessage();
            programChange.setMessage(MidiValues.PROGRAM_CHANGE, channel, instrument, 0); // 192 = Program Change
            event = new MidiEvent(programChange, tick);
        } catch (Exception ex) {
            // TODO implement
        }
        return event;
    }
}
