package MusicMaker;

import javax.sound.midi.*;
import java.util.*;

public class MusicParser {

    public static ArrayList<MidiEvent> listMidiEvents(TrackData track_info) {
        ArrayList<MidiEvent> midi_list = new ArrayList<>();
        ParserState state = new ParserState();
        // set initial bpm, volume and corrects channel based on percussion:
        initMidiSeq(midi_list, state, track_info);
        state.incTick();

        int i = 0;
        while (i < track_info.getInput_text().length()) {
            i += processToken(i, midi_list, track_info, state);
        }
        return midi_list;
    }

    private static int processToken(int i, ArrayList<MidiEvent> midi_list, TrackData track_info, ParserState state) {
        int text_len = track_info.getInput_text().length();
        String text = track_info.getInput_text();
        String token;

        if (i + MidiValues.LEN4 <= text_len && "BPM+".equals(text.substring(i,i+MidiValues.LEN4))) {
            state.incBpm();
            midi_list.add(MidiEvents.changeBpm(state));
            return "BPM+".length();
        }
        if (i + MidiValues.LEN2 <= text_len) {
            token = track_info.getInput_text().substring(i, i + MidiValues.LEN2);
            if (token.equals("R+")) {
                state.incOctave();
                return MidiValues.LEN2;
            }
            else if (token.equals("R-")) {
                state.decOctave();
                return MidiValues.LEN2;
            }
            else if ("C#D#F#G#A#".contains(token) && track_info.getChannel() != MidiValues.PERCUSSION_CHANNEL) {
                state.setNote(MidiValues.getNoteValue(token, false));
                MidiEvents.addNoteEvent(midi_list, track_info, state);
                state.incTick();
                return MidiValues.LEN2;
            }
        }
        if (i < text_len) {
            token = track_info.getInput_text().substring(i,i+MidiValues.LEN1);
            if ("ABCDEFG?".contains(token) && track_info.getChannel() != MidiValues.PERCUSSION_CHANNEL) {
                if (token.equals("?"))
                    state.setNote(MidiValues.getNoteValue("", true));
                else {
                    state.setNote(MidiValues.getNoteValue(token, false));
                }
                state.incTick();
                MidiEvents.addNoteEvent(midi_list, track_info, state);
                return MidiValues.LEN1;
            }
            else if (";".equals(token)) {
                state.setRandomBpm();
                midi_list.add(MidiEvents.changeBpm(state));
                return MidiValues.LEN1;
            }
            else if ("-".equals(token) || "+".equals(token)) {
                midi_list.add(MidiEvents.changeVolume(state, track_info, MidiValues.DEFAULT_VOLUME, "+".equals(token)));
                return MidiValues.LEN1;
            }
            else if ("\n".equals(token) && track_info.getChannel() != MidiValues.PERCUSSION_CHANNEL) {
                state.setInstrument(MidiValues.getInstrumentValue("", true));
                midi_list.add(MidiEvents.changeInstrument(state, track_info));
                return MidiValues.LEN1;
            }
            else if (" ".equals(token)) {
                state.incTick();
                return MidiValues.LEN1;
            }
            else if ("XYZPQTV".contains(token)) {
                state.setNote(MidiValues.getPercussionValue(token, false));
                MidiEvents.addNoteEvent(midi_list, track_info, state);
                state.incTick();
                return MidiValues.LEN1;
            }
        }
        return MidiValues.LEN1; // Return the updated index.
    }

    private static void initMidiSeq(ArrayList<MidiEvent> midi_list, ParserState state, TrackData track_info) {
        midi_list.add(MidiEvents.changeVolume(state , track_info, MidiValues.DEFAULT_VOLUME, false));
        midi_list.add(MidiEvents.changeBpm(state));
        if (!(track_info.getMidi_instrument().equals("DRUMSET"))) {
            state.setInstrument(MidiValues.getInstrumentValue(track_info.getMidi_instrument(), false));
            midi_list.add(MidiEvents.changeInstrument(state, track_info));
        }
        // if the DRUMSET is selected, then channel must be PERCUSSION_CHANNEL
        else
            track_info.setChannel(MidiValues.PERCUSSION_CHANNEL);
    }
}