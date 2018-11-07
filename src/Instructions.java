import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Instructions extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Instructions.fxml"));
		Scene scene = new Scene(root, 406, 650);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public ImageView goBack;

	@FXML
	public void displayMainPage(MouseEvent e) throws Exception {
		Stage stage = (Stage) goBack.getScene().getWindow();
		stage.hide();
		new MainPage().start(new Stage());
	}
	
	@FXML
	public ImageView exitGame;
	
	@FXML
	public void exit(MouseEvent e) throws Exception {
		Stage stage = (Stage) exitGame.getScene().getWindow();
		stage.close();
	}
}
