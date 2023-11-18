
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Comp 2503 Assigment 3 main class of the project that start
 * the calls and organize the order of the methods being called
 * 
 * @author Augusto De Morais Silva, Matthew Do, and Maxim Klyukanov
 *
 */
public class A3 {
    /**
     * Lists (trees) of words. Ordered by - Alphabetic, Frequency, and then Length
     */
    private BST<Token> wordsByNaturalOrder = new BST<Token>();
    private BST<Token> wordsByFreqDesc = new BST<Token>(Token.CompFreqDesc);
    private BST<Token> wordsByFreqAsec = new BST<Token>(Token.CompFreqAsc);
//    private BST<Token> wordsByLength = new BST<Token>(Token.CompLengthDesc);

    // 103 stopwords in list
    List<String> stopWordList = new ArrayList<>(List.of(
            "a", "about", "all", "am", "an", "and", "any", "are", "as", "at",
            "be", "been", "but", "by", "can", "cannot", "could", "did", "do", "does",
            "else", "for", "from", "get", "got", "had", "has", "have", "he", "her",
            "hers", "him", "his", "how", "i", "if", "in", "into", "is", "it",
            "its", "like", "more", "me", "my", "no", "now", "not", "of", "on",
            "one", "or", "our", "out", "said", "say", "says", "she", "so", "some",
            "than", "that", "thats", "the", "their", "them", "then", "there", "these", "they", "this",
            "to", "too", "us", "upon", "was", "we", "were", "what", "with", "when",
            "where", "which", "while", "who", "whom", "why", "will", "you", "your", "up", "down", "left", "right",
            "man", "woman", "would", "should", "dont", "after", "before", "im", "men"));

    private int wordCount = 0;
    private int stopWordCount = 0;
    
    private final String FILE_PATH = "res/input2.txt";
//    private Scanner scanner = new Scanner(System.in);
    
    /**
     * Reads file from txt word by word
     * Trims word of trailing and leading blanks, converts it to lowercase, and
     * removes any punctuation/digits
     * 
     */
    private void readFile() {
    	
    	Scanner scanner;
		try {
			scanner = new Scanner(new File(FILE_PATH));
	        while (scanner.hasNext()) {
	        	
	            String word = scanner.next();
	            word = word.trim().toLowerCase().replaceAll("[^a-z\\s]", "");
	            

	            if (word.length() > 0) {
	            	wordCount += 1;
	                Token token = new Token(word);
	                // Check if token is already in tree
	                Token existingNode = wordsByNaturalOrder.find(token);

	                if (existingNode != null) { // If token already in tree increase freauency
	                    existingNode.increaseCount();
	                } else {
	                    wordsByNaturalOrder.add(token); // If token not in tree, add it
	                }
	            }
	        }
	        removeStop();
	        
	        scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//createFreqLists();
		
    }

    /**
     * Creates the frequency and length lists
     * 
     */
    private void createFreqLists() {
        // TODO
    	Iterator<Token> bstIterator = wordsByNaturalOrder.iterator();
    	while(bstIterator.hasNext()) {
    		System.out.println(bstIterator.next().format());
    	}
    }

    /**
     * Calculates average length of words stored in wordsByNaturalOrder tree
     * 
     * @return avgLength of words
     */
    private int avgLength() {
    	int lengthSum = 0;
    	if(wordsByNaturalOrder.size() != 0) {
	    	Iterator<Token> bstIterator = wordsByNaturalOrder.iterator();
	    	while(bstIterator.hasNext()) {
	    		lengthSum += bstIterator.next().getWordLength();
	    	}
	        return lengthSum/wordsByNaturalOrder.size();
    	}
    	return 0;

    }
    


    /**
     * Removes stop words from tree
     */
    private void removeStop() {
        for(String s: stopWordList) {
        	Token token = new Token(s);
        	Token existingNode = wordsByNaturalOrder.find(token);
        	if (existingNode != null) {
        		wordsByNaturalOrder.delete(existingNode);
        		stopWordCount += 1;
        	}
        }
    }

    /**
     * Calculates optimal height for a tree of size n
     * Round to an int
     * 
     * @param n - size of tree
     * @return int - optimal height of tree
     */
    private int optHeight(int n) {
        return (int) (Math.log(n)/Math.log(2));
    }
    /////

    /**
     * Saves the lists of words to an external text file called output.txt
     * the output file has the correct format and ordering
     */
    public void printResult() {
    	 System.out.println("Total Words: " + wordCount);
         System.out.println("Unique Words: " + wordsByNaturalOrder.size());
         System.out.println("Stop Words: " + stopWordCount + "\n");
         
         System.out.println("10 Most Frequent\n");
         System.out.println("10 Longest\n");
         
         System.out.println("The longest word is");
         System.out.println("The average word length is " + avgLength() + "\n");
         
         System.out.println("All");
         
         System.out.println();
         
         System.out.println("Alphabetic Tree: (Optimum Height: " + optHeight(wordsByNaturalOrder.size()) + 
        		 			") (Actual Height: " + wordsByNaturalOrder.height() + ")");
         System.out.println("Frequency Tree: (Optimum Height: -1) (Actual Height: -1)");
         System.out.println("Length Tree: (Optimum Height: 5) (Actual Height: 17)");
    }

    /**
     * Main method that initiates the program
     */
    public static void main(String[] args) throws Exception {
        A3 a3 = new A3();
        a3.readFile();
        a3.printResult();
    }
}