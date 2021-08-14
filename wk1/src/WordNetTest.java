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

    @Test
    //sap of length 3
    public void sapTest2() {
        WordNet wn = new WordNet("organisms_synsets.txt", "organisms_hypernyms.txt");
        String commonAncestor = wn.sap("whale", "chimpanzee");
        assertEquals("mammal", commonAncestor);
    }

    @Test
    //sap of length 3 between external and internal vertices
    public void sapTest3() {
        WordNet wn = new WordNet("organisms_synsets.txt", "organisms_hypernyms.txt");
        String commonAncestor = wn.sap("ape", "fungus");
        assertEquals("organism", commonAncestor);
    }


    @Test
    //distance of 2
    public void distanceTest1() {
        WordNet wn = new WordNet("organisms_synsets.txt", "organisms_hypernyms.txt");
        assertEquals(2, wn.distance("mammal", "fungus"));
    }

    @Test
    //distance of 2 with vertices reversed
    public void distanceTest2() {
        WordNet wn = new WordNet("organisms_synsets.txt", "organisms_hypernyms.txt");
        assertEquals(2, wn.distance("fungus", "mammal"));
    }

    @Test
    //distance of 4
    public void distanceTest3() {
        WordNet wn = new WordNet("organisms_synsets.txt", "organisms_hypernyms.txt");
        assertEquals(4, wn.distance("human", "fungus"));
    }

    @Test
    //distance of 2; autograder test
    public void distanceTest4() {
        WordNet wn = new WordNet("synsets6.txt", "hypernyms6TwoAncestors.txt");
        assertEquals(2, wn.distance("b", "f"));
    }

}
