package MusicMaker;

public class TrackData {
    private final String input_text;
    private final int channel, midi_instrument;

    TrackData(String input_text, int channel, int midi_instrument) {
        this.input_text = input_text;
        this.channel = channel;
        this.midi_instrument = midi_instrument;
    }

    public int getChannel() {
        return channel;
    }

    public int getMidi_instrument() {
        return midi_instrument;
    }

    public String getInput_text() {
        return input_text;
    }
}
