import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPageController {

	@FXML
	private ImageView startGame;

	@FXML
	public void tapToStart(MouseEvent e) {
		Stage stage = (Stage) startGame.getScene().getWindow();
		stage.hide();
		new Game().start(new Stage());
	}

	@FXML
	private ImageView resumeGame;

	@FXML
	public void tapToResume(MouseEvent e) throws ClassNotFoundException, IOException {
		Stage stage = (Stage) startGame.getScene().getWindow();
		stage.hide();
		(Game.deserialize()).start(new Stage());
	}

	@FXML
	private ImageView displayLeaderboard;

	@FXML
	public void openLeaderboard(MouseEvent e) throws Exception {
		Stage stage = (Stage) displayLeaderboard.getScene().getWindow();
		stage.hide();
		MainPage.displayLeaderboard();
	}

	@FXML
	private ImageView displayGuide;

	@FXML
	public void openInstructions(MouseEvent e) throws Exception {
		Stage stage = (Stage) displayGuide.getScene().getWindow();
		stage.hide();
		new Instructions().start(new Stage());
	}

	@FXML
	private ImageView exitGame;

	@FXML
	public void exit(MouseEvent e) throws Exception {
		Stage stage = (Stage) exitGame.getScene().getWindow();
		stage.close();
	}

	@FXML
	private Text last_score;

	public void updateLastScore(String score) {
		last_score.setText(score);
	}
	
	@FXML
	private Text ttlCoins;
	
	public void updateCoins(String coins) {
		ttlCoins.setText(coins);
	}

}
