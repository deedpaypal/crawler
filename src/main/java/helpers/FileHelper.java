package helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileHelper {

    public File createFileBodiesPath(int i) {
        File file = new File("src/main/resources/bodies/text" + (i + 1) + ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File createFileLemmasPath(String name) {
        File file = new File("src/main/resources/lemmBodies/" + name);
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

    public File[] getAllFilesFromBodies() {
        File folder = new File("src/main/resources/bodies");
        File[] listOfFiles = folder.listFiles();

        return listOfFiles;
    }

    public String readFromFile(File file) throws IOException {
        BufferedReader abc = new BufferedReader(new FileReader(file));
        List<String> data = new ArrayList<String>();
        String s;
        while ((s = abc.readLine()) != null) {
            data.add(s);
        }
        abc.close();
        String joined = String.join("", data);
        return joined;
    }

    public LinkedList<String> getLinks() throws IOException {
        LinkedList links = new LinkedList();
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/index.txt"));
        String line = reader.readLine();
        while (line != null){
            links.add(line);
            line = reader.readLine();
        }
        return links;
    }
}

