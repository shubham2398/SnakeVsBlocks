import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPage extends Application {

	private static FXMLController controller;
	private static Stage primaryStage;
	private static ArrayList<Player> topScorers = new ArrayList<Player>();
	private static Leaderboard leaderboard = new Leaderboard();

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
		Parent root = loader.load();
		controller = (FXMLController) loader.getController();

		Scene scene = new Scene(root, 406, 650);
		stage.setScene(scene);
		stage.show();
	}

	public static void setLastScore(int score) {
		checkNewHighScore(score);
		controller.updateLastScore(Integer.toString(score));
	}

	private static void checkNewHighScore(int score) {
		boolean toBeAdded = false;
		if (topScorers.size() < 10)
			toBeAdded = true;
		else if (Integer.valueOf(topScorers.get(topScorers.size() - 1).getScore()) < score)
			toBeAdded = true;

		if (toBeAdded)
			addScoreToLeaderboard(score);
	}

	private static void addScoreToLeaderboard(int score) {
		if (topScorers.size() == 10)
			topScorers.remove(topScorers.size() - 1);
		// hurray New High Score Created
		topScorers.add(new Player("guest", score, "17/11/18"));
		int i = topScorers.size() - 1;

		while (i > 0
				&& Integer.valueOf(topScorers.get(i).getScore()) > Integer.valueOf(topScorers.get(i - 1).getScore())) {
			Player temp = topScorers.get(i);
			topScorers.set(i, topScorers.get(i - 1));
			topScorers.set(i - 1, temp);
			i -= 1;
		}
	}

	public static void displayMainPage() {
		primaryStage.show();
	}

	public static void displayLeaderboard() throws Exception {
		leaderboard.start(new Stage());
		leaderboard.updateLeaderboard(topScorers);
		
		for(int i = 0;i<topScorers.size();i++)
		{
			System.out.println(topScorers.get(i).getName() + " " + topScorers.get(i).getScore() + " " + topScorers.get(i).getDateHighScoreMade());
		}
		System.out.println();
	}
}
