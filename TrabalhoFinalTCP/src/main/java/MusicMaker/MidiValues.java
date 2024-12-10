package MusicMaker;

import java.util.Random;

public class MidiValues {

    // MIDI Commands
    public static final int NOTE_OFF = 128;         // 0x80
    public static final int NOTE_ON = 144;         // 0x90
    public static final int CONTROL_CHANGE = 176;  // 0xB0
    public static final int PROGRAM_CHANGE = 192;  // 0xC0

    // MIDI values for natural notes (A to G) in octave 4
    private static final int A = 69;
    private static final int B = 71;
    private static final int C = 60;
    private static final int D = 62;
    private static final int E = 64;
    private static final int F = 65;
    private static final int G = 67;

    // MIDI program numbers for some common instruments
    private static final int PIANO = 0;       // Acoustic Grand Piano
    private static final int GUITAR = 24;     // Acoustic Guitar (nylon)
    private static final int VIOLIN = 40;     // Violin
    private static final int FLUTE = 73;      // Flute
    private static final int TRUMPET = 56;    // Trumpet
    private static final int SAXOPHONE = 65;  // Alto Saxophone
    private static final int CELLO = 42;      // Cello
    private static final int CLARINET = 71;   // Clarinet
    private static final int ORGAN = 19;      // Church Organ

    // Array of all instrument values for random selection
    private static final int[] INSTRUMENTS = {
            PIANO, GUITAR, VIOLIN, FLUTE, TRUMPET,
            SAXOPHONE, CELLO, CLARINET, ORGAN
    };

    // Method to get the MIDI value of a specific instrument
    // Main method for testing

    // Method to get the note value

    public static int getNoteValue(String note, boolean random) {
        // Return a random note if requested
        if (random) {
            return getRandomNote();
        }

        // Map note letters to their MIDI values
        return switch (note.toUpperCase()) {
            case "A" -> A;
            case "B" -> B;
            case "C" -> C;
            case "D" -> D;
            case "E" -> E;
            case "F" -> F;
            case "G" -> G;
            default -> throw new IllegalArgumentException("Invalid note: " + note);
        };
    }
    // Method to get a random note value

    private static int getRandomNote() {
        int[] naturalNotes = {A, B, C, D, E, F, G};
        Random random = new Random();
        return naturalNotes[random.nextInt(naturalNotes.length)];
    }

    public static int getInstrumentValue(String instrument, boolean random) {
        if (random) {
            return getRandomInstrument();
        }

        return switch (instrument.toUpperCase()) {
            case "PIANO" -> PIANO;
            case "GUITAR" -> GUITAR;
            case "VIOLIN" -> VIOLIN;
            case "FLUTE" -> FLUTE;
            case "TRUMPET" -> TRUMPET;
            case "SAXOPHONE" -> SAXOPHONE;
            case "CELLO" -> CELLO;
            case "CLARINET" -> CLARINET;
            case "ORGAN" -> ORGAN;
            default -> throw new IllegalArgumentException("Invalid instrument: " + instrument);
        };
    }
    // Method to get a random instrument value
    private static int getRandomInstrument() {
        Random random = new Random();
        return INSTRUMENTS[random.nextInt(INSTRUMENTS.length)];
    }

    // Main for testing
    public static void main(String[] args) {
        // Test specific notes
        System.out.println("Note C: " + MidiValues.getNoteValue("C", false));
        System.out.println("Note A: " + MidiValues.getNoteValue("A", false));

        // Test random note
        System.out.println("Random Note: " + MidiValues.getNoteValue("", true));

        // Test random Instrument
        System.out.println("Guitar: " + getInstrumentValue("GUITAR", false));

        // Test random instrument
        System.out.println("Random Instrument: " + getInstrumentValue("", true));
    }
}

