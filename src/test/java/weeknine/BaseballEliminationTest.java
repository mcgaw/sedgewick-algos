package weeknine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import util.Util;

public class BaseballEliminationTest {

  private static String NEW_YORK = "New_York";
  private static String ATLANTA = "Atlanta";
  private static String PHILY = "Philadelphia";
  private static String MONTREAL = "Montreal";
  private static String DETROIT = "Detroit";
  private static String TORONTO = "Toronto";
  private static String IRELAND = "Ireland";
  private static String HUFFLE = "Hufflepuff";
  private static String TEAM21 = "Team21";

  @Test
  public void testResultsCreation() {
    BaseballElimination division = new BaseballElimination(Util.getFileUrl("weeknine/baseball", "teams4.txt"));
    Assert.assertEquals(4, division.numberOfTeams());
    List<String> teams = new ArrayList<>(Arrays.asList(new String[] {PHILY, ATLANTA, NEW_YORK, MONTREAL}));
    for (String team : division.teams()) {
      Assert.assertTrue(team + " not found in division", teams.contains(team));
    }
    // Check some random stats from the input file.
    Assert.assertTrue("Montreal have 3 remaining, not " + division.remaining(MONTREAL),
        division.remaining(MONTREAL) == 3);
    Assert.assertTrue("Altanta have 71 losses, not " + division.losses(ATLANTA),
        division.losses(ATLANTA) == 71);
    Assert.assertTrue("New York reported " + division.wins(NEW_YORK) + " instead of 78",
        division.wins(NEW_YORK) == 78);
    Assert.assertTrue("New York have defeated Atlanta 6 times, not " + division.against(NEW_YORK, ATLANTA),
        division.against(NEW_YORK, ATLANTA) == 6);
  }

  private void checkCertificate(Iterable<String> cert, List<String> ref) {
    int assertions = 0;
    Assert.assertTrue(cert != null);
    for (String team : ref)  {
        Assert.assertTrue("certificate doesn't contain "+team, ref.contains(team));
        assertions++; 
    }
    Assert.assertTrue(assertions == ref.size());
  }

  @Test
  public void testTeams4() {
    BaseballElimination division = new BaseballElimination(
        Util.getFileUrl("weeknine.baseball", "teams4.txt"));

    Assert.assertTrue(division.isEliminated(PHILY));
    Assert.assertTrue(division.isEliminated(MONTREAL));

    checkCertificate(division.certificateOfElimination(PHILY),
        Arrays.asList(new String[] {ATLANTA, NEW_YORK}));
    
    Assert.assertTrue(division.certificateOfElimination(MONTREAL) != null);
  }

  @Test
  public void testTeams4b() {
    BaseballElimination division = new BaseballElimination(
        Util.getFileUrl("weeknine.baseball", "teams4b.txt"));

    Assert.assertTrue(division.isEliminated(HUFFLE));

  }

  @Test
  public void testTeams5a() {
    BaseballElimination division = new BaseballElimination(
        Util.getFileUrl("weeknine.baseball", "teams5a.txt"));

    Assert.assertTrue(division.isEliminated(DETROIT));
    checkCertificate(division.certificateOfElimination(DETROIT),
            Arrays.asList(new String[] {NEW_YORK}));
  }

 @Test
  public void testTeams5b() {
    BaseballElimination division = new BaseballElimination(
        Util.getFileUrl("weeknine.baseball", "teams5b.txt"));

    Assert.assertTrue(!division.isEliminated(TORONTO));

    Assert.assertTrue(division.isEliminated(DETROIT));
    checkCertificate(division.certificateOfElimination(DETROIT),
            Arrays.asList(new String[] {NEW_YORK}));
  }

  @Test
  public void testTeams5cElimination() {
    BaseballElimination division = new BaseballElimination(
        Util.getFileUrl("weeknine.baseball", "teams5c.txt"));

    Assert.assertTrue(division.isEliminated(PHILY));
  }

  @Test
  public void testTeams7Elimination() {
    BaseballElimination division = new BaseballElimination(
        Util.getFileUrl("weeknine.baseball", "teams7.txt"));

    Assert.assertTrue(division.isEliminated(IRELAND));
  }

  @Test
  public void testTeams36Elimination() {
    BaseballElimination division = new BaseballElimination(
        Util.getFileUrl("weeknine.baseball", "teams36.txt"));

    Assert.assertTrue(division.isEliminated(TEAM21));
  }
}