import java.io.Serializable;

/**
 * This represents the player class that is used to save a player's name, score
 * and date in leaderboard. This is a serializable class.
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * name is the name of the player score is the score of the player
	 * dateHighScoreMade is the date on which high score was made.
	 */
	private String name;
	private String score;
	private String dateHighScoreMade;

	/**
	 * This contructor for Player makes a player taking values for name, score and
	 * date.
	 * 
	 * @param Name              is the name of the player
	 * @param Score             is the score made
	 * @param DateHighScoreMade is the date of high score
	 */
	public Player(String Name, int Score, String DateHighScoreMade) {
		name = Name;
		score = Integer.toString(Score);
		dateHighScoreMade = DateHighScoreMade;
	}

	/**
	 * This method gives the name of the player.
	 * 
	 * @return name of player
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method gives the score made by player.
	 * 
	 * @return score of player
	 */
	public String getScore() {
		return score;
	}

	/**
	 * This method gives the date of high score made
	 * 
	 * @return the high score date
	 */
	public String getDateHighScoreMade() {
		return dateHighScoreMade;
	}
}
