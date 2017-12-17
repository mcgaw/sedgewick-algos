package weeknine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

public class BaseballElimination {

  private int n;
  private Map<String, Integer> teams;
  private Map<Integer, String> teamIds;
  private int[] wins;
  private int[] losses;
  private int[] remaining;
  private int[][] fixtures;

  // create a baseball division from given filename in format specified below
  public BaseballElimination(String filename) {
    processInputFile(filename);
  }

  private void processInputFile(String filePath) {
    In f = new In(filePath);
    n = f.readInt();
    teams = new HashMap<>();
    teamIds = new HashMap<>();
    wins = new int[n];
    losses = new int[n];
    remaining = new int[n];
    fixtures = new int[n][];

    int i = 0;
    while (!f.isEmpty()) {
      String teamName = f.readString().trim();
      teams.put(teamName, i);
      teamIds.put(i, teamName);
      wins[i] = f.readInt();
      losses[i] = f.readInt();
      remaining[i] = f.readInt();
      fixtures[i] = new int[n];

      for (int j = 4; j < 4 + n; j++) {
        fixtures[i][j - 4] = f.readInt();
      }
      i++;
    }
  }

  // number of teams
  public int numberOfTeams() {
    return n;
  }

  // all teams
  public Iterable<String> teams() {
    return teams.keySet();
  }

  private void checkTeam(String team) {
    if (teams.get(team) == null) {
      throw new IllegalArgumentException("invalid team name");
    }
  }

  // number of wins for given team
  public int wins(String team) {
    checkTeam(team);
    return wins[teams.get(team)];
  }

  // number of losses for given team
  public int losses(String team) {
    checkTeam(team);
    return losses[teams.get(team)];
  }

  // number of remaining games for given team
  public int remaining(String team) {
    checkTeam(team);
    return remaining[teams.get(team)];
  }

  // number of remaining games between team1 and team2
  public int against(String team1, String team2) {
    checkTeam(team1);
    checkTeam(team2);
    return fixtures[teams.get(team1)][teams.get(team2)];
  }

  private int remainingFixtures(String exclude) {
    int remainingFixtures = 0;
    int excluded = teams.get(exclude);
    for (int i = 0; i < n; i++) {
      if (i == excluded) {
        continue;
      }
      for (int j = i; j < n; j++) {
        if (j == excluded || j == i) {
          continue;
        }
        remainingFixtures += fixtures[i][j];
      }
    }
    return remainingFixtures;
  }

  private List<int[]> matchUps(String exclude) {
    // The offset indexing maintains an index with
    // no 'gap' for the exclude team. This is the
    // effective teamIds for construction of
    // the flow network.
    List<int[]> combinations = new ArrayList<>();
    int excluded = teams.get(exclude);
    int offset1 = 0;
    for (int i = 0; i < n; i++) {
      if (i == excluded) {
        offset1 = -1;
        continue;
      }
      int offset2 = 0;
      for (int j = i; j < n; j++) {
        if (i == j) {
          continue;
        }
        if (j == excluded) {
          offset2 = -1;
          continue;
        }
        combinations.add(new int[] { i + offset1, j + offset2, i, j });
      }
    }
    return combinations;
  }

  private FlowNetwork constructGraph(String team) {
    List<int[]> matchups = matchUps(team);
    int numMatchups = matchups.size();

    int verts = 1 + numMatchups + (n - 1) + 1;
    FlowNetwork net = new FlowNetwork(verts);

    int fixturesVertice = 1;
    int totalCapacity = 0;
    for (int[] matchUp : matchups) {
      // Create fixture edges for this matchup from source
      // to fixture to teams playing.
      int numMatches = fixtures[matchUp[2]][matchUp[3]];
      totalCapacity += numMatches;
      net.addEdge(new FlowEdge(0, fixturesVertice, numMatches));
      int team1 = 1 + numMatchups + matchUp[0];
      int team2 = 1 + numMatchups + matchUp[1];

      assert team1 != team2;
      net.addEdge(new FlowEdge(fixturesVertice, team1, Double.MAX_VALUE));
      net.addEdge(new FlowEdge(fixturesVertice, team2, Double.MAX_VALUE));
      fixturesVertice++;
      // System.out.println(teamIds.get(matchUp[2]) + "(node "+ team1 +
      //   ") will play " + teamIds.get(matchUp[3]) + "(node "+ team2 +
      //   ") " + numMatches + " times");
    }
    assert totalCapacity == remainingFixtures(team);

    // Create the remaining edges from teams to sink.
    int teamVertice = numMatchups + 1;
    int sinkVertice = teamVertice + n - 1;

    assert sinkVertice == net.V() - 1;

    int teamIndex = 0;
    int numTeams = 0;
    // The number of wins team can possibly have at
    // end of season.
    int teamMax = wins(team) + remaining(team);
    for (int i = teamVertice; i < sinkVertice; i++) {
      // The capacity is restricted to allow the case where
      // team can win as many games as each of the other
      // teams AND there is a max flow from the source ie all
      // games have been played.
      if (teamIndex == teams.get(team)) {
        teamIndex += 1;
      }
      int cap = teamMax - wins[teamIndex];
      if (cap < 0) {
        // Trivial elimination!
        return null;
      }
      assert i != sinkVertice;
      net.addEdge(new FlowEdge(i, sinkVertice, cap));
      teamIndex++;
      numTeams++;
    }
    assert numTeams == n - 1;
    return net;
  }

  // is given team eliminated?
  // The crux of the algorithm is to check if the all the games in
  // the flow network can be played in such a way that could allow
  // the given team to finish top.
  // This is done by restricting the flow from the team to the sink
  // to a maximum that when added to the games already won is less
  // or equal to the possible max score of the given team.
  public boolean isEliminated(String team) {
    checkTeam(team);
    FlowNetwork net = constructGraph(team);
    if (net == null) {
      return true;
    }
    FordFulkerson ff = new FordFulkerson(net, 0, net.V() - 1);
    return (ff.value() < remainingFixtures(team));
  }

  // subset R of teams that eliminates given team; null if not eliminated
  public Iterable<String> certificateOfElimination(String team) {
    checkTeam(team);
    FlowNetwork net = constructGraph(team);
    List<String> certTeams = new ArrayList<>();
    if (net == null) {
      // Trivial elimination
      for (String rTeam : teams.keySet()) {
        if (rTeam.equals(team)) {
          continue;
        }
        if (wins(rTeam) - wins(team) > remaining(team)) {
          certTeams.add(rTeam);
        }
      }
      return certTeams;
    }

    // Non-trivial
    FordFulkerson ff = new FordFulkerson(net, 0, net.V() - 1);
    if (((int)ff.value()) == remainingFixtures(team)) {
      return null;
    }

    // Process which teams are in cut.
    for (int vert = 0; vert < net.V(); vert++) {
      if (ff.inCut(vert)) {
        if (vert >= net.V() - (n + 1)) {
          int flowTeamIndex = n - (net.V() - vert);
          // Effectively map the team index of flow networ
          // to league index.
          if (flowTeamIndex >= teams.get(team)) {
            flowTeamIndex++;
          }
          String t = teamIds.get(flowTeamIndex);
          certTeams.add(t);
        }
      }
    }

    // Check average number of wins for teams in the subset.
    return certTeams;
  }

}