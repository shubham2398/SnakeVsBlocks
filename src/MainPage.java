import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is the class corresponding to the MainPage where there is option
 * to start, resume, leaderboard and exit. This is the first page that the user
 * sees when he starts the application.
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class MainPage extends Application {
	/**
	 * controller is the MainPageController object which is used for providing
	 * eventListeners to link different pages using FXML. leaderboard is the object
	 * that holds the leaderboard of players last score represents the last score
	 * made by the player ttlCoins store the total number of coins collected by the
	 * player
	 */
	private static MainPageController controller;
	private static Leaderboard leaderboard;
	private static int lastScore = 0;
	private static int ttlCoins = 0;

	/**
	 * It is executed as the first thing when the program starts.
	 * 
	 * @param args are the command line arguments
	 * @throws IOException If any serialization, deserialization unsuccessful
	 * @throws ClassNotFoundException if any deserialiation unsuccessful
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		loadLeaderBoard();
		loadCoins();
		loadLastScore();
		launch();
	}

	/**
	 * This overrides the start method of Application class. It is used to spawn a
	 * new thread which will display the stage of our application. All other
	 * elements are loaded onto it. Here we also check whether we have a resume.txt
	 * file to resume the game play from previous session to display appropriate
	 * page.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = null;
		File f = new File("resume.txt");

		if (f.exists() && !f.isDirectory())
			loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
		else
			loader = new FXMLLoader(getClass().getResource("MainPage2.fxml"));
		Parent root = loader.load();
		controller = (MainPageController) loader.getController();
		loadLastScore();
		controller.updateLastScore(Integer.toString(lastScore));
		controller.updateCoins(Integer.toString(ttlCoins));

		Scene scene = new Scene(root, 406, 650);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This method is used to deserialize the leaderboard if it is stored, else make
	 * a new Leaderboard
	 * 
	 * @throws ClassNotFoundException
	 */
	private static void loadLeaderBoard() throws ClassNotFoundException {
		try {
			leaderboard = Leaderboard.deserialize();
		} catch (IOException e) {
			leaderboard = Leaderboard.getInstance();
		}
	}

	/**
	 * This method is used to deserialize last score if it is stored, else
	 * initialise it to zero
	 * 
	 * @throws IOException
	 */
	private static void loadLastScore() throws IOException {
		File f = new File("lastscore.txt");
		if (f.exists() && !f.isDirectory()) {
			DataInputStream in = null;
			try {
				in = new DataInputStream(new BufferedInputStream(new FileInputStream("lastscore.txt")));
				lastScore = in.readInt();
			} catch (EOFException e) {
				lastScore = 0;
			} finally {
				if (in != null) {
					in.close();
				}
			}
		}
	}

	/**
	 * This method is used to deserialize the total coins if it is stored, else
	 * initial it to zero.
	 * 
	 * @throws IOException
	 */
	private static void loadCoins() throws IOException {
		File f = new File("coins.txt");
		if (f.exists() && !f.isDirectory()) {
			DataInputStream in = null;
			try {
				in = new DataInputStream(new BufferedInputStream(new FileInputStream("coins.txt")));
				ttlCoins = in.readInt();
			} catch (EOFException e) {
				ttlCoins = 0;
			} finally {
				if (in != null) {
					in.close();
				}
			}
		}
	}

	/**
	 * This method is used to serialize last score. It also checks the leaderboard
	 * if this score can be added to it.
	 * 
	 * @param score is the score of the player
	 * @param name  is the name of the player
	 * @throws IOException if serialization unsuccessful
	 */
	public static void setLastScore(int score, String name) throws IOException {
		leaderboard.checkNewHighScore(score, name);
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("lastscore.txt")));
			out.writeInt(score);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public static int getCoins()
	{
		return ttlCoins;
	}

	/**
	 * This method displays the main page
	 */
	public static void displayMainPage() {
		try {
			new MainPage().start(new Stage());
		} catch (Exception e) {
			System.out.println("Error in displaying Main Page");
		}
	}

	/**
	 * This method displays the leaderboard
	 * 
	 * @throws Exception if exception thrown
	 */
	public static void displayLeaderboard() throws Exception {
		leaderboard.start(new Stage());
		leaderboard.updateLeaderboard();
	}

	/**
	 * This method adds the coins to ttlCoins and serializes it
	 * 
	 * @param coins are the number of coins to be added
	 * @throws IOException if exception thrown
	 */
	public static void addCoins(int coins) throws IOException {
		ttlCoins += coins;
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("coins.txt")));
			out.writeInt(ttlCoins);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
