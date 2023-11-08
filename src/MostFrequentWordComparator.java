
import java.util.Comparator;

/**
 * Class that implements comparator to organize the words in 
 * a ascending count order
 */
public class MostFrequentWordComparator implements Comparator<Token>{
    @Override
    public int compare(Token t1, Token t2){
        //Compare tokens on word frequencies - most frequent first, if tie than sorts alphabetically
        int freqCompare = Integer.compare(t2.getCount(), t1.getCount());
        if(freqCompare != 0){
            return freqCompare;
        }
        return t1.getWord().compareTo(t2.getWord());
}
}