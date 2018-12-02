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

/**
 * This class represents the leaderboard with the list of top 10 players. It is
 * serializable.
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class Leaderboard extends Application implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * topScorers is the AraayList of Players who are top 10 scorers. controller is
	 * the object of the LeaderBoardController class used to display the data on
	 * leaderboard using FXML leaderboard is the static Leaderboard as there will be
	 * only one instance of leaderboard and that is this variable
	 */
	private ArrayList<Player> topScorers;
	private transient LeaderboardController controller;
	private static Leaderboard leaderboard;

	/**
	 * The constructor assigns a new list of Players to the topScorers. It is made
	 * private because there will be only 1 leaderboard in the game. So it is
	 * implemented with Singleton design pattern.
	 */
	private Leaderboard() {
		topScorers = new ArrayList<Player>();
	}

	/**
	 * This gives the instance of LeaderBoard and since there is only one leadeboard
	 * hence it does so by singleton design pattern. 
	 * @return leaderboard static object
	 */
	public static Leaderboard getInstance() {
		if (leaderboard == null) {
			leaderboard = new Leaderboard();
		}
		return leaderboard;
	}

	/**
	 * This method updates the leaderboard according to the contents of topScorers
	 * list
	 */
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

	/**
	 * This method overrides the start method of Application class to display the
	 * leaderboard using FXML.
	 * 
	 * @throws IOException
	 */
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Leaderboard.fxml"));
		Parent root = loader.load();
		controller = (LeaderboardController) loader.getController();

		Scene scene = new Scene(root, 406, 650);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This method serializes the leaderboard and stores it in a file names
	 * leaderboard.txt
	 * 
	 * @throws IOException
	 */
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

	/**
	 * This static method is used to deserialize the leaderboard object stored in
	 * leaderboard.txt
	 * 
	 * @return Leaderboard type object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Leaderboard deserialize() throws IOException, ClassNotFoundException {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream("leaderboard.txt"));
			leaderboard = (Leaderboard) in.readObject();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return leaderboard;
	}

	/**
	 * This method checks whether the given score is a new highScore or not and
	 * whether it needs to be inserted in the topScorers list or not.
	 * 
	 * @param score is the score made by the player
	 * @param name  is the name of the player
	 */
	public void checkNewHighScore(int score, String name) {
		boolean toBeAdded = false;
		if (topScorers.size() < 10)
			toBeAdded = true;
		else if (Integer.valueOf(topScorers.get(topScorers.size() - 1).getScore()) < score)
			toBeAdded = true;

		if (toBeAdded)
			addScoreToLeaderboard(score, name);
	}

	/**
	 * This method adds a given score and name to the leaderboard and topScorers
	 * list with current date.
	 * 
	 * @param score is the score of the player
	 * @param name  is the name of the player
	 */
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
