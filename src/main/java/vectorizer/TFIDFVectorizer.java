package vectorizer;

import helpers.FileHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TFIDFVectorizer {

    public double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / doc.size();
    }

    public double idf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return 1 + Math.log(docs.size() / n);
    }
    
    public double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);

    }

    public static void main(String[] args) throws IOException {
        FileHelper helper = new FileHelper();
        File folder = new File("src/main/resources/lemmBodies/");
        File[] listOfFiles = folder.listFiles();
        List<List<String>> documents = new ArrayList<>();
        List<String> firstDoc = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String text = helper.readFromFile(file);
                String[] words = text.split(" ");
                if (firstDoc.isEmpty()) {
                    firstDoc = new ArrayList<>(Arrays.asList(words));
                }

                List<String> lines = new ArrayList<>(Arrays.asList(words));
               // documents.add(new ArrayList<>(lines));
                documents.add(lines);
            }
        }


        TFIDFVectorizer vectorizer = new TFIDFVectorizer();
        double tfidf = vectorizer.tfIdf(firstDoc, documents, "Product");
        System.out.println("TF-IDF (baby) = " + tfidf);


    }

}
