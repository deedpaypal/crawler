package similarity;

import helpers.FileHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class Search {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        VectorCalculator vc = new VectorCalculator();
        FileHelper helper = new FileHelper();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the request: ");
        String filename = "src/main/resources/tfIdVectors";
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename));
        List<double[]> tfidfDocsVector = (List<double[]>) inputStream.readObject();


        String request = sc.nextLine();
        double[] requestVector = vc.getRequestVector(request);


        List<Double> initResults = new CosineSimilarity().getCosineSimilarity(tfidfDocsVector, requestVector);
        System.out.println(initResults);
        if (initResults.get(0).isNaN()) {
            System.out.println("No matches");
        }
        else{
            List<Double> results = new ArrayList<>();
            results.addAll(initResults);
            Collections.sort(results);
            Collections.reverse(results);
            List<Double> values = results.subList(0, 4);

            File[] listOfFiles = new File("src/main/resources/lemmBodies/").listFiles();
            int index;


            for(Double value: values){
                if(value != 0){
                    index = initResults.indexOf(value);
                    String name = listOfFiles[index].getName();
                    System.out.println(name);
                    int linkIndex = Integer.parseInt(name.replaceAll("[^0-9]", ""));
                    System.out.println(helper.getLinks().get(linkIndex-1));
                }
            }
        }
    }
}



