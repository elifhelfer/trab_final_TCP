package MusicMaker;

import javax.management.openmbean.InvalidOpenTypeException;
import javax.sound.midi.*;
import java.lang.reflect.Array;
import java.util.*;

public class MusicParser {
    // Module constants
    // TODO take these items as class parameters
    private static final int OCTAVE_OFFSET = 12;
    private static final int DEFAULT_TICK = 4;
    private static final int DEFAULT_VELOCITY = 100;
    private static final int BPM_INCREASE = 80;
    private static final int DEFAULT_BPM = 60;
    private static final int CHANNEL = 1;

    //

    private int findsOutCase (String text, int offset, boolean was_note) {
        String current_char = text.substring(offset,offset+1).toUpperCase();
        // Case Increase bpm by 80
        // case Plays telephone
        // case Add pause
        if (text.startsWith("BPM+",offset))
            return 0;
            // case Up by one octave
        else if (text.startsWith("R+",offset))
            return 1;
            // case Down by an octave
        else if (text.startsWith("R-",offset))
            return 2;
        // play note
        else if ("ABCDEFG".contains(current_char))
            return 3;
        // case Plays random note
        else if ("?".equals(current_char))
            return 4;
        // case Repeats note
        else if ("OUI".contains(current_char)){
            if (was_note)
                return 5;
            else
                return 6;
        }
        // Case Change bpm randomly
        else if (";".equals(current_char))
            return 7;
        // Case Doubles volume
        else if ("+".equals(current_char))
            return 8;
        // case Go back to default volume
        else if ("-".equals(current_char))
            return 9;
        // case adds pause
        else if (" ".equals(current_char))
            return 10;
        // case Change instrument
        else if ("\n".equals(current_char))
            return 11;
        // case None
        else
            return 12;
    }

    public ArrayList<MidiEvent> listMidiEvents(String input_text) {
        ArrayList<MidiEvent> midi_list = new ArrayList<MidiEvent>();
        int current_BPM = 120;
        int current_volume = 30;
        boolean is_note = false;

        int str_index = 0;
        int current_tick = 0;
        while (str_index < input_text.length()) {
            // TODO fix tick offset
            try {
                // cases related with bpm
                if (input_text.startsWith("BPM+",str_index)) {
                    // changes bpm
                    str_index += "BPM+".length();
                }
                else if (input_text.startsWith(";",str_index)){
                    // Chooses random value to bpm
                    str_index++;
                }
                // cases related with volume
                else if (input_text.startsWith("+",str_index) || input_text.startsWith("-",str_index)){
                    // doubles volume or goes back to default

                    str_index++;
                }
                // cases related with change in instrument or timbre
                else if (input_text.startsWith("\n",str_index)){
                    // changes instrument randomly
                    str_index++;
                }
                else if ("OUI".contains(input_text.substring(str_index,str_index+1).toUpperCase())){
                    // plays telephone sound if previous command wasn't note, otherwise,
                    str_index++;
                }
                // cases related with change in note
                else if (input_text.startsWith("R+",str_index) || input_text.startsWith("R-",str_index)){
                    // increase or decrease a semitone
                    str_index += "R+".length();
                }
                else if ("ABCDEFG".contains(input_text.substring(str_index,str_index+1).toUpperCase())){
                    // adds pause
                    str_index++;
                }
                else {
                    // doesn't add midi record. Just check next character
                    str_index++;
                }
            }
            catch (Exception ex){
                // TODO catch Exception
            }
        }
        return midi_list;
    }
    private void addNoteEvent(ArrayList<MidiEvent> midi_list, int note, int channel, int velocity, int tick) {


        MidiEvent note_on = makeEvent(MidiValues.NOTE_ON, note, channel, velocity, tick);
        // stops note after two ticks
        MidiEvent note_off = makeEvent(MidiValues.NOTE_OFF, note, channel, velocity, tick + 2);
        midi_list.add(note_on);
        midi_list.add(note_off);
    }

    private MidiEvent makeEvent(int command, int note, int channel, int velocity, int tick) {
        // velocity and tick remains constant
        // only channel one will be used
        MidiEvent event = null;
        try {
            ShortMessage aux = new ShortMessage();
            aux.setMessage(command, channel, note, velocity);
            event = new MidiEvent(aux, tick);
        }
        catch (Exception ex) {
            // TODO catch exception
        }
        return event;
    }

    private MidiEvent changeBpm(long tick, int incrementBpm, int currentBpm) {
        int newBpm = currentBpm + incrementBpm;
        // TODO Check valid BPM
        MidiEvent event = null;
        // Convert BPM to MPQ (Microseconds Per Quarter Note)
        int mpq = 60000000 / newBpm;
        // Create a byte array with the MPQ value
        byte[] data = {
                (byte) (mpq >> 16), // High byte
                (byte) (mpq >> 8),  // Middle byte
                (byte) mpq          // Low byte
        };
        // Create a MetaMessage with the new tempo
        MetaMessage tempoChange = new MetaMessage();
        try {
            tempoChange.setMessage(0x51, data, data.length);
            event = new MidiEvent(tempoChange, tick);
        }
        catch (Exception ex) {
            // TODO catch exception
        }
        return event;
    }

    private MidiEvent changeVolume(long tick, int channel, int currentVolume, boolean doubleVolume){
        // Calculate the new volume
        int newVolume = doubleVolume ? Math.min(currentVolume * 2, 127) : currentVolume;
        MidiEvent event = null;
        // Create a Control Change event to set volume
        ShortMessage volumeChange = new ShortMessage();
        try {
            volumeChange.setMessage(176 + channel, 7, newVolume); // 176 = Control Change + channel
            event = new MidiEvent(volumeChange, tick);
        }
        catch (Exception ex) {
            // TODO catch exception
        }
        return event;
    }

    //private MidiEvent changeInstrument() {}
}

