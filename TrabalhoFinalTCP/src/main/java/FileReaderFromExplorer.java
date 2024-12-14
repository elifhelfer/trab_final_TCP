import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileReaderFromExplorer {

    private Path filePath;

    FileReaderFromExplorer(){
        this.filePath = null;
    }


    private Path getFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);

        int user_option = fileChooser.showOpenDialog(null);

        if (user_option == JFileChooser.APPROVE_OPTION) {
            File chosenFile = fileChooser.getSelectedFile();
            Path chosenFilePath = chosenFile.toPath();
            System.out.println("The file you chose was: " + chosenFile.getPath());
            return chosenFilePath;

        } else if (user_option == JFileChooser.CANCEL_OPTION) {
            System.out.println("Operation Cancelled.");
            return null;
        }
        return null;
    }

    public String readFileFromExplorer() throws IOException {
        this.filePath = getFilePath();
        if(this.filePath == null){
            System.out.println("oi");
            return null;
        }
        return Files.readString(this.filePath);
    }

    public String getThisFilePath(){
        if(this.filePath != null)
            return this.filePath.toString();
        return null;
    }
}

