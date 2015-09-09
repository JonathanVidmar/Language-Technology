import java.io.IOException;
import java.util.*;

/**
 * Created by Pierre Nugues on 30/07/15.
 */
public class Pipeline {
    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader();
        String text = reader.readFile(args[0]);
        Tokenizer tokenizer = new Tokenizer();
        String[] words = tokenizer.tokenize(text);
        WordCounter wordCounter = new WordCounter();
        
        Pipeline pipeline = new Pipeline();
        //Unigrams
        Map<String, Integer> unigrams = wordCounter.count(words);
        Map<String, Integer> sortedUnigrams = pipeline.sortByValue(unigrams);
        String uc = pipeline.printSortedMap(sortedUnigrams, unigrams);
        
//        //Bigrams
//        Map<String, Integer> bigrams = wordCounter.countBigrams(words);
//        Map<String, Integer> sortedBigrams = pipeline.sortByValue(bigrams);
//        String bc = pipeline.printSortedMap(sortedBigrams, bigrams);
//        
//        
////        //Ngrams
//        Map<String, Integer> ngrams = wordCounter.countNgrams(words, 5);
//        Map<String, Integer> sortedNgrams = pipeline.sortByValue(ngrams);
//        String nc = pipeline.printSortedMap(sortedNgrams, ngrams);
//        System.out.println("Unigram counts:  " + uc);
//        System.out.println("Bigram counts:  " + bc);
//        System.out.println("Ngram counts:  " + nc);
    }

    public static String printSortedMap(Map<String, Integer> map, Map<String, Integer> words) {
		int count = 0;
		int unique = 0;
    	for (String word: map.keySet()) {
			System.out.println(word + ": " + words.get(word));
			count += words.get(word);
			unique++;
		}
    	return count + "  Unique:  " +  unique;
    }
    
    // Method borrowed from stack overflow
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}