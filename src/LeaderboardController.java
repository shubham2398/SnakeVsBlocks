import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * @author SHUBHAM _THAKRAL, TANMAY BANSAL This class is the controller class of
 *         Leaderboard.fxml. It is used to set the name, score and date of the
 *         top 10 players.
 */
public class LeaderboardController {
	/**
	 * goBack is the fx:id of the back button, which when clicked will take the user
	 * back to main page. exitGame is the fx:id of the exit button, which when
	 * clicked will exit the game. name[i] is the fx:id of the ith name text field
	 * which stores the name of the ith player. score[i] is the fx:id of the ith
	 * score text field which stores the score of the ith player. date[i] is the
	 * fx:id of the ith date text field, which stores the date on which high score
	 * was created.
	 */

	public ImageView goBack;

	/**
	 * This function displays the main page when goBack button is clicked.
	 * 
	 * @param e is the MouseEvent which stores the state of the mouse.
	 * @throws Exception, It throws Exception if the stage creation fails.
	 */
	@FXML
	public void displayMainPage(MouseEvent e) throws Exception {
		Stage stage = (Stage) goBack.getScene().getWindow();
		stage.hide();
		MainPage.displayMainPage();
	}

	@FXML
	public ImageView exitGame;

	/**
	 * This function will exit the game when the exit button is clicked.
	 * 
	 * @param e is the MouseEvent which stores the state of the mouse.
	 * @throws Exception, It throws Exception if the stage creation fails.
	 */
	@FXML
	public void exit(MouseEvent e) throws Exception {
		Stage stage = (Stage) exitGame.getScene().getWindow();
		stage.close();
	}

	@FXML
	private Text name1;

	@FXML
	private Text score1;

	@FXML
	private Text date1;

	@FXML
	private Text name2;

	@FXML
	private Text score2;

	@FXML
	private Text date2;

	@FXML
	private Text name3;

	@FXML
	private Text score3;

	@FXML
	private Text date3;

	@FXML
	private Text name4;

	@FXML
	private Text score4;

	@FXML
	private Text date4;

	@FXML
	private Text name5;

	@FXML
	private Text score5;

	@FXML
	private Text date5;

	@FXML
	private Text name6;

	@FXML
	private Text score6;

	@FXML
	private Text date6;

	@FXML
	private Text name7;

	@FXML
	private Text score7;

	@FXML
	private Text date7;

	@FXML
	private Text name8;

	@FXML
	private Text score8;

	@FXML
	private Text date8;

	@FXML
	private Text name9;

	@FXML
	private Text score9;

	@FXML
	private Text date9;

	@FXML
	private Text name10;

	@FXML
	private Text score10;

	@FXML
	private Text date10;

	/**
	 * This function is used to update the leader board with a new high score.
	 * 
	 * @param name  is the Name of the player who created the high score.
	 * @param score is the final score of the player.
	 * @param date  is the date on which the player created the high score.
	 * @param rank  is the rank of player.
	 */
	public void updateLeaderboard(String name, String score, String date, int rank) {
		if (rank == 1)
			setEntry1(name, score, date);
		else if (rank == 2)
			setEntry2(name, score, date);
		else if (rank == 3)
			setEntry3(name, score, date);
		else if (rank == 4)
			setEntry4(name, score, date);
		else if (rank == 5)
			setEntry5(name, score, date);
		else if (rank == 6)
			setEntry6(name, score, date);
		else if (rank == 7)
			setEntry7(name, score, date);
		else if (rank == 8)
			setEntry8(name, score, date);
		else if (rank == 9)
			setEntry9(name, score, date);
		else if (rank == 10)
			setEntry10(name, score, date);
	}

	/**
	 * This sets the entry on the leaderboard.
	 * 
	 * @param name  is the name of the player who created the high score.
	 * @param score is the final score of the player.
	 * @param date  is the date on which high score was created.
	 */
	private void setEntry1(String name, String score, String date) {
		name1.setText(name);
		score1.setText(score);
		date1.setText(date);
	}

	/**
	 * This sets the entry on the leaderboard.
	 * 
	 * @param name  is the name of the player who created the high score.
	 * @param score is the final score of the player.
	 * @param date  is the date on which high score was created.
	 */
	private void setEntry2(String name, String score, String date) {
		name2.setText(name);
		score2.setText(score);
		date2.setText(date);
	}

	/**
	 * This sets the entry on the leaderboard.
	 * 
	 * @param name  is the name of the player who created the high score.
	 * @param score is the final score of the player.
	 * @param date  is the date on which high score was created.
	 */
	private void setEntry3(String name, String score, String date) {
		name3.setText(name);
		score3.setText(score);
		date3.setText(date);
	}

	/**
	 * This sets the entry on the leaderboard.
	 * 
	 * @param name  is the name of the player who created the high score.
	 * @param score is the final score of the player.
	 * @param date  is the date on which high score was created.
	 */
	private void setEntry4(String name, String score, String date) {
		name4.setText(name);
		score4.setText(score);
		date4.setText(date);
	}

	/**
	 * This sets the entry on the leaderboard.
	 * 
	 * @param name  is the name of the player who created the high score.
	 * @param score is the final score of the player.
	 * @param date  is the date on which high score was created.
	 */
	private void setEntry5(String name, String score, String date) {
		name5.setText(name);
		score5.setText(score);
		date5.setText(date);
	}

	/**
	 * This sets the entry on the leaderboard.
	 * 
	 * @param name  is the name of the player who created the high score.
	 * @param score is the final score of the player.
	 * @param date  is the date on which high score was created.
	 */
	private void setEntry6(String name, String score, String date) {
		name6.setText(name);
		score6.setText(score);
		date6.setText(date);
	}

	/**
	 * This sets the entry on the leaderboard.
	 * 
	 * @param name  is the name of the player who created the high score.
	 * @param score is the final score of the player.
	 * @param date  is the date on which high score was created.
	 */
	private void setEntry7(String name, String score, String date) {
		name7.setText(name);
		score7.setText(score);
		date7.setText(date);
	}

	/**
	 * This sets the entry on the leaderboard.
	 * 
	 * @param name  is the name of the player who created the high score.
	 * @param score is the final score of the player.
	 * @param date  is the date on which high score was created.
	 */
	private void setEntry8(String name, String score, String date) {
		name8.setText(name);
		score8.setText(score);
		date8.setText(date);
	}

	/**
	 * This sets the entry on the leaderboard.
	 * 
	 * @param name  is the name of the player who created the high score.
	 * @param score is the final score of the player.
	 * @param date  is the date on which high score was created.
	 */
	private void setEntry9(String name, String score, String date) {
		name9.setText(name);
		score9.setText(score);
		date9.setText(date);
	}

	/**
	 * This sets the entry on the leaderboard.
	 * 
	 * @param name  is the name of the player who created the high score.
	 * @param score is the final score of the player.
	 * @param date  is the date on which high score was created.
	 */
	private void setEntry10(String name, String score, String date) {
		name10.setText(name);
		score10.setText(score);
		date10.setText(date);
	}
}
