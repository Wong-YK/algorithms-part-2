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
            if (distanceSum > maxDistance) {
                maxDistance = distanceSum;
                result = nouns[i];
            }
        }
        return result;
    }

    // see test client below
    public static void main(String[] args) {}
}
