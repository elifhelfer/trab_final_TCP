package MusicMaker;
import java.util.*;

public class MidiValues {
    // Constants for MIDI
    public static final int NOTE_ON = 144;    // Command for Note On
    public static final int NOTE_OFF = 128;   // Command for Note Off
    public static final int CONTROL_CHANGE = 176; // Command for Control Change
    public static final int PROGRAM_CHANGE = 192; // Command for Program Change
    public static final int DEFAULT_BPM = 60;
    public static final int BPM_INCREASE = 80;
    public static final int DEFAULT_VOLUME = 100;
    public static final int MAX_VOLUME = 127;
    public static final int MIN_VOLUME = 0;
    public static final int DEFAULT_TICK = 4;
    public static final int OCTAVE_OFFSET = 12;
    public static final int HIGHEST_NOTE = 127;
    public static final int LOWEST_NOTE = 0;
    public static final int VELOCITY = 125;
    public static final int NOTE_DURATION = 15;

    // HashMaps for Notes and Commands
    public static final Map<String, Integer> NOTE_MAP = new HashMap<>();
    public static final Map<String, Integer> INSTRUMENT_MAP = new HashMap<>();
    public static final Map<String, Integer> PERCUSSION_MAP = new HashMap<>();

    // Static block to initialize the maps
    static {

        NOTE_MAP.put("C", 60);
        NOTE_MAP.put("C#", 61);
        NOTE_MAP.put("D", 62);
        NOTE_MAP.put("D#", 63);
        NOTE_MAP.put("E", 64);
        NOTE_MAP.put("F", 65);
        NOTE_MAP.put("F#", 66);
        NOTE_MAP.put("G", 67);
        NOTE_MAP.put("G#", 68);
        NOTE_MAP.put("A", 69);
        NOTE_MAP.put("A#", 70);
        NOTE_MAP.put("B", 71);

        INSTRUMENT_MAP.put("PIANO", 1);
        INSTRUMENT_MAP.put("GUITAR", 25);
        INSTRUMENT_MAP.put("VIOLIN", 40);
        INSTRUMENT_MAP.put("FLUTE", 74);
        INSTRUMENT_MAP.put("TRUMPET", 56);
        INSTRUMENT_MAP.put("SAXOPHONE", 65);
        INSTRUMENT_MAP.put("CELLO", 42);
        INSTRUMENT_MAP.put("CLARINET", 57);
        INSTRUMENT_MAP.put("ORGAN", 19);
        INSTRUMENT_MAP.put("MARIMBA", 88);
        INSTRUMENT_MAP.put("XYLOPHONE", 96);

        // Initializing percussion instruments with their General MIDI values
        PERCUSSION_MAP.put("DRUMS", 128);
        PERCUSSION_MAP.put("TAMBOURINE", 127);
        PERCUSSION_MAP.put("SNARE", 38);
        PERCUSSION_MAP.put("HI-HAT", 42);
        PERCUSSION_MAP.put("CYMBAL", 49);
    }

    // Method to get MIDI value for a note
    public static int getNoteValue(String note_str, int current_octave,boolean random) {
        if (random) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(NOTE_MAP.size());
            return current_octave + (int) NOTE_MAP.values().toArray()[randomIndex];
        }
        return current_octave + NOTE_MAP.get(note_str.toUpperCase());
    }

    // Method to get random instrument value
    public static int getInstrumentValue(String instrument, boolean random) {
        if (random) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(INSTRUMENT_MAP.size());
            return (int) INSTRUMENT_MAP.values().toArray()[randomIndex];
        }
        return INSTRUMENT_MAP.get(instrument.toUpperCase());
    }
}

