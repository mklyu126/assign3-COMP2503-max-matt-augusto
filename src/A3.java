
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Comp 2503 Assigment 3 main class of the project that start the calls and
 * organize the order of the methods being called
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
	private BST<Token> wordsByLength = new BST<Token>(Token.CompLengthDesc);

	// 103 stopwords in list
	List<String> stopWordList = new ArrayList<>(List.of("a", "about", "all", "am", "an", "and", "any", "are", "as",
			"at", "be", "been", "but", "by", "can", "cannot", "could", "did", "do", "does", "else", "for", "from",
			"get", "got", "had", "has", "have", "he", "her", "hers", "him", "his", "how", "i", "if", "in", "into", "is",
			"it", "its", "like", "more", "me", "my", "no", "now", "not", "of", "on", "one", "or", "our", "out", "said",
			"say", "says", "she", "so", "some", "than", "that", "thats", "the", "their", "them", "then", "there",
			"these", "they", "this", "to", "too", "us", "upon", "was", "we", "were", "what", "with", "when", "where",
			"which", "while", "who", "whom", "why", "will", "you", "your", "up", "down", "left", "right", "man",
			"woman", "would", "should", "dont", "after", "before", "im", "men"));

	private int wordCount = 0;
	private int stopWordCount = 0;

	private final String FILE_PATH = "res/input3.txt";
//    private Scanner scanner = new Scanner(System.in);

	/**
	 * Reads file from txt word by word Trims word of trailing and leading blanks,
	 * converts it to lowercase, and removes any punctuation/digits
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
		createFreqLists();

	}

	/**
	 * Creates the frequency and length trees
	 * 
	 */
	private void createFreqLists() {
		//Increment amount of words allowed in an array
		int counter = 0;

		Iterator<Token> itrNatural = wordsByNaturalOrder.iterator();

		// Make the list
		while (itrNatural.hasNext()) {
			Token data = itrNatural.next();
			
			if (data.getCount() > 2) {
				wordsByFreqDesc.add(data);
			}
			wordsByLength.add(data);
			counter ++;
		}
		
		
	}

	/**
	 * Calculates average length of words stored in wordsByNaturalOrder tree
	 * 
	 * @return avgLength of words
	 */
	private int avgLength() {
		int lengthSum = 0;
		if (wordsByNaturalOrder.size() != 0) {
			Iterator<Token> bstIterator = wordsByNaturalOrder.iterator();
			while (bstIterator.hasNext()) {
				lengthSum += bstIterator.next().getWordLength();
			}
			return lengthSum / wordsByNaturalOrder.size();
		}
		return 0;

	}

	/**
	 * Removes stop words from tree
	 */
	private void removeStop() {
		for (String s : stopWordList) {
			Token token = new Token(s);
			Token existingNode = wordsByNaturalOrder.find(token);
			if (existingNode != null) {
				wordsByNaturalOrder.delete(existingNode);
				stopWordCount += 1;
			}
		}
	}

	/**
	 * Calculates optimal height for a tree of size n Round to an int
	 * 
	 * @param n - size of tree
	 * @return int - optimal height of tree
	 */
	private int optHeight(int n) {
		if(n != 0) {
			return (int) (Math.log(n) / Math.log(2));
		}
		return -1;
	}
	/////

	/**
	 * Saves the lists of words to an external text file called output.txt the
	 * output file has the correct format and ordering
	 */
	public void printResult() {
		Token tk = new Token(null);
		System.out.println("Total Words: " + wordCount);
		System.out.println("Unique Words: " + wordsByNaturalOrder.size());
		System.out.println("Stop Words: " + stopWordCount + "\n");

		System.out.println("10 Most Frequent");
		
		int count = 0;
		Iterator<Token> itrMostReq = wordsByFreqDesc.iterator();
		while(itrMostReq.hasNext() && count < 10){
			System.out.println(itrMostReq.next().format());
			count++;
		}
		System.out.println();
		
		
		System.out.println("10 Longest");
		
		int count2 = 0;
		Iterator<Token> itrMostLen = wordsByLength.iterator();
		while(itrMostLen.hasNext() && count2 < 10) {
			if (count2 == 0) {
				tk = itrMostLen.next();
				System.out.println(tk.format());
				count2++;
			} else {
				System.out.println(itrMostLen.next().format());
				count2++;
			}
		}
		
		System.out.println();
		
		System.out.println("The longest word is " + tk.format());
		System.out.println("The average word length is " + avgLength() + "\n");

		System.out.println("All");
		
		Iterator<Token> bstIterator = wordsByNaturalOrder.iterator();
		while (bstIterator.hasNext()) {
			System.out.println(bstIterator.next().format());
		}
		
		System.out.println();

		System.out.println("Alphabetic Tree: (Optimum Height: " + optHeight(wordsByNaturalOrder.size())
				+ ") (Actual Height: " + wordsByNaturalOrder.height() + ")");
		
		
		System.out.println("Frequency Tree: (Optimum Height: " + optHeight(wordsByFreqDesc.size()) 
				+ ") (Actual Height: " + wordsByFreqDesc.height() + ")");
		
		System.out.println("Length Tree: (Optimum Height: " + optHeight(wordsByLength.size()) 
				+ ") (Actual Height: " + wordsByLength.height() + ")");
	}
	
	/**
	 * temporary method just to text input 3 should be deleted later
	 * @throws FileNotFoundException
	 */
	public void saveResult() throws FileNotFoundException {
		Token tk = new Token(null);
		PrintWriter outputFile = new PrintWriter("res/output.txt");
		outputFile.println("Total Words: " + wordCount);
		outputFile.println("Unique Words: " + wordsByNaturalOrder.size());
		outputFile.println("Stop Words: " + stopWordCount + "\n");

		outputFile.println("10 Most Frequent");
		
		int count = 0;
		Iterator<Token> itrMostReq = wordsByFreqDesc.iterator();
		while(itrMostReq.hasNext() && count < 10){
			outputFile.println(itrMostReq.next().format());
			count++;
		}
		outputFile.println();
		
		
		outputFile.println("10 Longest");
		
		int count2 = 0;
		Iterator<Token> itrMostLen = wordsByLength.iterator();
		while(itrMostLen.hasNext() && count2 < 10) {
			if (count2 == 0) {
				tk = itrMostLen.next();
				outputFile.println(tk.format());
				count2++;
			} else {
				outputFile.println(itrMostLen.next().format());
				count2++;
			}
		}
		
		outputFile.println();
		
		outputFile.println("The longest word is " + tk.format());
		outputFile.println("The average word length is " + avgLength() + "\n");

		outputFile.println("All");
		
		Iterator<Token> bstIterator = wordsByNaturalOrder.iterator();
		while (bstIterator.hasNext()) {
			outputFile.println(bstIterator.next().format());
		}
		
		outputFile.println();

		outputFile.println("Alphabetic Tree: (Optimum Height: " + optHeight(wordsByNaturalOrder.size())
				+ ") (Actual Height: " + wordsByNaturalOrder.height() + ")");
		
		
		outputFile.println("Frequency Tree: (Optimum Height: " + optHeight(wordsByFreqDesc.size()) 
				+ ") (Actual Height: " + wordsByFreqDesc.height() + ")");
		
		outputFile.println("Length Tree: (Optimum Height: " + optHeight(wordsByLength.size()) 
				+ ") (Actual Height: " + wordsByLength.height() + ")");
		
		outputFile.close();
	}

	/**
	 * Main method that initiates the program
	 */
	public static void main(String[] args) throws Exception {
		A3 a3 = new A3();
		a3.readFile();
        //a3.printResult();
		a3.saveResult();
	}
}