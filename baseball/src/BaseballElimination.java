import java.util.LinkedHashSet;
import java.util.Set;

public class BaseballElimination {

	private Set<String> commands = new LinkedHashSet<>();

	private FlowNetwork fn;

	// create a baseball division from given filename in format specified below
	public BaseballElimination(String filename) {
		In in = new In(filename);
		int n = in.readInt();

		fn = new FlowNetwork(n * (n - 1) / 2 + n + 2);
		int S = 0;
		int T = 1;

		for(int i = 0; i < n; i++) {
			commands.add(in.readString());
			int w = in.readInt();
			int l = in.readInt();
			int r = in.readInt();
			FlowEdge edge = new FlowEdge(i + 2, T, w);
			fn.addEdge(edge);
			for(int j = 0; j < n; j++) {
				FlowEdge ge = new FlowEdge(S, vnum(i, j), in.readInt());
				fn.addEdge(ge);
				FlowEdge te = new FlowEdge(vnum(i, j), i + 2, Double.POSITIVE_INFINITY);
				fn.addEdge(te);
			}
		}
	}

	private int vnum(int i, int j) {
		return fn.V() * i + j + 2 + fn.V();
	}

	// number of teams
	public int numberOfTeams() {
		return -1;
	}

	// all teams
	public Iterable<String> teams() {
		return commands;
	}

	// number of wins for given team
	public int wins(String team) {
		checkTeamExists(team);
		return -1;
	}

	// number of losses for given team
	public int losses(String team) {
		checkTeamExists(team);
		return -1;
	}

	// number of remaining games for given team
	public int remaining(String team) {
		checkTeamExists(team);
		return -1;
	}

	// number of remaining games between team1 and team2
	public int against(String team1, String team2) {
		checkTeamExists(team1);
		checkTeamExists(team2);
		return -1;
	}

	// is given team eliminated?
	public boolean isEliminated(String team) {
		checkTeamExists(team);
		return false;
	}

	public Iterable<String> certificateOfElimination(String team) {
		checkTeamExists(team);
		return null;
	}

	private void checkTeamExists(String team) {
		if(!team.contains(team))
			throw new IllegalArgumentException("Team not exists");
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination(args[0]);
		for (String team : division.teams()) {
			if (division.isEliminated(team)) {
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team)) {
					StdOut.print(t + " ");
				}
				StdOut.println("}");
			}
			else {
				StdOut.println(team + " is not eliminated");
			}
		}
	}
}
