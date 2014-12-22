public class BaseballElimination {

	// create a baseball division from given filename in format specified below
	public BaseballElimination(String filename) {

	}

	// number of teams
	public int numberOfTeams() {
		return -1;
	}

	// all teams
	public Iterable<String> teams() {
		return null;
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
		throw new IllegalArgumentException("Team not exists");
	}
}
