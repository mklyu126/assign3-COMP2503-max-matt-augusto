

/**
 * Token class that contains the directions on how a token
 * should look like (word with the frequency count)
 */
public class Token implements Comparable<Token> {
    private String word;
    private int count;
    
    /**
     * Constructor
     * @param word a string that forms a word
     */
    public Token(String word){ 
        this.word = word;
        this.count = 0;
    }

    public String getWord(){
        return word;
    }
    public int getCount(){
        return count;
    }
    
    /**
     * Increases the count attribute to show the frequency
     * of the word 
     */
    public void increaseCount(){
        count++;
    }

    @Override
    public int compareTo(Token anotherToken){
        /**
         * Compares two words 
         */

        return (this.getWord().compareTo(anotherToken.getWord()));
    }

    @Override
    public boolean equals(Object obj){
     
    if (obj == null){
        return false;
     }   

     if (obj instanceof Token){
        Token otherToken = (Token) obj;
        return this.getWord().equals(otherToken.getWord());
     }
     else return false;
    }
    @Override
    public String toString(){
        return this.word;
    }
    
    public String format() {
    	return this.word + " : " + this.count;
    }
}