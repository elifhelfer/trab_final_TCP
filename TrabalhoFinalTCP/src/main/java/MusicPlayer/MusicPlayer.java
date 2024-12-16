package MusicPlayer;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.sound.midi.*;

public class MusicPlayer implements Runnable{
    private final AtomicBoolean isPlaying = new AtomicBoolean(false);
    private Sequence song;
    private Sequencer sequencer;

    public MusicPlayer(Sequence newSong) {
        this.song = newSong;
    }

    public AtomicBoolean getIsPlaying(){
        return this.isPlaying;
    }

    public void startPlaying() {
        if (isPlaying.get()) {
            return; // Already playing
        }
        isPlaying.set(true);
        new Thread(this).start(); // Start the thread
    }

    public void stopPlaying() {
        isPlaying.set(false); // Stop playback
        if (sequencer != null && sequencer.isOpen()) {
            sequencer.stop();
            sequencer.close();
        }
    }

    private void playMusic() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setTempoInBPM(120);
            sequencer.setSequence(song);

            sequencer.start();

            // Wait until the sequencer is running
            while (isPlaying.get() && sequencer.isRunning()) {
                Thread.sleep(500); // Check every 100ms
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Consider logging this instead
        } finally {
            if (sequencer != null && sequencer.isOpen()) {
                sequencer.close(); // Ensure resources are released
            }
            isPlaying.set(false); // Reset the flag
        }
    }

    @Override
    public void run() {
        playMusic();
    }
}


