package crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileHelper {

    public File createFile(int i) {
        File file = new File("src/main/resources/bodies/text" + (i + 1) + ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void writeToIndexTXT(List<String> list) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("src/main/resources/index.txt");
            for (String str : list) {
                writer.write(str);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeToFile(File file, String body) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(body);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
