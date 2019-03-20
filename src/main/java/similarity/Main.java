package similarity;

import helpers.FileHelper;
import vectorizer.TFIDFVectorizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {

    private List<List<String>> documents = new ArrayList<>();
    private List<String> allTerms = new ArrayList<>();  // список всех слов во всех документах без повторений
    private List<double[]> tfidfDocsVector = new ArrayList<>();
    private FileHelper helper = new FileHelper();
    private TFIDFVectorizer vectorizer = new TFIDFVectorizer();
    private double[] requestVector;



    public void parseFiles(String filePath) throws FileNotFoundException, IOException { // "src/main/resources/lemmBodies/"
        File[] listOfFiles =  new File(filePath).listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String text = helper.readFromFile(file);
                List<String> words =  new ArrayList<>(Arrays.asList((text.split(" "))));
                for (String term : words) {
                    if (!allTerms.contains(term)) {
                        allTerms.add(term);
                    }
                }
                documents.add(words);
            }
        }
        System.out.println(documents);
        System.out.println(allTerms);
    }


    public void getDocsVector() {
        for (List<String> document : documents) {
            tfidfDocsVector.add(tfIdfCalculator(document));
        }
    }

    public void getRequestVector() {
        // TODO добавить лемматизацию запроса
        String request = "apple gorilla";
        List<String> requestDocument =  new ArrayList<>(Arrays.asList((request.split(" "))));
        requestVector = tfIdfCalculator(requestDocument);
    }

    public double[] tfIdfCalculator(List<String> document){
        double tfidf;
        double[] tfidfvectors = new double[allTerms.size()];
        int count = 0;
        for (String term : allTerms) {
            tfidf = vectorizer.tfIdf(document, documents, term);
            tfidfvectors[count] = tfidf;
            count++;
        }
        return tfidfvectors;
    }



    public List getCosineSimilarity() {
        List<Double> results = new ArrayList();
        for (int i = 0; i < tfidfDocsVector.size(); i++) {
            results.add(new CosineSimilarity().cosineSimilarity
                    (
                            requestVector,
                            tfidfDocsVector.get(i)
                    )
            );
        }
        return results;
    }


    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        Main main = new Main();
        main.parseFiles( "src/main/resources/lemm/");
        main.getDocsVector();
        main.getRequestVector();
        List results = main.getCosineSimilarity();
        double maxValue = (double)Collections.max(results);
        int index = results.indexOf(maxValue);
        File[] listOfFiles =  new File("src/main/resources/lemm/").listFiles();
        System.out.println(listOfFiles[index].getName());
        System.out.println(main.helper.getLinks().get(index));
    }
}
