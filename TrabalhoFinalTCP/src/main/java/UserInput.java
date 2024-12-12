public class UserInput {
    private final String input_text;
    private final int default_bpm, default_channel, midi_instrument;

    UserInput(String input_text, int default_bpm, int default_channel, int midi_instrument) {
        this.input_text = input_text;
        this.default_bpm = default_bpm;
        this.default_channel = default_channel;
        this.midi_instrument = midi_instrument;
    }

    public int getDefault_bpm() {
        return default_bpm;
    }

    public int getDefault_channel() {
        return default_channel;
    }

    public int getMidi_instrument() {
        return midi_instrument;
    }

    public String getInput_text() {
        return input_text;
    }
}
