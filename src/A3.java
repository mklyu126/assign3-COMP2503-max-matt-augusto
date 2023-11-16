
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
    private BST<Token> wordsByLength = new BST<Token>(Token.CompLengthDesc);

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

    // SLL<Token> orderedTkList = new SLL<Token>();
    // SLL<Token> mostFrequent = new SLL<Token>(mFcomparator);
    // SLL<Token> leastFrequent = new SLL<Token>(lFcomparator);
    // SLL<Token> uniqueWords;

    private int wordCount = 0;
    private int stopWordCount = 0;

    private Scanner scanner = new Scanner(System.in);

    /**
     * Reads file from txt word by word
     * Trims word of trailing and leading blanks, converts it to lowercase, and
     * removes any punctuation/digits
     * If word is not in stopWordList, add it to tokenList
     * Then print the information in the console
     */
    private void readFile() {

        SLL<Token> tokenList = new SLL<Token>();
        while (scanner.hasNext()) {

            String word = scanner.next();
            word = word.trim().toLowerCase().replaceAll("[^a-z\\s]", "");

            if (word.length() > 0) {
                Token token = new Token(word);
                // Check if token is already in tree
                BST<Token>.BSTNode existingNode = wordsByNaturalOrder.find(token);

                if (existingNode != null) { // If token already in tree increase freauency
                    existingNode.getData().increaseCount();
                } else {
                    wordsByNaturalOrder.add(token); // If token not in tree, add it
                }
            }

        }
        // uniqueWords = getUniqueWords(tokenList);

        // System.out.println("Total Words: " + wordCount);
        // System.out.println("Unique Words: " + uniqueWords.size());
        // System.out.println("Stop Words: " + stopWordCount);

        // orderedTkList = orderTheList(tokenList);// use this list to make the most and
        // least

        // orderLeastFreq();
        // orderMostFreq();

        // System.out.println();
        // System.out.println("Most frequent");
        // printMostFreq();

        // System.out.println();
        // System.out.println("Least frequent");
        // printLeastFreq();

        // System.out.println();
        // System.out.println("All: ");
        // printAllWords();

        scanner.close();

    }

    /**
     * Creates the frequency and length lists
     */
    private void createFreqLists() {
        // TODO
    }

    /**
     * Calculates average length of words stored in wordsByNaturalOrder tree
     * 
     * @return avgLength of words
     */
    private int avgLength() {
        // TODO
        return 0;

    }

    /**
     * Removes stop words from tree
     */
    private void removeStop() {
        // TODO
    }

    /**
     * Calculates optimal height for a tree of size n
     * Round to an int
     * 
     * @param n - size of tree
     * @return int - optimal height of tree
     */
    private int optHeight(int n) {
        // TODO
        return 0;
    }
    /////

    /**
     * Saves the lists of words to an external text file called output.txt
     * the output file has the correct format and ordering
     */
    public void saveData() {

        try {

            PrintWriter outputFile = new PrintWriter("projeproj/res/output.txt");
            outputFile.println("Total Words: " + wordCount);
            outputFile.println("Unique Words: " + uniqueWords.size());
            outputFile.println("Stop Words: " + stopWordCount);

            outputFile.println("");
            outputFile.println("10 Most Frequent");

            // add in most frequent lines in output txt
            for (int i = 0; i < 10; i++) {
                outputFile.println(mostFrequent.get(i).getData().format());

            }

            outputFile.println("");
            outputFile.println("10 Least Frequent");

            for (int i = 0; i < 10; i++) {
                outputFile.println(leastFrequent.get(i).getData().format());

            }

            outputFile.println("");
            outputFile.println("All");

            for (int i = 0; i < orderedTkList.size(); i++) {
                outputFile.println(orderedTkList.get(i).getData().format());

            }

            outputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Main method that initiates the program
     */
    public static void main(String[] args) throws Exception {
        A3 a3 = new A3();
        a3.readFile();
        a3.saveData();
    }
}