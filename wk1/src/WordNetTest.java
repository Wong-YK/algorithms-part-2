import org.junit.Test;
import static org.junit.Assert.*;

public class WordNetTest {

    @Test
    //7 vertex WordNet
    public void nounsTest1() {
        WordNet wn = new WordNet("organisms_synsets.txt", "organisms_hypernyms.txt");
        Iterable<String> nouns = wn.nouns();
        for (String noun: nouns) {
            System.out.println(noun);
        }
    }

    @Test
    //noun in 7 vertex WordNet
    public void isNounTest1() {
        WordNet wn = new WordNet("organisms_synsets.txt", "organisms_hypernyms.txt");
        assertTrue(wn.isNoun("ape"));
    }

    @Test
    //noun not in 7 vertex WordNet
    public void isNounTest2() {
        WordNet wn = new WordNet("organisms_synsets.txt", "organisms_hypernyms.txt");
        assertFalse(wn.isNoun("snake"));
    }

    @Test
    //sap of length 2
    public void sapTest1() {
        WordNet wn = new WordNet("organisms_synsets.txt", "organisms_hypernyms.txt");
        String commonAncestor = wn.sap("human", "chimpanzee");
        assertEquals("ape", commonAncestor);
    }

}
