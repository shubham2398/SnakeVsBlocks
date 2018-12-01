import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOverController {
	
	@FXML
	private Text finalScore;
	
	@FXML
	private TextField name;
	
	@FXML
	private Button submitName;
	
	@FXML
	public void enterName(MouseEvent e) throws Exception {
		Stage stage = (Stage) finalScore.getScene().getWindow();
		stage.close();
		String userName = name.getText();
		MainPage.setLastScore(Integer.valueOf(finalScore.getText()), userName);
		MainPage.displayMainPage();
	}
	
	public void setFinalScore(String score) {
		finalScore.setText(score);
	}
}
