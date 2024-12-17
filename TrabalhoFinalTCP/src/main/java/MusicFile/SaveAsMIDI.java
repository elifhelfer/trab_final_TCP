package MusicFile;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveAsMIDI {
    public static void save(String fileName, String fileSavePath, Sequence sequence) {
        if (!fileName.isEmpty()) {
            Path save_dir_path = Paths.get(fileSavePath);
            File save_file_path = new File(fileSavePath + fileName + ".midi");
            try {
                if (!Files.exists(save_dir_path)) {
                    Files.createDirectories(save_dir_path);
                    System.out.println("Directory created: " + save_dir_path);
                }
                MidiSystem.write(sequence, 1, save_file_path);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
