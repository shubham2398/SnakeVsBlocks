import java.io.Serializable;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Leaderboard extends Application implements Serializable {

	private ArrayList<Player> topScorers;
	private transient LeaderboardController controller;

	public void updateLeaderboard(ArrayList<Player> rankList) {
		topScorers = rankList;
		for(int i = 0; i < 10; i++)
		{
			if(i<topScorers.size())
			{
				controller.updateLeaderboard(topScorers.get(i).getName(), topScorers.get(i).getScore(), topScorers.get(i).getDateHighScoreMade(), i+1);
			}
			else
			{
				controller.updateLeaderboard("", "", "", i+1);
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Leaderboard.fxml"));
		Parent root = loader.load();
		controller = (LeaderboardController) loader.getController();
		
		Scene scene = new Scene(root, 406, 650);
		stage.setScene(scene);
		stage.show();
	}

}
