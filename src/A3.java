
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

    List<String> stopWordList = new ArrayList<>(List.of(
            "a", "about", "all", "am", "an", "and", "any", "are", "as", "at", "be",
            "been", "but", "by", "can", "cannot", "could", "did", "do", "does", "else",
            "for", "from", "get", "got", "had", "has", "have", "he", "her", "hers", "him",
            "his", "how", "i", "if", "in", "into", "is", "it", "its", "like", "more", "me",
            "my", "no", "now", "not", "of", "on", "one", "or", "our", "out", "said", "say",
            "says", "she", "so", "some", "than", "that", "the", "their", "them", "then",
            "there", "these", "they", "this", "to", "too", "us", "upon", "was", "we", "were",
            "what", "with", "when", "where", "which", "while", "who", "whom", "why", "will",
            "you", "your"));

    private MostFrequentWordComparator mFcomparator = new MostFrequentWordComparator();
    private LeastFrequentWordComparator lFcomparator = new LeastFrequentWordComparator();

    SLL<Token> orderedTkList = new SLL<Token>();
    SLL<Token> mostFrequent = new SLL<Token>(mFcomparator);
    SLL<Token> leastFrequent = new SLL<Token>(lFcomparator);
    SLL<Token> uniqueWords;

    int wordCount = 0;
    int stopWordCount = 0;

    /**
     * Reads file from txt word by word
     * Trims word of trailing and leading blanks, converts it to lowercase, and
     * removes any punctuation/digits
     * If word is not in stopWordList, add it to tokenList
     * Then print the information in the console
     */
    private void readFile() {

        Scanner scanner = new Scanner(System.in);

        SLL<Token> tokenList = new SLL<Token>();
        while (scanner.hasNext()) {

            String word = scanner.next();
            word = word.trim().toLowerCase().replaceAll("[^a-z\\s]", "");

            if (stopWordList.contains(word)) {
                wordCount++;
                stopWordCount++;
            } else if (!word.isEmpty()) {
                wordCount++;
                Token newToken = new Token(word);

                tokenList.addAt(wordCount, newToken);
            }

        }
        uniqueWords = getUniqueWords(tokenList);

        System.out.println("Total Words: " + wordCount);
        System.out.println("Unique Words: " + uniqueWords.size());
        System.out.println("Stop Words: " + stopWordCount);

        orderedTkList = orderTheList(tokenList);// use this list to make the most and least

        orderLeastFreq();
        orderMostFreq();

        System.out.println();
        System.out.println("Most frequent");
        printMostFreq();

        System.out.println();
        System.out.println("Least frequent");
        printLeastFreq();

        System.out.println();
        System.out.println("All: ");
        printAllWords();

        scanner.close();

    }

    /**
     * Used to store unique words
     * Uses SLL.contains method to check if word is already in uniqueWords list
     * If it isn't, word is added to the list
     * 
     * @param tokenList The list of token objects (words + count)
     * @return uniqueWords One list with just unique words (no repetition)
     */
    public SLL<Token> getUniqueWords(SLL<Token> tokenList) {

        SLL<Token> uniqueWords = new SLL<>();

        Node<Token> curr = tokenList.get(0);
        while (curr != null) {
            Token token = curr.getData();

            if (!uniqueWords.contains(token)) {
                uniqueWords.addAt(0, token);
            }

            curr = curr.getNext();
        }
        return uniqueWords;
    }

    /**
     * Order the list in alphabetical order using addInOrder method from SLL class
     * 
     * @param tokenList The list of token objects (words + count)
     * @return orderedTkList The list with the elements in alphabetical order
     */
    private SLL<Token> orderTheList(SLL<Token> tokenList) {

        SLL<Token> uniqueWords = getUniqueWords(tokenList);
        for (int i = 0; i < uniqueWords.size(); i++) {
            orderedTkList.addInOrder(uniqueWords.get(i).getData());
        }
        for (int i = 0; i < orderedTkList.size(); i++) {
            countRepetition(tokenList, orderedTkList.get(i).getData());
        }
        return orderedTkList;
    }

    /**
     * Count how many times the word repeats in the list
     * each time it update the count from token class
     * 
     * @param tokenList The list of token objects (words + count
     * @param word      One of the words in the tokenList
     */
    private void countRepetition(SLL<Token> tokenList, Token word) {

        for (int i = 0; i < tokenList.size(); i++) {
            if (word.compareTo(tokenList.get(i).getData()) == 0) {
                word.increaseCount();
            }
        }
    }

    /**
     * Order the list by frequency, from the most to the least frequent
     */
    public void orderMostFreq() {

        for (int i = 0; i < orderedTkList.size(); i++) {
            mostFrequent.addInOrder(orderedTkList.get(i).getData());
        }
    }

    /**
     * Order the list by frequency, from the least to the most frequent
     */
    public void orderLeastFreq() {

        for (int i = 0; i < orderedTkList.size(); i++) {
            leastFrequent.addInOrder(orderedTkList.get(i).getData());
        }
    }

    /**
     * Print in the console the list of the most frequent word
     * but it just print until the 10th word
     */
    public void printMostFreq() {
        int num;
        if (mostFrequent.size() > 10) {
            num = 10;
        } else {
            num = mostFrequent.size();
        }

        for (int i = 0; i < num; i++) {
            System.out.println(mostFrequent.get(i).getData().format());
        }

    }

    /**
     * Print in the console the list of the least frequent word
     * but it just print until the 10th word
     */
    public void printLeastFreq() {
        int num;
        if (leastFrequent.size() > 10) {
            num = 10;
        } else {
            num = leastFrequent.size();
        }

        for (int i = 0; i < num; i++) {
            System.out.println(leastFrequent.get(i).getData().format());
        }
    }

    /**
     * Print all words in the console followed by its own count (frequency)
     */
    private void printAllWords() {

        for (int i = 0; i < orderedTkList.size(); i++) {
            System.out.println(orderedTkList.get(i).getData().format());
        }
    }

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