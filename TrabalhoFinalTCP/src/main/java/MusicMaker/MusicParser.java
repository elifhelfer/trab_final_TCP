package MusicMaker;

import javax.sound.midi.*;
import java.util.*;

public class MusicParser {

    public static ArrayList<MidiEvent> listMidiEvents(TrackData track_info) {
        ArrayList<MidiEvent> midi_list = new ArrayList<>();

        // setting default values for bpm, instrument and volume :
        int default_instrument = MidiValues.getInstrumentValue(track_info.getMidi_instrument(), false);
        midi_list.add(MidiEvents.changeInstrument(0, track_info.getChannel(), default_instrument));
        midi_list.add(MidiEvents.changeVolume(0, track_info.getChannel(), 0, MidiValues.DEFAULT_VOLUME, false));

        int tick = 4;
        int current_bpm = MidiValues.DEFAULT_BPM;
        int current_octave = 0;
        int current_note = -1;
        int current_volume = MidiValues.DEFAULT_VOLUME;

        String input_text = track_info.getInput_text().toUpperCase();

        int i = 0;
        while (i < input_text.length()) {
            if (isBpmIncrease(input_text, i)) {
                midi_list.add(MidiEvents.changeBpm(tick, MidiValues.BPM_INCREASE, current_bpm));
                current_bpm += MidiValues.BPM_INCREASE;
                System.out.println("1");
                i += "BPM+".length();
            }
            else if (isBpmRandom(input_text, i)) {
                current_bpm = MidiEvents.getRandomBPM();
                midi_list.add(MidiEvents.changeBpm(tick, 0, current_bpm));
                i++;
            }
            else if (isSharpNote(input_text, i)) {
                current_note = MidiValues.getNoteValue(input_text.substring(i,i + "x#".length()), current_octave, false);
                MidiEvents.addNoteEvent(midi_list,current_note,track_info.getChannel(), tick);
                tick += MidiValues.DEFAULT_TICK;
                i += "x#".length();
            }
            else if (isNote(input_text, i)) {
                current_note = MidiValues.getNoteValue(input_text.substring(i,i + "x".length()), current_octave, false);
                MidiEvents.addNoteEvent(midi_list,current_note,track_info.getChannel(), tick);
                tick += MidiValues.DEFAULT_TICK;
                i++;
            }
            else if (isOctaveChange(input_text, i)) {
                if ("R+".equals(input_text.substring(i,i+2))) {
                    if (current_note + ((current_octave+1) * MidiValues.OCTAVE_OFFSET) <= MidiValues.HIGHEST_NOTE)
                        current_octave++;
                }
                else {
                    if (current_note - ((current_octave-1) * MidiValues.OCTAVE_OFFSET) >= MidiValues.LOWEST_NOTE)
                        current_octave--;
                }
                i += "Rx".length();
            }
            else if (isInstrumentChange(input_text, i)) {
                int instrument = MidiValues.getInstrumentValue("", true);
                midi_list.add(MidiEvents.changeInstrument(tick, track_info.getChannel(), instrument));
                i++;
            }
            else if (isRandomNote(input_text, i)) {
                current_note = MidiValues.getNoteValue("",current_octave,true);
                MidiEvents.addNoteEvent(midi_list,current_note,track_info.getChannel(), tick);
                tick += MidiValues.DEFAULT_TICK;
                i++;
            }
            else if (isVolumeChange(input_text, i)) {
                midi_list.add(MidiEvents.changeVolume(tick, track_info.getChannel(), current_volume,  MidiValues.DEFAULT_VOLUME, input_text.charAt(i) == '+'));
                System.out.println("changed_volume");
                i++;
            }
            else if (isLastNote(input_text, i)) {
                if (current_note != -1) {
                    MidiEvents.addNoteEvent(midi_list,current_note,track_info.getChannel(), tick);
                }
                tick += MidiValues.DEFAULT_TICK;
                i++;
            }
            else if (isPause(input_text, i)){
                tick += MidiValues.DEFAULT_TICK;
                i++;
            }
            else {
                i++;
            }
        }

        return midi_list;
    }

    static private boolean isNote(String input_text, int i) {
        return (i < input_text.length()) && "ABCDEFG".contains(input_text.substring(i, i + 1));
    }

    static private boolean isSharpNote(String input_text, int i) {
        return (i + 1 < input_text.length()) && "C#D#F#G#A#".contains(input_text.substring(i, i + 2));
    }

    static private boolean isBpmIncrease(String input_text, int i) {
        return input_text.startsWith("BPM+", i);
    }

    static private boolean isOctaveChange(String input_text, int i) {
        return (i + 1 <= input_text.length()) && (input_text.startsWith("R+", i) || input_text.startsWith("R-", i));
    }

    static private boolean isInstrumentChange(String input_text, int i) {
        return input_text.startsWith("\n", i);
    }

    static boolean isPause(String input_text, int i) {
        return (i < input_text.length()) && input_text.charAt(i) == ' ';
    }

    static boolean isBpmRandom(String input_text, int i) {
        return (i < input_text.length()) && input_text.charAt(i) == ';';
    }

    static boolean isRandomNote(String input_text, int i) {
        return (i < input_text.length()) && input_text.charAt(i) == '?';
    }

    static boolean isVolumeChange(String input_text, int i) {
        return (i < input_text.length()) && ((input_text.charAt(i) == '+' || input_text.charAt(i) == '-'));
    }

    static boolean isLastNote(String input_text, int i) {
        return (i < input_text.length()) && "IUO".contains(input_text.substring(i, i + 1));
    }
}
