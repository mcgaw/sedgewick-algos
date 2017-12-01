package weekseven;

import org.junit.Assert;
import org.junit.Test;

public class OutcastTest {

    private String testFilePath(String name) {
        return "src/test/java/weekseven/wordnet/" + name;
    }

    @Test
    public void testFruitOutlier() {
        WordNet wordnet = new WordNet(testFilePath("synsets.txt"), testFilePath("hypernyms.txt"));
        Outcast outcast = new Outcast(wordnet);
        String[] nouns = new String[] {"apple", "pear", "peach", "banana", "lime", "lemon", "blueberry",
            "strawberry", "mango", "watermelon", "potato"};
        Assert.assertEquals("potato", outcast.outcast(nouns));
    }

}