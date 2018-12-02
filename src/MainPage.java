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

public class MainPage extends Application {

	private static MainPageController controller;
	private static Stage primaryStage;
	private static Leaderboard leaderboard;
	private static int lastScore = 0;
	private static int ttlCoins = 0;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		loadLeaderBoard();
		loadCoins();
		loadLastScore();
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
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

	private static void loadLeaderBoard() throws ClassNotFoundException {
		try {
			leaderboard = Leaderboard.deserialize();
		} catch (IOException e) {
			leaderboard = new Leaderboard();
		}
	}

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

	public static void displayMainPage() {
		try {
			new MainPage().start(new Stage());
		} catch (Exception e) {
			System.out.println("Error in displaying Main Page");
		}
	}

	public static void displayLeaderboard() throws Exception {
		leaderboard.start(new Stage());
		leaderboard.updateLeaderboard();
	}

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
