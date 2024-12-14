package MusicMaker;

import javax.sound.midi.*;
import java.util.*;

public class MusicParser {
    // Module constants
    int default_channel, default_bpm;
    public MusicParser(int channel, int default_bpm) {
        this.default_channel = channel;
        this.default_bpm = default_bpm;
    }

    public ArrayList<MidiEvent> listMidiEvents(String inputText) {
        ArrayList<MidiEvent> midiList = new ArrayList<>();
        int currentBPM = default_bpm;
        int currentVolume = MidiValues.DEFAULT_VOLUME;

        boolean wasNote = false;
        int currentTick = 4;
        int midiValue = 0;
        int strIndex = 0;
        int current_octave = 0;

        while (strIndex < inputText.length()) {
            try {
                int caseType = findCase(inputText, strIndex, wasNote);
                String note;
                switch (caseType) {
                    case 0: // Increase BPM+
                        midiList.add(changeBpm(currentTick, MidiValues.BPM_INCREASE, currentBPM));
                        currentBPM += MidiValues.BPM_INCREASE;
                        wasNote = false;
                        strIndex += "BPM+".length();
                        break;

                    case 1: // Up an octave
                        // TODO check if there was last note (edge case for first note)
                        if (midiValue + (current_octave + 1)*(MidiValues.OCTAVE_OFFSET) <= MidiValues.HIGHEST_NOTE) {
                            current_octave++;
                        }
                        strIndex += "R+".length();
                        break;

                    case 2: // Down an octave
                        System.out.println("inside case 2");
                        // TODO check if there was last note (edge case for first note)
                        if (midiValue - (current_octave - 1)*(MidiValues.OCTAVE_OFFSET) <= MidiValues.HIGHEST_NOTE)
                            current_octave--;
                        strIndex += "R-".length();
                        break;

                    case 3: // Play a note
                        note = inputText.substring(strIndex, strIndex + 1).toUpperCase();
                        midiValue = current_octave*MidiValues.OCTAVE_OFFSET + MidiValues.getNoteValue(note, false); // Assuming MidiValues class
                        addNoteEvent(midiList, midiValue, this.default_channel, MidiValues.DEFAULT_VELOCITY, currentTick);
                        currentTick += MidiValues.DEFAULT_TICK; // Increment for the next event
                        wasNote = true;
                        strIndex++;
                        break;

                    case 4: // Play a random note
                        int randomNote = MidiValues.getNoteValue("", true); // Assuming MidiValues class
                        addNoteEvent(midiList, randomNote, this.default_channel, MidiValues.DEFAULT_VELOCITY, currentTick);
                        currentTick += MidiValues.DEFAULT_TICK;
                        wasNote = true;
                        strIndex++;
                        break;

                    case 5: // Repeat last note
                        addNoteEvent(midiList, midiValue, this.default_channel, MidiValues.DEFAULT_VELOCITY, currentTick);
                        currentTick += MidiValues.DEFAULT_TICK;
                        wasNote = true;
                        strIndex++;
                        break;

                    case 6: // Play Telephone sound
                        int telephoneNote = 81; // MIDI value for Telephone
                        addNoteEvent(midiList, telephoneNote, 10, MidiValues.DEFAULT_VELOCITY, currentTick); // Channel 9
                        currentTick += MidiValues.DEFAULT_TICK;
                        strIndex++;
                        break;

                    case 7: // Random BPM
                        int randomBPM = getRandomBPM();
                        midiList.add(changeBpm(currentTick, randomBPM, 0));
                        currentBPM = randomBPM;
                        strIndex++;
                        break;

                    case 8: // Double volume
                        currentVolume = Math.min(currentVolume * 2, MidiValues.MAX_VOLUME);
                        midiList.add(changeVolume(currentTick, this.default_channel, currentVolume, true));
                        strIndex++;
                        break;

                    case 9: // Default volume
                        currentVolume = MidiValues.DEFAULT_VOLUME;
                        midiList.add(changeVolume(currentTick, this.default_channel, currentVolume, false));
                        strIndex++;
                        break;

                    case 10: // Add a pause
                        currentTick += MidiValues.DEFAULT_TICK; // Skip ticks for a pause
                        strIndex++;
                        break;

                    case 11: // Change instrument
                        int randomInstrument = MidiValues.getInstrumentValue("", true);
                        midiList.add(changeInstrument(currentTick, this.default_channel, randomInstrument));
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
        int newVolume = doubleVolume ? Math.min(currentVolume * 2, MidiValues.MAX_VOLUME) : currentVolume;
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

    private MidiEvent changeInstrument(long tick, int channel ,int instrument) {
        MidiEvent event = null;
        try {
            ShortMessage programChange = new ShortMessage();
            programChange.setMessage(MidiValues.PROGRAM_CHANGE, this.default_channel, instrument, 0); // 192 = Program Change
            event = new MidiEvent(programChange, tick);
        } catch (Exception ex) {
            // TODO implement
        }
        return event;
    }
}
