import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Pierre Nugues on 29/07/15.
 */
public class MutualInfo {
    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader();
        String text = reader.readFile(args[0]);
        Tokenizer tokenizer = new Tokenizer();
        String[] words = tokenizer.tokenize(text);
        WordCounter wc = new WordCounter();
        
        Map<String, Integer> unigrams = wc.count(words);
        int unigramCount = wc.totalWords(unigrams);
        
        Map<String, Integer> bigrams = wc.countBigrams(words);
        int bigramCount = wc.totalWords(bigrams);
        
        MutualInfo mi = new MutualInfo();
//        Map<String, Double> miScores = mi.compute(words.length, unigrams, bigramCounts);
//        for (String bigram : miScores.keySet()) {
//            String[] pair = bigram.split("\t");
//            System.out.println(miScores.get(bigram) + "\t" + bigram + "\t" + bigramCounts.get(bigram) + "\t" + unigrams.get(pair[0]) + "\t" + unigrams.get(pair[1]));
//        }
        
        mi.unigramFindSentence(unigrams, "<s> det var en gång en katt som hette nils </s>", unigramCount);
        mi.bigramFindSentence(unigrams, bigrams, "<s> det var en gång en katt som hette nils </s>", unigramCount, bigramCount);
    }

    private static void printSummary(String type, double totalProb) {
    	System.out.println("========================================================================================");
    	System.out.println("Prob." + type + ": " + totalProb);	
    }
    
    public static void unigramFindSentence(Map<String, Integer> unigrams, String sentence, int totalCount) {
    	String[] words = sentence.split(" ");
    	System.out.println("wi \t C(wi) \t\t words \t\t P(wi)");
    	System.out.println("=====================================================");
    	
    	double entropy = 0.0;
    	double totalProb = 1.0;
    	for (String word: words) {
    		double a = unigrams.get(word);
    		double b = totalCount;
    		double p = a/b;
    		System.out.println(word + "\t" + unigrams.get(word) + "\t\t" + totalCount + "\t\t" + String.valueOf(p));
    		totalProb *= p;
    		entropy -= p*(Math.log(p)/Math.log(2));
		}
    	printSummary("unigrams	", totalProb);
    }
    
    public static void bigramFindSentence(Map<String, Integer> unigrams, Map<String, Integer> bigrams, String sentence, int unigramCount, int bigramCount) {
    	String[] words = sentence.split(" ");
    	
    	String[] bigramWords = new String[words.length - 1];
    	for (int i = 0; i < bigramWords.length; i++) {
    		bigramWords[i] = words[i] + "\t" + words[i+1];
    	}
    	
    	System.out.println("wi \t wi+1 \t\t Ci,i+1 \t\t C(i) \t\t P(wi+1|wi)");
    	System.out.println("========================================================================================");
    	
    	double totalProb = 1.0;
    	for (String word: bigramWords) {
    		String[] pair = word.split("\t");
    		String word1 = pair[0];
    		String word2 = pair[1];
    		
    		String prob = "";
    		double p = 0.0;
    		Integer a = bigrams.get(word);
    		if (a == null || a < 1) {
    			a = 0;
    			p = (double)unigrams.get(word1)/(double)unigramCount;
    			prob = "backoff: " + String.valueOf(p);
    		} else {
    			p = (double)a/(double)unigrams.get(word1);
    			prob = String.valueOf(p);
    		}
    		System.out.println(word1 + "\t" + word2 + "\t\t" + a + "\t\t\t" + unigrams.get(word1) + "\t\t" + prob);
    		totalProb *= p;
		}
    	printSummary("bigrams", totalProb);
    }
    
    Map<String, Double> compute(int N, Map<String, Integer> unigramCounts, Map<String, Integer> bigramCounts) {
        Map<String, Double> miScores = new TreeMap<>();

        for (String bigram : bigramCounts.keySet()) {
            String[] pair = bigram.split("\t");
            double mi = Math.log((double) N * bigramCounts.get(bigram) / (unigramCounts.get(pair[0]) * unigramCounts.get(pair[1]))) / Math.log(2.0);
            miScores.put(bigram, mi);
        }
        return miScores;
    }
    


    
}