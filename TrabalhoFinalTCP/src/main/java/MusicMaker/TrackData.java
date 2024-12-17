package MusicMaker;

public class TrackData {
    private final String input_text, midi_instrument;
    private final int note_duration;
    private int channel;
    public TrackData(String input_text, int channel, String midi_instrument, int note_duration) {
        this.input_text = input_text.toUpperCase();
        this.channel = channel;
        this.note_duration = note_duration;
        this.midi_instrument = midi_instrument.toUpperCase();
    }

    public int getChannel() {
        return this.channel;
    }

    public String getMidi_instrument() {
        return this.midi_instrument;
    }

    public String getInput_text() {
        return this.input_text;
    }

    public int getNote_duration() {
        return note_duration;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}