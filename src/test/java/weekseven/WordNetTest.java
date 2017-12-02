package weekseven;

import org.junit.Assert;
import org.junit.Test;


public class WordNetTest {

    private String testFilePath(String name) {
        return "src/test/java/weekseven/wordnet/" + name;
    }

    @Test
    public void test100Graph() {
        WordNet wn = new WordNet(testFilePath("synsets100-subgraph.txt"), testFilePath("hypernyms100-subgraph.txt"));
        Assert.assertEquals("protein", wn.sap("gluten", "keratin"));
        Assert.assertTrue(wn.distance("gluten", "keratin") == 4);
    }

    @Test
    public void testFull() {
        WordNet wn = new WordNet(testFilePath("synsets.txt"), testFilePath("hypernyms.txt"));
        Assert.assertTrue(wn.distance("Gota_Canal", "sweet_orange") == 15);
    }

}