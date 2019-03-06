package inverted_index;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nurie on 27.02.2019.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        InvertedIndex index = new InvertedIndex();
        List names = getNames();
        System.out.println(index.buildIndex(names));

        System.out.println("Print search phrase: ");
        String phrase = in.nextLine();
        index.find(phrase);
    }

    public static LinkedList<String> getNames() throws IOException {
        LinkedList links = new LinkedList();
        LinkedList names = new LinkedList();
        File folder = new File("src/main/resources/lemmBodies");
        for (File file: folder.listFiles()){
            names.add(file.getName());
        }
        return names;
    }
}
