import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This is the controller class for FXML MainPage which links it to other pages.
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class MainPageController {
	
	/**
	 * startGame is the fx:id assigned to start game button
	 */
	@FXML
	private ImageView startGame;
	
	/**
	 * This method is called when the user clicks on start game method. 
	 * @param e is the MouseEvent which stores the state of the mouse when the even had occured
	 */
	@FXML
	public void tapToStart(MouseEvent e) {
		Stage stage = (Stage) startGame.getScene().getWindow();
		stage.hide();
		new Game().start(new Stage());
	}
	
	/**
	 * resumeGame is the fx:id assigned to the resume game button
	 */
	@FXML
	private ImageView resumeGame;
	
	/**
	 * This method is called when the user clicks resume game button to resume the game
	 * @param e is the mousevent which has the state of the mouse at the time of the click
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void tapToResume(MouseEvent e) throws ClassNotFoundException, IOException {
		Stage stage = (Stage) startGame.getScene().getWindow();
		stage.hide();
		(Game.deserialize()).start(new Stage());
	}
	
	/**
	 * displayLeaderboard is the fx:id assigned to the display leaderboard button
	 */
	@FXML
	private ImageView displayLeaderboard;
	
	/**
	 * This method is called when the user clicks display leaderboard button 
	 * @param e is the mousevent which has the state of the mouse at the time of the click
	 * @throws Exception
	 */
	@FXML
	public void openLeaderboard(MouseEvent e) throws Exception {
		Stage stage = (Stage) displayLeaderboard.getScene().getWindow();
		stage.hide();
		MainPage.displayLeaderboard();
	}
	
	/**
	 * displayGuide is the fx:id assigned to the instructions button
	 */
	@FXML
	private ImageView displayGuide;
	
	/**
	 This method is called when the user clicks instructions button
	 * @param e is the mousevent which has the state of the mouse at the time of the click
	 * @throws Exception
	 */
	@FXML
	public void openInstructions(MouseEvent e) throws Exception {
		Stage stage = (Stage) displayGuide.getScene().getWindow();
		stage.hide();
		new Instructions().start(new Stage());
	}
	
	/**
	 * exitGame is the fx:id assigned to the exit button
	 */
	@FXML
	private ImageView exitGame;

	/**
	 * This method is called when the user clicks exit button
	 * @param e is the mousevent which has the state of the mouse at the time of the click
	 * @throws Exception
	 */
	@FXML
	public void exit(MouseEvent e) throws Exception {
		Stage stage = (Stage) exitGame.getScene().getWindow();
		stage.close();
	}
	
	/**
	 *  last score is the last score of player
	 */
	@FXML
	private Text last_score;
	
	/**
	 * This method updates the last score on screen
	 * @param score is the score of the player
	 */
	public void updateLastScore(String score) {
		last_score.setText(score);
	}
	
	/**
	 * ttlCoins is the total no. of coins collected by the player
	 */
	@FXML
	private Text ttlCoins;
	
	/**
	 * This method updates the total no. of coins on screen
	 * @param coins is the total no. of coins
	 */
	public void updateCoins(String coins) {
		ttlCoins.setText(coins);
	}

}
