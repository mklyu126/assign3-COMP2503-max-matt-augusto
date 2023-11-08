
import java.util.Comparator;

/**
 * Class that implements comparator to organize the words in 
 * a descending count order
 */
public class LeastFrequentWordComparator implements Comparator<Token>{
    @Override
    public int compare(Token t1, Token t2){
        //Compare tokens on word frequencies, least frequent first, if tie than sorts alphabetically
        int freqCompare = Integer.compare(t1.getCount(), t2.getCount());
        if(freqCompare != 0){
            return freqCompare;
            }
            return t1.getWord().compareTo(t2.getWord());
    }
}