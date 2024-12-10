package MusicMaker;

import javax.sound.midi.*;
import java.util.*;

public class MusicParser {
    // Module constants
    private static final int OCTAVE_OFFSET = 12;
    private static final int HIGHEST_NOTE = 127;
    private static final int LOWEST_NOTE = 0;
    private static final int DEFAULT_TICK = 4;
    private static final int DEFAULT_VELOCITY = 100;
    private static final int BPM_INCREASE = 80;
    private static final int DEFAULT_BPM = 120;
    private static final int DEFAULT_VOLUME = 30;
    private static final int MAX_VOLUME = 127;
    private static final int CHANNEL = 1;

    public ArrayList<MidiEvent> listMidiEvents(String inputText) {
        ArrayList<MidiEvent> midiList = new ArrayList<>();
        int currentBPM = DEFAULT_BPM;
        int currentVolume = DEFAULT_VOLUME;

        boolean wasNote = false;
        int currentTick = 4;
        int strIndex = 0;
        while (strIndex < inputText.length()) {
            try {
                int caseType = findCase(inputText, strIndex, wasNote);
                int midiValue = 0;
                String note;
                switch (caseType) {
                    case 0: // Increase BPM
                        midiList.add(changeBpm(currentTick, BPM_INCREASE, currentBPM));
                        currentBPM += BPM_INCREASE;
                        wasNote = false;
                        strIndex += "BPM+".length();
                        break;

                    case 1: // Up an octave
                        note = inputText.substring(strIndex, strIndex + 1).toUpperCase();
                        midiValue = MidiValues.getNoteValue(note, false); // Assuming MidiValues class
                        if (midiValue + OCTAVE_OFFSET <= HIGHEST_NOTE)
                            midiValue += OCTAVE_OFFSET;
                        addNoteEvent(midiList, midiValue, CHANNEL, DEFAULT_VELOCITY, currentTick);
                        currentTick += DEFAULT_TICK; // Increment for the next event
                        wasNote = true;
                        strIndex += "R+".length();
                        break;

                    case 2: // Down an octave
                        note = inputText.substring(strIndex, strIndex + 1).toUpperCase();
                        midiValue = MidiValues.getNoteValue(note, false); // Assuming MidiValues class
                        if (midiValue - OCTAVE_OFFSET >= 0)
                            midiValue -= OCTAVE_OFFSET;
                        addNoteEvent(midiList, midiValue, CHANNEL, DEFAULT_VELOCITY, currentTick);
                        wasNote = true;
                        strIndex += "R-".length();
                        break;

                    case 3: // Play a note
                        note = inputText.substring(strIndex, strIndex + 1).toUpperCase();
                        midiValue = MidiValues.getNoteValue(note, false); // Assuming MidiValues class
                        addNoteEvent(midiList, midiValue, CHANNEL, DEFAULT_VELOCITY, currentTick);
                        currentTick += DEFAULT_TICK; // Increment for the next event
                        wasNote = true;
                        strIndex++;
                        break;

                    case 4: // Play a random note
                        int randomNote = MidiValues.getNoteValue("", true); // Assuming MidiValues class
                        addNoteEvent(midiList, randomNote, CHANNEL, DEFAULT_VELOCITY, currentTick);
                        currentTick += DEFAULT_TICK;
                        wasNote = true;
                        strIndex++;
                        break;

                    case 5: // Repeat last note
                        addNoteEvent(midiList, midiValue, CHANNEL, DEFAULT_VELOCITY, currentTick);
                        currentTick += DEFAULT_TICK;
                        wasNote = true;
                        strIndex++;
                        break;

                    case 6: // Play Telephone sound
                        int telephoneNote = 81; // MIDI value for Telephone
                        addNoteEvent(midiList, telephoneNote, 10, DEFAULT_VELOCITY, currentTick); // Channel 9
                        currentTick += DEFAULT_TICK;
                        strIndex++;
                        break;

                    case 7: // Random BPM
                        int randomBPM = getRandomBPM();
                        midiList.add(changeBpm(currentTick, randomBPM, 0));
                        currentBPM = randomBPM;
                        strIndex++;
                        break;

                    case 8: // Double volume
                        currentVolume = Math.min(currentVolume * 2, MAX_VOLUME);
                        midiList.add(changeVolume(currentTick, CHANNEL, currentVolume, true));
                        strIndex++;
                        break;

                    case 9: // Default volume
                        currentVolume = DEFAULT_VOLUME;
                        midiList.add(changeVolume(currentTick, CHANNEL, currentVolume, false));
                        strIndex++;
                        break;

                    case 10: // Add a pause
                        currentTick += DEFAULT_TICK; // Skip ticks for a pause
                        strIndex++;
                        break;

                    case 11: // Change instrument
                        int randomInstrument = MidiValues.getInstrumentValue("", true);
                        midiList.add(changeInstrument(currentTick, randomInstrument));
                        strIndex++;
                        break;

                    default: // Unknown character, skip
                        strIndex++;
                        break;
                }
            } catch (Exception ex) {
                // TODO catch exception
            }
        }

        return midiList;
    }

    private int findCase(String text, int offset, boolean wasNote) {
        String currentChar = text.substring(offset, offset + 1).toUpperCase();
        if (text.startsWith("BPM+", offset)) return 0;
        else if (text.startsWith("R+", offset)) return 1;
        else if (text.startsWith("R-", offset)) return 2;
        else if ("ABCDEFG".contains(currentChar)) return 3;
        else if ("?".equals(currentChar)) return 4;
        else if ("OUI".contains(currentChar)) return wasNote ? 5 : 6;
        else if (";".equals(currentChar)) return 7;
        else if ("+".equals(currentChar)) return 8;
        else if ("-".equals(currentChar)) return 9;
        else if (" ".equals(currentChar)) return 10;
        else if ("\n".equals(currentChar)) return 11;
        else return 12;
    }

    private int getRandomBPM() {
        Random rand = new Random();
        return rand.nextInt(181) + 60; // Random BPM between 60 and 240
    }

    private void addNoteEvent(ArrayList<MidiEvent> midiList, int note, int channel, int velocity, int tick) {
        MidiEvent noteOn = makeEvent(MidiValues.NOTE_ON, channel, note, velocity, tick); // 144 = NOTE_ON
        MidiEvent noteOff = makeEvent(MidiValues.NOTE_OFF, channel, note, velocity, tick + 2); // 128 = NOTE_OFF
        midiList.add(noteOn);
        midiList.add(noteOff);
    }

    private MidiEvent makeEvent(int command, int channel, int note, int velocity, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage message = new ShortMessage();
            message.setMessage(command, channel, note, velocity);
            event = new MidiEvent(message, tick);
        } catch (Exception ex) {
            // TODO catch exception
        }
        return event;
    }

    private MidiEvent changeBpm(long tick, int incrementBpm, int currentBpm) {
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

    private MidiEvent changeVolume(long tick, int channel, int currentVolume, boolean doubleVolume) {
        int newVolume = doubleVolume ? Math.min(currentVolume * 2, MAX_VOLUME) : currentVolume;
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

    private MidiEvent changeInstrument(long tick, int instrument) {
        MidiEvent event = null;
        try {
            ShortMessage programChange = new ShortMessage();
            programChange.setMessage(MidiValues.PROGRAM_CHANGE, CHANNEL, instrument, 0); // 192 = Program Change
            event = new MidiEvent(programChange, tick);
        } catch (Exception ex) {
            // TODO implement
        }
        return event;
    }
}
