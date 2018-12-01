import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	Leaderboard() {
		topScorers = new ArrayList<Player>();
	}

	public void updateLeaderboard() {
		for (int i = 0; i < 10; i++) {
			if (i < topScorers.size()) {
				controller.updateLeaderboard(topScorers.get(i).getName(), topScorers.get(i).getScore(),
						topScorers.get(i).getDateHighScoreMade(), i + 1);
			} else {
				controller.updateLeaderboard("", "", "", i + 1);
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

	public void serialize() throws IOException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream("leaderboard.txt"));
			out.writeObject(this);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static Leaderboard deserialize() throws IOException, ClassNotFoundException {
		Leaderboard obj;
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream("leaderboard.txt"));
			obj = (Leaderboard) in.readObject();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return obj;
	}

	public void checkNewHighScore(int score, String name) {
		boolean toBeAdded = false;
		if (topScorers.size() < 10)
			toBeAdded = true;
		else if (Integer.valueOf(topScorers.get(topScorers.size() - 1).getScore()) < score)
			toBeAdded = true;

		if (toBeAdded)
			addScoreToLeaderboard(score, name);
	}

	private void addScoreToLeaderboard(int score, String name) {
		if (topScorers.size() == 10)
			topScorers.remove(topScorers.size() - 1);
		// hurray New High Score Created
		
		topScorers.add(new Player(name, score, String.valueOf(java.time.LocalDate.now())));
		int i = topScorers.size() - 1;

		while (i > 0
				&& Integer.valueOf(topScorers.get(i).getScore()) > Integer.valueOf(topScorers.get(i - 1).getScore())) {
			Player temp = topScorers.get(i);
			topScorers.set(i, topScorers.get(i - 1));
			topScorers.set(i - 1, temp);
			i -= 1;
		}
		try {
			serialize();
		} catch (IOException e) {
			System.out.println("Can't serialize Leaderboard");
		}
	}

}
