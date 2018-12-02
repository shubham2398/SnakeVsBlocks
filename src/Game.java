import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author SHUBHAM _THAKRAL, TANMAY BANSAL This class is the game class. It
 *         extends the Application class and implements the Serializable
 *         interface. It creates the javafx stage for the actual game and
 *         initializes the first start screen. It is also used to add the power
 *         ups, coins, blocks etc on the pane. It overrides the start method of
 *         the Application class and implements the serialize and deserialize
 *         methods to save and restore the state of a game.
 */
public class Game extends Application implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * It has various attributes which are private so that they can't be accessed by
	 * other classes, except Animation class (Encapsulation - data hiding). Some
	 * variables are made transient, since some can't be serialized while other
	 * don't need to be serialized. Some variables are made final, since we don't
	 * want them to get updated. The speed variable stores the current speed of the
	 * game, with which all the elements on the screen move down. This will be
	 * continuously updated depending upon the length of snake increases or
	 * decreases. The stage and the scene variables stores the Stage and the Scene
	 * of the javafx application where all the elements that need to be added to
	 * screen will be added. The gc is an attribute of the Graphics Context class,
	 * which stores the canvas of the game. Canvas is used to insert images on it.
	 * The BALL_SIZE variable stores the image dimensions of the ball. Since it will
	 * be a square hence only one side is stored as dimension. The BLOCK_SIZE
	 * variable stores the image dimensions of the block. Since it will be a square
	 * hence only one side is stored as dimension. The GAME_SPEED stores the initial
	 * speed of the game at which the game will begin. The ADD_SPPED stores the
	 * offset which needs to be added to the GAME_SPEED variable, i.e. the value by
	 * which the speed of the game should be increased. The snake_left_right_speed
	 * stores the speed of the snake with which it will move left or right. The
	 * ballImagePath stores the ball image path. This is used to serialize the ball
	 * image. Here ball refers to the balls of the snake. The ballPowerUpImagePath
	 * stores the ball image path. This is used to serialize the ball image. The
	 * coinImagePath stores the path of the coin image. This is used to serialize
	 * the coin image. The magnetImagePath stores the path of the magnet image. This
	 * is used to serialize the magnet image. The shieldImagePath stores the path of
	 * the shield image. This is used to serialize the shield image. The
	 * destroyBlocksPowerUpImagePath stores the path of the destroy blocks power up
	 * image. This is used to serialize the destroy blocks power up image. The
	 * doubleScorePowerUpImagePath stores the path of the double score power up
	 * image. This is used to serialize the double score power up image. The
	 * blockRelativePath stores the path of the block image. This is used to
	 * serialize the block image. The wallPath stores the path of the wall image.
	 * This is used to serialize the wall image. The blockExplosionPath stores the
	 * path of the block explosion image. This is used to serialize the block
	 * explosion image. The tokenExplosionPath stores the path of the token
	 * explosion image. This is used to serialize the block explosion image. The
	 * allElements stores the list which contains all the elements which are
	 * displayed on the game screen. The snake is an object of Snake class, and it
	 * used to render the snake during the game play. It stores the dimensions,
	 * velocity etc of the snake. The snakeYPos stores the initial Y coordinate of
	 * the snake which in turn is further used to initialize the snake object. The
	 * snakeToBeShiftedTo stores the x-coordinate of the position at which snake has
	 * to be moved, depending upon the mouse location. The screenCoordinates stores
	 * the dimensions of the stage and the canvas. The myAnimation is an object of
	 * the Animation class, which is used to render 60 frames in a second.
	 */
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
	private ArrayList<Sprite> allElements = new ArrayList<Sprite>();
	private Snake snake;
	private int snakeYPos;
	private double snakeToBeShiftedTo;
	private final int[] screenCoordinates = { 0, 406, 0, 650 };
	private Animation myAnimation;

	/**
	 * The constructor of game class initializes all the instances of the game
	 * class.
	 */
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
		snake = new Snake(screenCoordinates);
		snake.setImage(new Image(ballImagePath, BALL_SIZE, BALL_SIZE, false, true));
		snake.setPosition(200, snakeYPos);
	}

	/**
	 * This function is used to serialize the ongoing game, if it is closed
	 * abruptly.
	 * @throws IOException is thrown 
	 */
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

	/**
	 * This function is used to restore the last state of the game, when the player
	 * closed it while playing.
	 * 
	 * @return It returns the object of Game class, which contains the state of the
	 *         last state of the game, when the player close the game abruptly.
	 * @throws IOException            It is thrown if the file resume.txt does not
	 *                                exist, or there is some error in reading the
	 *                                objects of the class.
	 * @throws ClassNotFoundException It is thrown if the JVM can't find the
	 *                                Game.class file.
	 */
	public static Game deserialize() throws IOException, ClassNotFoundException {
		Game obj;
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream("resume.txt"));
			obj = (Game) in.readObject();
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

	/**
	 * This method creates the stage, scene and canvas for the game. It also adds
	 * the game pause option and defines the event listeners for different buttons.
	 */
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
				myAnimation.setLastNanoTime(myAnimation.getCurrentNanoTime());
				// System.out.println(lastNanoTime.value);
				myAnimation.setFlag(1);
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
					myAnimation.setLastNanoTime(myAnimation.getCurrentNanoTime());
					myAnimation.setFlag(1);
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

		this.scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.P) {
				myAnimation.setLastNanoTime(myAnimation.getCurrentNanoTime());
				myAnimation.setFlag(1);
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

	/**
	 * This function overrides the start method the Application class. It is used to
	 * spawn a new thread which will display the stage of the game.
	 */
	@Override
	public void start(Stage theStage) {
		this.stage = theStage;
		initialise();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try {
					myAnimation.setLastNanoTime(myAnimation.getCurrentNanoTime());
					myAnimation.setFlag(1);
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

	/**
	 * This method is used to display a ball power up on canvas at a particular
	 * location.
	 * 
	 * @param px    is used to store the x-coordinate of the position where the
	 *              power up has to be displayed.
	 * @param py    is used to store the y-coordinate of the position where the
	 *              power up has to be displayed.
	 * @param value stores the value by which the length of the snake increases if
	 *              the snake will eat it.
	 * @return It returns the ball object of actual type BallPowerUp and declared
	 *         type Sprite class.
	 */
	public Sprite makeBallPowerUp(double px, double py, int value) {
		Sprite ball = new BallPowerUp(screenCoordinates, value);
		ball.setImage(new Image(ballPowerUpImagePath, BALL_SIZE, BALL_SIZE, true, false));
		ball.setImagePath(ballPowerUpImagePath);
		ball.setPosition(px, py);
		ball.addVelocity(0, speed);
		return ball;
	}

	/**
	 * This method is used to display the shield power up on canvas at a particular
	 * location.
	 * 
	 * @param px is used to store the x-coordinate of the position where the power
	 *           up has to be displayed.
	 * @param py is used to store the y-coordinate of the position where the power
	 *           up has to be displayed.
	 * @return It returns the shield object of actual type Shield and declared type
	 *         Sprite class.
	 */
	public Sprite makeShield(double px, double py) {
		Sprite shield = new Shield(this.screenCoordinates);
		shield.setImage(new Image(shieldImagePath, BALL_SIZE, BALL_SIZE, true, false));
		shield.setImagePath(shieldImagePath);
		shield.setPosition(px, py);
		shield.addVelocity(0, speed);
		return shield;
	}

	/**
	 * This method is used to display the coins on canvas at a particular location.
	 * 
	 * @param px is used to store the x-coordinate of the position where the power
	 *           up has to be displayed.
	 * @param py is used to store the y-coordinate of the position where the power
	 *           up has to be displayed.
	 * @return It returns the coin object of actual type Coin and declared type
	 *         Sprite class.
	 */
	public Sprite makeCoin(double px, double py) {
		Sprite coin = new Coin(this.screenCoordinates);
		coin.setImage(new Image(coinImagePath, BALL_SIZE, BALL_SIZE, true, false));
		coin.setImagePath(coinImagePath);
		coin.setPosition(px, py);
		coin.addVelocity(0, speed);
		return coin;
	}

	/**
	 * This method is used to display the magnet power up on canvas at a particular
	 * location.
	 * 
	 * @param px is used to store the x-coordinate of the position where the power
	 *           up has to be displayed.
	 * @param py is used to store the y-coordinate of the position where the power
	 *           up has to be displayed.
	 * @return It returns the magnet object of actual type Magnet and declared type
	 *         Sprite class.
	 */
	public Sprite makeMagnet(double px, double py) {
		Sprite magnet = new Magnet(this.screenCoordinates);
		magnet.setImage(new Image(magnetImagePath, BALL_SIZE, BALL_SIZE, true, false));
		magnet.setImagePath(magnetImagePath);
		magnet.setPosition(px, py);
		magnet.addVelocity(0, speed);
		return magnet;
	}

	/**
	 * This method is used to display the destroy blocks power up on canvas at a
	 * particular location.
	 * 
	 * @param px is used to store the x-coordinate of the position where the power
	 *           up has to be displayed.
	 * @param py is used to store the y-coordinate of the position where the power
	 *           up has to be displayed.
	 * @return It returns the bomb object of actual type DestroyBlocksPowerUp and
	 *         declared type Sprite class.
	 */
	public Sprite makeDestroyBlocksPowerUp(double px, double py) {
		Sprite bomb = new DestroyBlocksPowerUp(this.screenCoordinates);
		bomb.setImage(new Image(destroyBlocksPowerUpImagePath, BALL_SIZE + 20, BALL_SIZE + 20, true, false));
		bomb.setImagePath(destroyBlocksPowerUpImagePath);
		bomb.setPosition(px, py);
		bomb.addVelocity(0, speed);
		return bomb;
	}

	/**
	 * This method is used to display the double score power up on canvas at a
	 * particular location.
	 * 
	 * @param px is used to store the x-coordinate of the position where the power
	 *           up has to be displayed.
	 * @param py is used to store the y-coordinate of the position where the power
	 *           up has to be displayed.
	 * @return It returns the x2 object of actual type DoubleScorePowerUp and
	 *         declared type Sprite class.
	 */
	public Sprite makeDobleScorePowerUp(double px, double py) {
		Sprite x2 = new DoubleScorePowerUp(this.screenCoordinates);
		x2.setImage(new Image(doubleScorePowerUpImagePath, BALL_SIZE + 20, BALL_SIZE + 20, true, false));
		x2.setImagePath(destroyBlocksPowerUpImagePath);
		x2.setPosition(px, py);
		x2.addVelocity(0, speed);
		return x2;
	}

	/**
	 * This method is used to display the block power up on canvas at a particular
	 * location.
	 * 
	 * @param px is used to store the x-coordinate of the position where the power
	 *           up has to be displayed.
	 * @param py is used to store the y-coordinate of the position where the power
	 *           up has to be displayed.
	 * @param num is the the number with which block needs to be made
	 * @return It returns the block object of actual type Block and declared type
	 *         Sprite class.
	 */
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

	/**
	 * This method is used to display the wall power up on canvas at a particular
	 * location.
	 * 
	 * @param px is used to store the x-coordinate of the position where the power
	 *           up has to be displayed.
	 * @param py is used to store the y-coordinate of the position where the power
	 *           up has to be displayed.
	 * @param len is the length of wall
	 * @param side is the thickness of wall
	 * @return It returns the wall object of actual type Wall and declared type
	 *         Sprite class.
	 */
	public Sprite makeWall(double px, double py, int len, int side) {
		Sprite wall = new Wall(screenCoordinates);
		wall.setImage(new Image("file:images/wall.png", BALL_SIZE / 2, len, false, false));
		wall.setImagePath(wallPath);
		wall.setPosition(px, py - (len + BLOCK_SIZE) * side);
		wall.addVelocity(0, speed);
		return wall;
	}

	/**
	 * It returns the list which contains all the elements which are currently
	 * displayed on the screen.
	 * 
	 * @return allElements, which stores all the elements which are displayed on the
	 *         screen.
	 */
	public ArrayList<Sprite> getAllElements() {
		return this.allElements;
	}

	/**
	 * It return the dimensions of the block.
	 * 
	 * @return BLOCK_SIZE, which stores the dimensions of the block image.
	 */
	public int getBLOCK_SIZE() {
		return this.BLOCK_SIZE;
	}

	/**
	 * It returns the screen coordinates of the stage
	 * 
	 * @return screenCooridniates, which is an array of integer and stores the
	 *         screen coordinates.
	 */
	public int[] getScreenCoordinates() {
		return this.screenCoordinates;
	}

	/**
	 * It returns the snake object of the Game class.
	 * 
	 * @return snake, which is of type Snake.
	 */
	public Snake getSnake() {
		return this.snake;
	}

	/**
	 * It returns the graphic context of the scene.
	 * 
	 * @return gc, which contains the graphic context.
	 */
	public GraphicsContext getGc() {
		return this.gc;
	}

	/**
	 * This method returns the stage of the game.
	 * 
	 * @return stage,
	 */
	public Stage getStage() {
		return this.stage;
	}

	/**
	 * It is used to return the y-coordinate of the position of the snake.
	 * 
	 * @return snakeYPos, which stores the y-coordinate of the location of the
	 *         snake.
	 */
	public int getSnakeYPos() {
		return snakeYPos;
	}

	/**
	 * It is used to return the current speed of the game.
	 * 
	 * @return speed, which stores the speed at which the game is currently running.
	 */
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * This function sets the ADD_SPEED variable of the Game class. It sets the
	 * variable the speed with which the game speed has to be increased.
	 * 
	 * @param speed contains the speed with which the game speed has to be
	 *              increased.
	 */
	public void addSpeed(int speed) {
		this.ADD_SPEED = speed;
	}

	/**
	 * This function is used to set the current speed of the game.
	 * 
	 * @param speed contains the speed at which the game speed has to be set to.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * This is used to restore the speed of the game back to normal/default speed of
	 * the game.
	 */
	public void setSpeedToDefault() {
		this.speed = this.GAME_SPEED + this.ADD_SPEED;
	}

	/**
	 * It returns the location at which the snake needs to be shifted to.
	 * 
	 * @return snakeToBeShiftedTo, which stores the destination location of the
	 *         snake where the snake has to be moved.
	 */
	public double getSnakeToBeShiftedTo() {
		return this.snakeToBeShiftedTo;
	}

	/**
	 * This method redirects the game page to the game over screen and closes the
	 * current stage.
	 */
	public void endGame() {
		System.out.println("Game Ended");
		stage.close();
		
		myAnimation.setLastNanoTime(myAnimation.getCurrentNanoTime());
		myAnimation.setFlag(1);
		myAnimation.stop();
		
		try {
			MainPage.addCoins(snake.getCoins());
			serialize();
		} catch (IOException e) {

		}
		if(MainPage.getCoins()>=20)
			GameOver.gameOverWithRevive(snake.getScore());
		else
			GameOver.gameOver(snake.getScore());
	}

	/**
	 * It is called, when the game is ended abruptly without completing.
	 */
	public void endGame2() {
		System.out.println("Gello 2");
		stage.close();
		MainPage.displayMainPage();
	}

	/**
	 * It is used to get the path at which the explosion gif is saved with which a
	 * block explodes.
	 * 
	 * @return blockExplosionPath, which stores the path at which the image with
	 *         which the blocks explode is saved.
	 */
	public String getBlockExplosionPath() {
		return blockExplosionPath;
	}

	/**
	 * It is used to get the path at which explosion gif is saved with which any
	 * token other than block explodes.
	 * 
	 * @return tokenExplosionPath, which stores the path at which the image of
	 *         explosion is saved.
	 */
	public String getTokenExplosionPath() {
		return tokenExplosionPath;
	}

	/**
	 * It is used to return the speed with which the snake moves left or right.
	 * 
	 * @return snake_left_right_speed, which stores the speed with which snake moves
	 *         left or right.
	 */
	public int getSnake_left_right_speed() {
		return snake_left_right_speed;
	}

	/**
	 * It is used to get the dimensions of the ball image.
	 * 
	 * @return BALL_SIZE, which stores the dimensions of the ball image.
	 */
	public int getBallSize() {
		return this.BALL_SIZE;
	}
	
	public static void reviveSnake() throws Exception {
		Game gameCont = deserialize();
		gameCont.snake.activateDestroyAllBlocks();
		gameCont.snake.addBalls(10);
		gameCont.snake.setCoins(0);
		(gameCont).start(new Stage());
	}
}