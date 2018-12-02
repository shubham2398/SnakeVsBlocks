import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author SHUBHAM _THAKRAL, TANMAY BANSAL This class is the Instructions class.
 *         It extends the Application class and creates the javafx stage for the
 *         Instructions screen. It overrides the start method of the Application
 *         class. It shows the objective of game and how to play it.
 */
public class Instructions extends Application {
	/**
	 * goBack is the fx:id of the back button, which when clicked will take the screen back to main page.
	 * exit is the fx:id of the exit button, which when clicked will exit the game.
	 */

	/**
	 * This function overrides the start method the Application class. It is used to
	 * spawn a new thread which will display the stage of the instructions screen.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Instructions.fxml"));
		Scene scene = new Scene(root, 406, 650);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public ImageView goBack;

	/**
	 * This function is used to go back to the previous screen i.e. the main page, when the back button is clicked.
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
	 * This function is used to exit the game, when the exit button is clicked.
	 * @param e is the MouseEvent which stores the state of the mouse.
	 */
	@FXML
	public void exit(MouseEvent e) {
		Stage stage = (Stage) exitGame.getScene().getWindow();
		stage.close();
	}
}
