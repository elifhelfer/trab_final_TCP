import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class FileReaderFromExplorerTest{

    FileReaderFromExplorer filereader = new FileReaderFromExplorer();
    String path = null;


    @Test
    public void chooseNormalTxt(){
        try {
            String content = filereader.readFileFromExplorer();
            if(filereader != null) {
                path = filereader.getThisFilePath();
            }
            int extensionIndex = 0;
            if (path != null)
                extensionIndex = path.lastIndexOf(".");
            if (extensionIndex != -1 && extensionIndex != 0) {
                path = path.substring(extensionIndex + 1);
            }
            assertEquals("txt", path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void cancelOperation(){
        try {
            String content = filereader.readFileFromExplorer();
            if(filereader != null) {
                path = filereader.getThisFilePath();
            }
            int extensionIndex = 0;
            if (path != null)
                extensionIndex = path.lastIndexOf(".");
            if (extensionIndex != -1 && extensionIndex != 0) {
                path = path.substring(extensionIndex + 1);
            }
            assertEquals(null, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}