package similarity;


import java.util.ArrayList;
import java.util.List;

public class CosineSimilarity {

    public double calculateCosineSimilarity(double[] docVector1, double[] docVector2) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        double cosineSimilarity = 0.0;

        for (int i = 0; i < docVector1.length; i++) //docVector1 and docVector2 must be of same length
        {
            dotProduct += docVector1[i] * docVector2[i];
            magnitude1 += Math.pow(docVector1[i], 2);
            magnitude2 += Math.pow(docVector2[i], 2);
        }

        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);

        if (magnitude1 != 0.0 | magnitude2 != 0.0) {
            cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
        } else {
            return 0.0;
        }
        return cosineSimilarity;
    }

    public List<Double> getCosineSimilarity(List<double[]> tfidfDocsVector, double[] requestVector) {
        List<Double> results = new ArrayList();
        for (int i = 0; i < tfidfDocsVector.size(); i++) {
            results.add(calculateCosineSimilarity
                    (
                            requestVector,
                            tfidfDocsVector.get(i)
                    )
            );
        }
        return results;
    }

}