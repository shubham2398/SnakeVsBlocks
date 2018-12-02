import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author SHUBHAM _THAKRAL, TANMAY BANSAL This class is the GameOver class. It
 *         extends the Application class. It creates the stage for the game over
 *         screen.
 */
public final class GameOver extends Application {

	/**
	 * controller is an object of the GameOverController class. It is used to set
	 * the final score of the player on the game over screen and get the input of
	 * the name entered by user gover is the only global object of GameOver class,
	 * since this class uses the singleton design technique and hence the object is
	 * made static.
	 */
	private static GameOverController controller = null;
	private static GameOver gover = null;
	private static boolean revive = false;

	/**
	 * The constructor is made private since there can be only one object of this
	 * class, and hence no other class can declare its object.
	 */
	private GameOver() {

	}

	/**
	 * This function overrides the start method the Application class. It is used to
	 * spawn a new thread which will display the stage of the game over class.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = null;
		if(revive)
			loader = new FXMLLoader(getClass().getResource("GameOverWithRevive.fxml"));
		else
			loader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
		Parent root = loader.load();
		controller = (GameOverController) loader.getController();
		Scene scene = new Scene(root, 406, 650);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This function is called when the game gets over, and we need to display to
	 * game over screen. It also sets the final score on this screen using the
	 * controller object.
	 * 
	 * @param score contains the final score of the player.
	 */
	public static void gameOver(int score) {
		revive = false;
		if (gover == null)
			gover = new GameOver();
		try {
			gover.start(new Stage());
		} catch (Exception e) {
			System.out.println("Game Over screen can't be displayed");
		}
		controller.setFinalScore(Integer.toString(score));
	}
	
	public static void gameOverWithRevive(int score) {
		revive = true;
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
