import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainPage extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
		Scene scene = new Scene(root, 406, 650);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public ImageView startGame;

	@FXML
	public void tapToStart(MouseEvent e) {
		Stage stage = (Stage) startGame.getScene().getWindow();
		stage.hide();
		new Game().start(new Stage());
	}
	
	@FXML
	public ImageView resumeGame;

	@FXML
	public void tapToResume(MouseEvent e) {
		Stage stage = (Stage) startGame.getScene().getWindow();
		stage.hide();
		new Game().start(new Stage());
	}

	@FXML
	public ImageView displayLeaderboard;

	@FXML
	public void openLeaderboard(MouseEvent e) throws Exception {
		Stage stage = (Stage) displayLeaderboard.getScene().getWindow();
		stage.hide();
		new Leaderboard().start(new Stage());
	}
	
	@FXML
	public ImageView displayGuide;

	@FXML
	public void openInstructions(MouseEvent e) throws Exception {
		Stage stage = (Stage) displayGuide.getScene().getWindow();
		stage.hide();
		new Instructions().start(new Stage());
	}

	@FXML
	public ImageView exitGame;

	@FXML
	public void exit(MouseEvent e) throws Exception {
		Stage stage = (Stage) exitGame.getScene().getWindow();
		stage.close();
	}

}
