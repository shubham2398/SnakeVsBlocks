import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * @author SHUBHAM _THAKRAL, TANMAY BANSAL This class is the GameOverController
 *         class. It is the controller of the java fxml Game OverClass. It is
 *         used to set the text and fetch the name of player entered on the
 *         screen.
 */
public class GameOverController {

	/**
	 * finalScore is the fx:id of the text box, which contains the final score of
	 * the player. name is the fx:id of the text box, which contains the name
	 * entered by the player. submitName is the fx:id of the okay button, which is
	 * used to save the name in leaderboard.
	 */

	@FXML
	private Text finalScore;

	@FXML
	private TextField name;

	@FXML
	private Button submitName;

	/**
	 * It is use to save the name of player into leaderboard when the ok button is
	 * clicked. It also closes the game over stage and displays the main page.
	 * 
	 * @param e
	 *            is the MouseEvent which stores the state of the mouse.
	 * @throws Exception,
	 *             It throws Exception if the stage creation fails.
	 */
	@FXML
	public void enterName(MouseEvent e) throws Exception {
		Stage stage = (Stage) finalScore.getScene().getWindow();
		stage.close();
		String userName = name.getText();
		MainPage.setLastScore(Integer.valueOf(finalScore.getText()), userName);
		MainPage.displayMainPage();
	}

	/**
	 * It is used to set the final score of the player in the text field available.
	 * 
	 * @param score
	 *            contains the final score of the player.
	 */
	public void setFinalScore(String score) {
		finalScore.setText(score);
	}
}
