import java.util.Comparator;

/**
 * Token class that contains the directions on how a token
 * should look like (word with the frequency count)
 */
public class Token implements Comparable<Token> {
    private String word;
    private int count;

    /**
     * Constructor
     * 
     * @param word a string that forms a word
     */
    public Token(String word) {
        this.word = word;
        this.count = 1;
    }

    /*
     * Comparator to sort from high to low frequency
     */
    public static Comparator<Token> CompFreqDesc = new Comparator<Token>() {

        public int compare(Token t1, Token t2) {
            int freq1 = t1.getCount();
            int freq2 = t2.getCount();
            if (freq2 - freq1 == 0)
                return t1.compareTo(t2);

            else
                return freq2 - freq1;

        }
    };
    /*
     * Comparator to sort from low to high frequency
     */
    public static Comparator<Token> CompFreqAsc = new Comparator<Token>() {

        public int compare(Token t1, Token t2) {
            int freq1 = t1.getCount();
            int freq2 = t2.getCount();
            if (freq1 - freq2 == 0) {
                return t1.compareTo(t2);
            } else {
                return freq1 - freq2;
            }
        }
    };
    /*
     * Comparator to sort word length, from longest to shortest
     */
    public static Comparator<Token> CompLengthDesc = new Comparator<Token>() {
        public int compare(Token t1, Token t2) {
            int freq1 = t1.getCount();
            int freq2 = t2.getCount();
            if (freq2 - freq1 == 0) {
                return t1.compareTo(t2);
            } else {
                return Integer.compare(t2.getWordLength(), t1.getWordLength());
            }
        }
    };

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public int getWordLength() {
        return word.length();
    }

    /**
     * Increases the count attribute to show the frequency
     * of the word
     */
    public void increaseCount() {
        count++;
    }

    @Override
    public int compareTo(Token anotherToken) {
        /**
         * Compares two words
         */

        return (this.getWord().compareTo(anotherToken.getWord()));
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj instanceof Token) {
            Token otherToken = (Token) obj;
            return this.getWord().equals(otherToken.getWord());
        } else
            return false;
    }

    @Override
    public String toString() {
        return this.word;
    }

    public String format() {
        return this.word + " : " + getWordLength() + " : " + this.count;
    }
}