import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer{
	
	private String normalized_selma = "";
	
    public Tokenizer(String text) throws IOException {
    	normalizeSelma(text);
    }

    void normalizeSelma(String file) throws IOException {
    	System.out.println(file);
    	File f = new File(file);
    	Scanner scanner = new Scanner(f);
    	while (scanner.hasNext()) {
    		System.out.println(scanner.next());
    	}
    	//String fileText = new Scanner(new File(file)).useDelimiter("").next();
//        String text = replaceNewLine(fileText);
//        normalized_selma = tokenizeSentence(text);
    }
    
    String getNormalized() {
    	return this.normalized_selma;
    }
    
    String replaceNewLine(String text) {
        return text.replaceAll("\\s+", " ");
    }
    
    String[] tokenize(String text) {
        String[] words = text.split("\\P{L}+");
        return words;
    }
    
    String tokenizeSentence(String text) {
    	String sentences = "";
    	Pattern pattern = Pattern.compile("([\\\\sA-ZÅÄÖ]+[^.!?]*[.!?])");
    	Matcher matcher = pattern.matcher(text);
        
    	while (matcher.find()) {
    		String sentence = matcher.group().toLowerCase();
    		String modifiedSentence = "<s> " + sentence.replaceAll("[\\.,!?;:\"]", " ") + "</s> ";
    		sentences += modifiedSentence;
        }
    	return sentences;
    }
    
    void writeLinesToFile(String fileName, ArrayList<String> text) throws IOException {
    	
		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(fileName), "utf-8"));
		for (int i = 0; i < text.size(); i++) {
			writer.write(text.get(i));
		}
		writer.write('\n');
    	System.out.println("Done printing file!");
    }
}









