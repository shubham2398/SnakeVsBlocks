
public class Player {
	private String name;
	private String score;
	private String dateHighScoreMade;

	Player(String Name, int Score, String DateHighScoreMade) {
		name = Name;
		score = Integer.toString(Score);
		dateHighScoreMade = DateHighScoreMade;
	}

	public String getName() {
		return name;
	}

	public String getScore() {
		return score;
	}

	public String getDateHighScoreMade() {
		return dateHighScoreMade;
	}
}
