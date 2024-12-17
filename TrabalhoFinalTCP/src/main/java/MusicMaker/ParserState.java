package MusicMaker;

import java.util.Random;

public class ParserState {
    private int tick, bpm, octave, note, volume, instrument;
    public ParserState() {
        this.tick = MidiValues.START_TICK;
        this.bpm = MidiValues.DEFAULT_BPM;
        this.note = MidiValues.INVALID_NOTE;
        this.volume = MidiValues.DEFAULT_VOLUME;
        this.octave = MidiValues.DEFAULT_OCTAVE;
        this.instrument = MidiValues.DEFAULT_INSTRUMENT;
    }

    public int getBpm() {
        return bpm;
    }

    public int getNote() {
        return this.note + (MidiValues.OCTAVE_OFFSET * this.octave);
    }

    public int getTick() {
        return tick;
    }

    public int getOctave() {
        return octave;
    }

    public int getVolume() {
        return volume;
    }

    public int getInstrument() {
        return instrument;
    }

    public void setInstrument(int instrument) {
        this.instrument = instrument;
    }

    public void setBpm(int bpm) {
        if (bpm > MidiValues.MIN_BPM)
            this.bpm = bpm;
    }

    public void incBpm() {
        this.bpm += MidiValues.BPM_INCREASE;
    }

    public void setRandomBpm() {
        Random rand = new Random();
        this.bpm = rand.nextInt(181) + 60;
    }

    public void setNote(int note) {
        this.note = note;
    }
    public void setInvNote(){
        this.note = MidiValues.INVALID_NOTE;
    }

    public void incOctave() {
        if (octave + 1 < MidiValues.MAX_OCTAVE)
            this.octave++;
    }

    public void decOctave() {
        if (octave - 1 >= MidiValues.MIN_OCTAVE) {
            this.octave --;
        }
    }

    public void initTick() {
        this.tick = MidiValues.DEFAULT_TICK;
    }

    public void incTick() {
        this.tick += MidiValues.DEFAULT_TICK;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}