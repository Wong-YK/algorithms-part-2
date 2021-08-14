import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDistance = 0;
        String result = nouns[0];
        for (int i = 0; i < nouns.length; i++) {
            int distanceSum = 0;
            for (int j = 0; j < nouns.length; j++) {
                distanceSum += this.wordnet.distance(nouns[i], nouns[j]);
                }
            if (distanceSum >= maxDistance) {
                maxDistance = distanceSum;
                result = nouns[i];
            }
        }
        return result;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        //String[] nouns = {"horse", "zebra", "cat", "bear", "table"};
        String[] nouns = {"water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea", "coffee"};
        //String[] nouns = {"apple", "pear", "peach", "banana", "lime", "lemon", "blueberry", "strawberry", "mango", "watermelon", "potato"};
        StdOut.println(outcast.outcast(nouns));
    }
}
