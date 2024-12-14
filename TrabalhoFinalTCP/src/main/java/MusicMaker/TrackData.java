package MusicMaker;

public class TrackData {
    private final String input_text, midi_instrument;
    private final int channel;

    public TrackData(String input_text, int channel, String midi_instrument) {
        this.input_text = input_text;
        this.channel = channel;
        this.midi_instrument = midi_instrument;
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
}
