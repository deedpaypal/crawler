package inverted_index;

import helpers.FileHelper;

import java.util.*;
import java.io.*;

/**
 * Created by nurie on 27.02.2019.
 */
public class InvertedIndex {
    Map<Integer,String> sources;
    HashMap<String, HashSet<Integer>> index;
    FileHelper helper = new FileHelper();

    InvertedIndex(){
        sources = new HashMap<Integer,String>();
        index = new HashMap<String, HashSet<Integer>>();
    }

    public HashMap buildIndex(List<String> fileNames){
        int i = 0;
        for(String fileName:fileNames){
            try(BufferedReader file = new BufferedReader(new FileReader("src/main/resources/lemmBodies/" + fileName)))
            {
                sources.put(i,fileName);
                String ln;
                while( (ln = file.readLine()) !=null) {
                    String[] words = ln.split("\\W+");
                    for(String word:words){
                        word = word.toLowerCase();
                        if (!index.containsKey(word))
                            index.put(word, new HashSet<Integer>());
                        index.get(word).add(i);
                    }
                }
            } catch (IOException e){
                System.out.println("File "+fileName+" not found. Skip it");
            }
            i++;
        }
    return index;
    }

    public void find(String phrase) throws IOException {
        String[] words = phrase.split("\\W+");
        HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()));
        for(String word: words){
            res.retainAll(index.get(word));
        }

        if(res.size()==0) {
            System.out.println("Not found");
            return;
        }
        System.out.println("Found in: ");
        List links = helper.getLinks();

        for(int num : res){
            System.out.println("\t FileName "+sources.get(num) + " Link: " + links.get(num) );
        }
    }


}
