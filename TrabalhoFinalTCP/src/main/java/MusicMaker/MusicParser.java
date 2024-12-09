package MusicMaker;

import javax.sound.midi.*;
import java.util.*;

public class MusicParser {
    // Module constants
    private static final int OCTAVE_OFFSET = 12;

    // TODO take these items as class parameters
    private static final int DEFAULT_TICK = 4;
    private static final int DEFAULT_VELOCITY = 100;
    private static final int BPM_INCREASE = 80;
    private static final int DEFAULT_BPM = 60;
    private static final int CHANNEL = 1;

    public ArrayList<MidiEvent> listMidiEvents(String input_text) {
        // state variables
        int current_BPM = 120;
        int current_volume = 30;

        ArrayList<MidiEvent> midi_list = new ArrayList<MidiEvent>();
        // invalid current input == 0;
        int current_note = 0;
        int str_index = 0;
        int current_tick = 0;

        while (str_index < input_text.length()) {
            // TODO fix tick offset
            try {
                // cases related with bpm
                if (input_text.startsWith("BPM+",str_index)) {
                    // changes bpm
                    current_BPM += BPM_INCREASE;
                    midi_list.add(changeBpm(current_tick, BPM_INCREASE, current_BPM));
                    current_note = 0;
                    str_index += "BPM+".length();
                }
                else if (input_text.startsWith(";",str_index)){
                    // Chooses random value to bpm
                    Random random = new Random();
                    // random bpm is between 60 and 180
                    current_BPM = DEFAULT_BPM + random.nextInt(121);
                    midi_list.add(changeBpm(DEFAULT_TICK, BPM_INCREASE, current_BPM));
                    current_note = 0;
                    str_index++;
                }
                // cases related with volume
                else if (input_text.startsWith("+",str_index)){
                    // doubles volume
                    current_note = 0;
                    str_index++;
                }
                else if (input_text.startsWith("-",str_index)){
                    // lowers volume back to normal
                    current_note = 0;
                    str_index++;
                }
                // cases related with change in instrument or timbre
                else if (input_text.startsWith("\n",str_index)){
                    // changes instrument
                    str_index++;
                }
                else if ("OUI".contains(input_text.substring(str_index,str_index+1).toUpperCase()) && (current_note == 0)){
                    // plays telephone sound
                    str_index++;
                }
                // cases related with change in note
                else if (input_text.startsWith("R+",str_index)){
                    // one octave higher
                    if (current_note != 0 && (current_note + OCTAVE_OFFSET) <= 127) {
                        current_note = current_note + OCTAVE_OFFSET;
                    }
                    else {
                        current_note = 0;
                    }
                    str_index += "R+".length();
                }
                else if (input_text.startsWith("R-",str_index)){
                    // one octave lower
                    if ((current_note - OCTAVE_OFFSET) > 0) {
                        current_note = current_note - OCTAVE_OFFSET;
                    }
                    str_index += "R-".length();
                }
                else if (input_text.startsWith("?",str_index)){
                    // Chooses random value to notes
                    current_note = MidiValues.getNoteValue("", true);
                    str_index++;
                }
                else if (input_text.toUpperCase().startsWith(" ",str_index)){
                    // adds pause
                    str_index++;
                }
                else if ("OUI".contains(input_text.substring(str_index,str_index+1).toUpperCase()) && (current_note != 0)){
                    //repeats note
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

    private MidiEvent makeEvent(int command, int channel, int note, int velocity, int tick) {
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

    // private MidiEvent changeInstrument

    // changeOctave


    // random BPM



}
