package weekseven;

import org.junit.Assert;
import org.junit.Test;

import util.Util;


public class WordNetTest {
    
    private static String TEST_PATH = "weekseven.wordnet";

    @Test
    public void test100Graph() {
        WordNet wn = new WordNet(Util.getFileUrl(TEST_PATH, "synsets100-subgraph.txt"),
            Util.getFileUrl(TEST_PATH, "hypernyms100-subgraph.txt"));
        Assert.assertEquals("protein", wn.sap("gluten", "keratin"));
        Assert.assertTrue(wn.distance("gluten", "keratin") == 4);
    }

    @Test
    public void testFull() {
        WordNet wn = new WordNet(Util.getFileUrl(TEST_PATH, "synsets.txt"),
            Util.getFileUrl(TEST_PATH, ("hypernyms.txt")));
        Assert.assertTrue(wn.distance("Gota_Canal", "sweet_orange") == 15);
    }

}