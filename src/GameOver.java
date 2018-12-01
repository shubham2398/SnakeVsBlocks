import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameOver extends Application {

	private static GameOverController controller = null;
	private static GameOver gover = null;

	private GameOver() {

	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
		Parent root = loader.load();
		controller = (GameOverController) loader.getController();
		Scene scene = new Scene(root, 406, 650);
		stage.setScene(scene);
		stage.show();
	}

	public static void gameOver(int score) {
		if (gover == null)
			gover = new GameOver();
		try {
			gover.start(new Stage());
		} catch (Exception e) {
			System.out.println("Game Over screen can't be displayed");
		}
		controller.setFinalScore(Integer.toString(score));
	}

}
