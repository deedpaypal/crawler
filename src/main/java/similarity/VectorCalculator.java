package similarity;

import helpers.FileHelper;
import lemmatizer.StanfordLemmatizer;
import vectorizer.TFIDFVectorizer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VectorCalculator {

    FileHelper helper = new FileHelper();
    List<List<String>> documents = new ArrayList<>(); // список списка слов каждого документа:)
    List<String> allTerms = new ArrayList<>();  // список всех слов во всех документах без повторений
    TFIDFVectorizer vectorizer = new TFIDFVectorizer();


    public void getDocuments(String filePath) throws IOException { // "src/main/resources/lemmBodies/"
        File[] listOfFiles = new File(filePath).listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String text = helper.readFromFile(file);
                List<String> words = new ArrayList<>(Arrays.asList((text.split(" "))));
                for (String term : words) {
                    if (!allTerms.contains(term)) {
                        allTerms.add(term);
                    }
                }
                documents.add(words);
            }
        }
        setAllTerms(allTerms);
    }

    public void setAllTerms(List<String> allTerms){
        this.allTerms = allTerms;
    }


    public void getDocsVector(List<double[]> tfidfDocsVector) throws IOException {
        for (List<String> document : documents) {
            tfidfDocsVector.add(tfIdfCalculator(document));
        }
        String filename = "src/main/resources/tfIdVectors";
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
        outputStream.writeObject(tfidfDocsVector);
    }

    public double[] getRequestVector(String request) throws IOException {
        getDocuments("src/main/resources/lemmBodies/");

        StanfordLemmatizer slem = new StanfordLemmatizer();
        request = slem.lemmatize(request);
        List<String> requestDocument = new ArrayList<>(Arrays.asList((request.split(" "))));
        double[] requestVector = tfIdfCalculator(requestDocument);
        return requestVector;
    }

    public double[] tfIdfCalculator(List<String> document) {
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





    public static void main(String args[]) throws FileNotFoundException, IOException {
         List<double[]> tfidfDocsVector = new ArrayList<>();

         VectorCalculator vc = new VectorCalculator();
         vc.getDocuments("src/main/resources/lemmBodies/");
         vc.getDocsVector(tfidfDocsVector);
    }
}
