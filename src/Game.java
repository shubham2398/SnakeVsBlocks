import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Game extends Application implements Serializable {
	private int speed;
	private transient Stage stage;
	private transient Scene scene;
	private transient GraphicsContext gc;
	private final int BALL_SIZE;
	private final int BLOCK_SIZE;
	private final int GAME_SPEED = 300;
	private int ADD_SPEED = 0;
	private final int snake_left_right_speed = 450;
	private String ballImagePath;
	private final String ballPowerUpImagePath;
	private final String coinImagePath;
	private final String magnetImagePath;
	private final String shieldImagePath;
	private final String destroyBlocksPowerUpImagePath;
	private final String doubleScorePowerUpImagePath;
	private final String blockRelativePath;
	private final String wallPath;
	private final String blockExplosionPath;
	private final String tokenExplosionPath;
	private ArrayList<String> input = new ArrayList<String>();
	private ArrayList<Sprite> allElements = new ArrayList<Sprite>();
	private Snake snake;
	private int snakeYPos;
	private double snakeToBeShiftedTo;
	private final int[] screenCoordinates = { 0, 406, 0, 650 };
	private Animation myAnimation;
	private transient Random rnd;

	public Game() {
		speed = this.GAME_SPEED + this.ADD_SPEED;
		snakeYPos = 450;
		BLOCK_SIZE = (screenCoordinates[1] - 6) / 5;
		BALL_SIZE = BLOCK_SIZE / 4;
		ballImagePath = "file:images/ball/yellow.png";
		ballPowerUpImagePath = "file:images/ball/blue.png";
		shieldImagePath = "file:images/shield.png";
		coinImagePath = "file:images/coin.gif";
		magnetImagePath = "file:images/magnet.jpg";
		destroyBlocksPowerUpImagePath = "file:images/bomb.gif";
		blockRelativePath = "file:images/block/";
		wallPath = "file:images/wall.png";
		blockExplosionPath = "file:images/explosion.gif";
		tokenExplosionPath = "file:images/token_explosion.gif";
		doubleScorePowerUpImagePath = "file:images/double_score.png";
		myAnimation = new Animation(this);
		rnd = new Random();
		snake = new Snake(screenCoordinates);
		snake.setImage(new Image(ballImagePath, BALL_SIZE, BALL_SIZE, false, true));
		snake.setPosition(200, snakeYPos);
	}

	public void serialize() throws IOException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream("resume.txt"));
			out.writeObject(this);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static Game deserialize() throws IOException, ClassNotFoundException {
		Game obj;
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream("resume.txt"));
			obj = (Game) in.readObject();
			obj.rnd = new Random();
			obj.snake.setImage(new Image(obj.ballImagePath, obj.BALL_SIZE, obj.BALL_SIZE, false, true));
			for (Sprite s : obj.allElements) {
				s.setImage();
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return obj;
	}

	private void initialise() {
		stage.setTitle("Snake Game");
		stage.setResizable(false);
		stage.sizeToScene();

		BorderPane root = new BorderPane();
		Pane paneCenter = new Pane();
		StackPane holder = new StackPane();
		Canvas canvas = new Canvas(screenCoordinates[1], screenCoordinates[3]);

		holder.getChildren().add(canvas);
		paneCenter.getChildren().add(holder);
		root.setCenter(paneCenter);
		holder.setStyle("-fx-background-image: url('file:images/backg5.jpg')");

		final Image image = new Image("file:images/pause.jpg", 40, 40, false, false);
		MenuButton menuButton = new MenuButton("", new ImageView(image));
		menuButton.setStyle("-fx-background-color: transparent");
		menuButton.setPrefSize(40, 40);
		menuButton.relocate(10, 590);

		MenuItem resumeGame = new MenuItem("Resume Game");
		MenuItem restartGame = new MenuItem("Restart Game");
		MenuItem mainMenu = new MenuItem("Main Menu");
		MenuItem exit = new MenuItem("Exit");
		menuButton.getItems().addAll(resumeGame, restartGame, mainMenu, exit);

		paneCenter.getChildren().add(menuButton);

		menuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				myAnimation.lastNanoTime.value = myAnimation.currentNanoTime;
				// System.out.println(lastNanoTime.value);
				myAnimation.flag.value = 1;
				myAnimation.stop();
			}
		});
		
		

		resumeGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				myAnimation.start();
			}
		});

		restartGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				myAnimation.stop();
				stage.close();
				new Game().start(new Stage());
			}
		});

		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					serialize();
					stage.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		mainMenu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// myAnimation.start();
				try {
					myAnimation.lastNanoTime.value = myAnimation.currentNanoTime;
					myAnimation.flag.value = 1;
					myAnimation.stop();
					serialize();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				endGame2();
			}
		});

		scene = new Scene(root, screenCoordinates[1], screenCoordinates[3]);
		stage.setScene(scene);
		
		this.scene.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.P) {
				myAnimation.lastNanoTime.value = myAnimation.currentNanoTime;
				myAnimation.flag.value = 1;
				myAnimation.stop();
			}
		});

		gc = canvas.getGraphicsContext2D();

		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				snakeToBeShiftedTo = mouseEvent.getSceneX();
			}
		});
	}

	@Override
	public void start(Stage theStage) {
		this.stage = theStage;
		initialise();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try {
					myAnimation.lastNanoTime.value = myAnimation.currentNanoTime;
					myAnimation.flag.value = 1;
					myAnimation.stop();
					serialize();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		myAnimation.start();

		theStage.show();
	}

	public Sprite makeBallPowerUp(double px, double py, int value) {
		Sprite ball = new BallPowerUp(screenCoordinates, value);
		ball.setImage(new Image(ballPowerUpImagePath, BALL_SIZE, BALL_SIZE, true, false));
		ball.setImagePath(ballPowerUpImagePath);
		ball.setPosition(px, py);
		ball.addVelocity(0, speed);
		return ball;
	}

	public Sprite makeShield(double px, double py) {
		Sprite shield = new Shield(this.screenCoordinates);
		shield.setImage(new Image(shieldImagePath, BALL_SIZE, BALL_SIZE, true, false));
		shield.setImagePath(shieldImagePath);
		shield.setPosition(px, py);
		shield.addVelocity(0, speed);
		return shield;
	}

	public Sprite makeCoin(double px, double py) {
		Sprite coin = new Coin(this.screenCoordinates);
		coin.setImage(new Image(coinImagePath, BALL_SIZE, BALL_SIZE, true, false));
		coin.setImagePath(coinImagePath);
		coin.setPosition(px, py);
		coin.addVelocity(0, speed);
		return coin;
	}

	public Sprite makeMagnet(double px, double py) {
		Sprite magnet = new Magnet(this.screenCoordinates);
		magnet.setImage(new Image(magnetImagePath, BALL_SIZE, BALL_SIZE, true, false));
		magnet.setImagePath(magnetImagePath);
		magnet.setPosition(px, py);
		magnet.addVelocity(0, speed);
		return magnet;
	}

	public Sprite makeDestroyBlocksPowerUp(double px, double py) {
		Sprite bomb = new DestroyBlocksPowerUp(this.screenCoordinates);
		bomb.setImage(new Image(destroyBlocksPowerUpImagePath, BALL_SIZE + 20, BALL_SIZE + 20, true, false));
		bomb.setImagePath(destroyBlocksPowerUpImagePath);
		bomb.setPosition(px, py);
		bomb.addVelocity(0, speed);
		return bomb;
	}
	
	public Sprite makeDobleScorePowerUp(double px, double py) {
		Sprite x2 = new DoubleScorePowerUp(this.screenCoordinates);
		x2.setImage(new Image(doubleScorePowerUpImagePath, BALL_SIZE + 20, BALL_SIZE + 20, true, false));
		x2.setImagePath(destroyBlocksPowerUpImagePath);
		x2.setPosition(px, py);
		x2.addVelocity(0, speed);
		return x2;
	}

	public Sprite makeBlock(double px, double py, int num) {
		Sprite block = new Block(this.screenCoordinates, num);
		block.setPosition(px, py);
		block.addVelocity(0, speed);
		num = ((num - 1) / 5 + 1) * 5;
		block.setImage(
				new Image(blockRelativePath + (num - 4) + "-" + num + ".png", BLOCK_SIZE, BLOCK_SIZE, false, false));
		block.setImagePath(blockRelativePath + (num - 4) + "-" + num + ".png");
		return block;
	}

	public Sprite makeWall(double px, double py, int len, int side) {
		Sprite wall = new Wall(screenCoordinates);
		wall.setImage(new Image("file:images/wall.png", BALL_SIZE / 2, len, false, false));
		wall.setImagePath(wallPath);
		wall.setPosition(px, py - (len + BLOCK_SIZE) * side);
		wall.addVelocity(0, speed);
		return wall;
	}
	/*
	 * 1. upar, ya neeche 2. length 50 - 100
	 */

	public ArrayList<Sprite> getAllElements() {
		return this.allElements;
	}

	public int getBLOCK_SIZE() {
		return this.BLOCK_SIZE;
	}

	public int[] getScreenCoordinates() {
		return this.screenCoordinates;
	}

	public Snake getSnake() {
		return this.snake;
	}

	public ArrayList<String> getInput() {
		return this.input;
	}

	public GraphicsContext getGc() {
		return this.gc;
	}

	public Stage getStage() {
		return this.stage;
	}

	public int getSnakeYPos() {
		return snakeYPos;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void addSpeed(int speed) {
		this.ADD_SPEED = speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setSpeedToDefault() {
		this.speed = this.GAME_SPEED + this.ADD_SPEED;
	}

	public double getSnakeToBeShiftedTo() {
		return this.snakeToBeShiftedTo;
	}

	public void endGame() {
		System.out.println("Game Ended");
		stage.close();
		try {
			MainPage.addCoins(snake.getCoins());
		} catch (IOException e) {
			
		}
		GameOver.gameOver(snake.getScore());
	}

	public void endGame2() {
		System.out.println("Gello 2");
		stage.close();
		MainPage.displayMainPage();
	}

	public String getBlockExplosionPath() {
		return blockExplosionPath;
	}

	public String getTokenExplosionPath() {
		return tokenExplosionPath;
	}

	public int getSnake_left_right_speed() {
		return snake_left_right_speed;
	}

	public int getBallSize() {
		return this.BALL_SIZE;
	}
}