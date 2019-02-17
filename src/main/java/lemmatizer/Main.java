package lemmatizer;

import helpers.FileHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) throws IOException {
        FileHelper helper = new FileHelper();
        File[] bodiesFiles = helper.getAllFilesFromBodies();
        StanfordLemmatizer slem = new StanfordLemmatizer();

        for (File file : bodiesFiles) {
            String filename = "lemm" + file.getName();
            helper.writeToFile(helper.createFileLemmasPath(filename), slem.lemmatize(String.valueOf(Files.readAllLines(file.toPath()))));
        }
    }
}
