import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 
 * @author SHUBHAM _THAKRAL, TANMAY BANSAL This class is the animation class. It
 *         is a subclass of AnimationTimer. It is used to render and update the
 *         game scene at regular intervals. It override the handle method on
 *         AnimationTimer.
 */
public class Animation extends AnimationTimer implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * It has various attributes which are private so that they can't be accessed by
	 * other classes, except Animation class (Encapsulation - data hiding). The game
	 * attribute of Game type is used because we want to access the attributes and
	 * methods of Game class, which we can't access directly since they are private.
	 * It is therefore used to render blocks, power ups, walls etc. while playing.
	 * The lastNanoTime stores the system time of the last frame generated. The
	 * currentNanoTime stores the current system time. The repeat variable stores
	 * the number of independent block layers that should be displayed after which
	 * the complete chain of blocks will be displayed. The delay variable stores the
	 * time difference between the current system time and the last time game was
	 * paused. The flag variable variable is used to know whether the game was
	 * paused any time during the game play or not. If it was paused then the flag
	 * will become 1 and we have to subtract delay time for every operation
	 * thereafter. The last variable stores the time elapsed after the last layer of
	 * blocks were displayed. The rnd attribute of Random type is used to generate
	 * random numbers. It is used to find the value to be placed on blocks, balls,
	 * etc. The addElements is an array list of type Sprite which is used to
	 * remove/add elements from screen while we are already iterating a collection.
	 * Otherwise we get concurrentModificationException, if we try to add and remove
	 * elements from a collection while already iterating a collection.
	 */

	Game game;
	private long lastNanoTime;
	private long currentNanoTime;
	private int repeat;
	private long delay;
	private int flag;
	private double last;
	private int[] layer;
	private ArrayList<Sprite> addElements = new ArrayList<Sprite>();
	private static transient Random rnd = new Random();

	/**
	 * In the constructor, we initialize the repeat variable to a random value. Also
	 * the last, layer, lastNanoTime, delay and flag are all initialized to the
	 * correct value in the start.
	 * 
	 * @param g is an the attribute of Game class, which will initialize the game
	 *          attribute of our class.
	 */
	public Animation(Game g) {
		repeat = 3 + rnd.nextInt(2);
		layer = new int[5];
		for (int i = 0; i < 5; i++)
			layer[i] = 1;
		last = 0;
		game = g;
		lastNanoTime = System.nanoTime();
		delay = 0L;
		flag = 0;
	}

	/**
	 * This method is used to add the power ups on the game screen using the game
	 * attribute. This method uses the Monte Carlo technique to reduce the frequency
	 * of power ups as compared to balls. It is made private so that it cannot be
	 * called by another class except the Animation class itself.
	 * 
	 * @param powerUpNo stores which power up to display. Since all power Ups are
	 *                  assigned a unique number, hence depending upon the value of
	 *                  powerUpNo, that power up is added.
	 * @param x_dist    is the x coordinate or the position, where the power up
	 *                  should be added.
	 */
	private void displayPowerUp(int powerUpNo, int x_dist) {
		if (powerUpNo == 0) {
			game.getAllElements()
					.add(game.makeShield(game.getBLOCK_SIZE() * x_dist + x_dist + game.getBLOCK_SIZE() / 2 - 12,
							-360 + game.getBLOCK_SIZE() / 2 - 10));
		} else if (powerUpNo >= 1 && powerUpNo <= 7) {
			game.getAllElements()
					.add(game.makeCoin(game.getBLOCK_SIZE() * x_dist + x_dist + game.getBLOCK_SIZE() / 2 - 12,
							-360 + game.getBLOCK_SIZE() / 2 - 10));
		} else if (powerUpNo == 8) {
			game.getAllElements()
					.add(game.makeMagnet(game.getBLOCK_SIZE() * x_dist + x_dist + game.getBLOCK_SIZE() / 2 - 12,
							-360 + game.getBLOCK_SIZE() / 2 - 10));
		} else if (powerUpNo == 9) {
			game.getAllElements()
					.add(game.makeDestroyBlocksPowerUp(
							game.getBLOCK_SIZE() * x_dist + x_dist + game.getBLOCK_SIZE() / 2 - 12,
							-360 + game.getBLOCK_SIZE() / 2 - 10));
		} else if (powerUpNo == 10) {
			game.getAllElements()
					.add(game.makeDobleScorePowerUp(
							game.getBLOCK_SIZE() * x_dist + x_dist + game.getBLOCK_SIZE() / 2 - 12,
							-360 + game.getBLOCK_SIZE() / 2 - 10));
		}
	}

	/**
	 * This method decides, how many walls to display in one row and adds them on
	 * the game screen. It is made private so that it cannot be called by another
	 * class except the Animation class itself.
	 */
	private void genWalls() {
		int how_many = rnd.nextInt(3);
		if (how_many == 1) {
			int l1 = rnd.nextInt(4);
			// System.err.println(l1+" "+layer[l1]);
			if (layer[l1] == 1)
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE() / 2, 50 + rnd.nextInt(40), 0));
			else
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE() / 2, 180 + rnd.nextInt(91), 0));
		} else if (how_many == 2) {
			int l1 = rnd.nextInt(4);
			int l2 = rnd.nextInt(4);
			while (l1 == l2)
				l2 = rnd.nextInt(4);
			// System.err.println(l1+" "+layer[l1]+" "+l2+" "+layer[l2]);

			if (layer[l1] == 1)
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE() / 2, 50 + rnd.nextInt(40), 0));
			else
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE() / 2, 180 + rnd.nextInt(91), 0));

			if (layer[l2] == 1)
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l2 + l2 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE() / 2, 50 + rnd.nextInt(40), 0));
			else
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l2 + l2 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE() / 2, 180 + rnd.nextInt(91), 0));
		}
	}

	/**
	 * This method generates the chain of blocks, i.e. the complete row is filled
	 * with blocks, making sure that at least 2 blocks are having value less than
	 * snake's current length. It is made private so that it cannot be called by
	 * another class except the Animation class itself.
	 */
	private void genChainOfBlocks() {
		for (int i = 0; i < 5; i++) {
			layer[i] = 1;
		}

		int ch1, ch2;
		ch1 = rnd.nextInt(5);
		ch2 = rnd.nextInt(5);
		while (ch1 == ch2) {
			ch2 = rnd.nextInt(5);
		}

		for (int i = 0; i < 5; i++) {
			if (i != ch1 && i != ch2)
				game.getAllElements()
						.add(game.makeBlock(0 + (game.getBLOCK_SIZE()) * i + (i + 1), -360, 1 + rnd.nextInt(50)));
		}
		if (game.getSnake().getLength() > 1) {
			game.getAllElements().add(game.makeBlock(0 + (game.getBLOCK_SIZE()) * ch1 + (ch1 + 1), -360,
					1 + rnd.nextInt(Math.min(50, game.getSnake().getLength() - 1))));
			game.getAllElements().add(game.makeBlock(0 + (game.getBLOCK_SIZE()) * ch2 + (ch2 + 1), -360,
					1 + rnd.nextInt(Math.min(50, game.getSnake().getLength() - 1))));
		}
		repeat = 3 + rnd.nextInt(2);
		last = 0;
	}

	/**
	 * This method generates a single independent block in the row at a random x
	 * coordinate. It is made private so that it cannot be called by another class
	 * except the Animation class itself.
	 * 
	 * @param object_exist is an array list of integers, which stores all the
	 *                     possible location of a row at which some object exists.
	 *                     (possible locations are just 5, since at max 5 blocks can
	 *                     be displayed in 1 row)
	 */
	private void genSingleBlock(ArrayList<Integer> object_exist) {
		int l1 = rnd.nextInt(5);
		int ball_count = rnd.nextInt(3);

		object_exist.add(l1);
		layer[l1] = 1;
		for (int i = 0; i < 5; i++) {
			if (i != l1)
				layer[i] = 0;
		}
		game.getAllElements()
				.add(game.makeBlock(0 + (game.getBLOCK_SIZE()) * l1 + (l1 + 1), -360, 1 + rnd.nextInt(50)));

		genBalls(ball_count, object_exist);
	}

	/**
	 * This function generates balls on the game screen at random x coordinates. It
	 * is made private so that it cannot be called by another class except the
	 * Animation class itself.
	 * 
	 * @param ball_count   stores the number of balls that need to be generated in
	 *                     the row.
	 * @param object_exist stores the x coordinates at which already some object
	 *                     exist and hence ball can't be generated at that
	 *                     coordinate.
	 */
	private void genBalls(int ball_count, ArrayList<Integer> object_exist) {
		while (ball_count > 0) {
			int x_loc = rnd.nextInt(5);
			if (!object_exist.contains(x_loc)) {
				object_exist.add(x_loc);
				game.getAllElements()
						.add(game.makeBallPowerUp(game.getBLOCK_SIZE() * x_loc + x_loc + game.getBLOCK_SIZE() / 2 - 12,
								-360 + game.getBLOCK_SIZE() / 2 - 10, 1 + rnd.nextInt(5)));
				ball_count -= 1;
			}
		}
	}

	/**
	 * This function is used to generate 2 independent blocks on the game screen in
	 * one row. It is made private so that it cannot be called by another class
	 * except the Animation class itself.
	 * 
	 * @param object_exist is an array list of integers, which stores all the
	 *                     possible location of a row at which some object exists.
	 *                     (possible locations are just 5, since at max 5 blocks can
	 *                     be displayed in 1 row)
	 */
	private void gen2Blocks(ArrayList<Integer> object_exist) {
		int ball_count = rnd.nextInt(3);

		int l1 = rnd.nextInt(5);
		int l2 = rnd.nextInt(5);
		while (l1 == l2) {
			l2 = rnd.nextInt(5);
		}
		object_exist.add(l1);
		object_exist.add(l2);
		layer[l1] = 1;
		layer[l2] = 1;
		for (int i = 0; i < 5; i++) {
			if (i != l1 && i != l2)
				layer[i] = 0;
		}

		game.getAllElements()
				.add(game.makeBlock(0 + (game.getBLOCK_SIZE()) * l1 + (l1 + 1), -360, 1 + rnd.nextInt(50)));
		game.getAllElements()
				.add(game.makeBlock(0 + (game.getBLOCK_SIZE()) * l2 + (l2 + 1), -360, 1 + rnd.nextInt(50)));

		genBalls(ball_count, object_exist);
	}

	/**
	 * This function decides which power up to generate and how many should be
	 * generated in a row. It then calls the displayPowerUp function to display them
	 * on the screen. It is made private so that it cannot be called by another
	 * class except the Animation class itself.
	 * 
	 * @param object_exist stores the x coordinates at which already some object
	 *                     exist and hence powerUp can't be generated at that
	 *                     coordinate.
	 */
	private void generatePowerUps(ArrayList<Integer> object_exist) {
		int numOfPowerUps = 1 + rnd.nextInt(2);
		int x_dist = rnd.nextInt(5);
		while (object_exist.contains(x_dist)) {
			x_dist = rnd.nextInt(5);
		}
		object_exist.add(x_dist);

		int which_powerUp = rnd.nextInt(20);
		displayPowerUp(which_powerUp, x_dist);

		if (which_powerUp >= 1 && which_powerUp <= 7) {
			which_powerUp = 1;
		}

		if (numOfPowerUps == 2) {

			if (object_exist.size() < 5) {
				while (object_exist.contains(x_dist)) {
					x_dist = rnd.nextInt(5);
				}
				int secondPowerUp = rnd.nextInt(20);
				if (secondPowerUp >= 1 && secondPowerUp <= 7) {
					secondPowerUp = 1;
				}
				while (secondPowerUp == which_powerUp) {
					secondPowerUp = rnd.nextInt(20);
					if (secondPowerUp >= 1 && secondPowerUp <= 7) {
						secondPowerUp = 1;
					}
				}
				displayPowerUp(secondPowerUp, x_dist);
			}
		}
	}

	/**
	 * It is used to randomize every element of the game screen. It call the
	 * functions genWalls(), genChainOfBlocks(), genSingleBlock(), gen2Blocks() and
	 * generatePowerUps internally to display them on game screen. It is made
	 * private so that it cannot be called by another class except the Animation
	 * class itself.
	 */
	private void randomizeScreen() {
		genWalls();

		if (repeat == 0) {
			genChainOfBlocks();
		} else {
			int how_many = 1 + rnd.nextInt(2);
			ArrayList<Integer> object_exist = new ArrayList<Integer>(5);

			if (how_many == 1) {
				genSingleBlock(object_exist);
			} else if (how_many == 2) {
				gen2Blocks(object_exist);
			}

			last = 0;
			repeat -= 1;

			if (object_exist.size() < 5 && rnd.nextInt(2) == 1)
				generatePowerUps(object_exist);
		}
	}

	/**
	 * This method overrides the handle method of AnimationTimerClass. It is going
	 * to be called in every frame while the animation timer is active. This defines
	 * whole functionality of the game such as rendering, collision, updation, etc.
	 */
	@Override
	public void handle(long currentNanoTime) {
		game.addSpeed((int)Math.pow(game.getSnake().getLength(),1.5));
		game.setSpeedToDefault();
		this.currentNanoTime = currentNanoTime;
		double elapsedTime;
		if (flag == 1) {
			delay = currentNanoTime - lastNanoTime;
			flag = 0;
			elapsedTime = (currentNanoTime - lastNanoTime - delay) / 1000000000.0;
		} else {
			elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
		}
		last += elapsedTime;

		if ((last * game.getSpeed()) >= 300) {
			randomizeScreen();
		}

		lastNanoTime = currentNanoTime;

		addElements();

		Iterator<Sprite> allElementsIter = game.getAllElements().iterator();
		while (allElementsIter.hasNext()) {
			Sprite element = allElementsIter.next();
			if (element.outOfFrame()) {
				allElementsIter.remove();
			} else if (element.intersects(game.getSnake())) {
				if (element instanceof Block) {
					this.onBlockAction((Block) element, allElementsIter);
				} else if (element instanceof Tokenizable) {
					((Tokenizable) element).action(game.getSnake());
					addElements.add(Explosion.getExplosionObject(element, game.getTokenExplosionPath(),
							game.getScreenCoordinates(), 1.2));
					Media sound = new Media(new File("sounds/token_collected.mp3").toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(sound);
					mediaPlayer.play();
					allElementsIter.remove();
				}
			} else if (element instanceof Block || element instanceof Wall) {
				element.forceSpriteCorrectly(game.getSnake());
			} else if (element instanceof Coin) {
				this.attractCoinIfMagnetActive((Coin) element);
			} else if (element instanceof Explosion) {
				if (((Explosion) element).isDestroyable(elapsedTime)) {
					allElementsIter.remove();
				}
			}
		}
		if (game.getSnake().consumeDestroyAllBlocks()) {
			Iterator<Sprite> allElementsIter2 = game.getAllElements().iterator();
			while (allElementsIter2.hasNext()) {
				Sprite element = allElementsIter2.next();
				if (element instanceof Block) {
					game.getSnake().incrementScore(((Block) element).getNumber());
					addElements.add(Explosion.getExplosionObject(element, game.getBlockExplosionPath(),
							game.getScreenCoordinates(), 0.89));
					allElementsIter2.remove();
				}
			}
			Media sound = new Media(new File("sounds/block_exploded.mp3").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
		}

		game.getGc().clearRect(0, 0, game.getScreenCoordinates()[1], game.getScreenCoordinates()[3]);
		game.getSnake().render(game.getGc());

		for (Sprite element : game.getAllElements()) {
			if (!(element instanceof Coin))
				element.setVelocity(0, game.getSpeed());
			element.update(elapsedTime);
			element.render(game.getGc());
		}

		game.getSnake().setVelocity(0, 0);
		if (game.getSnakeToBeShiftedTo() > game.getSnake().positionX + 15) {
			game.getSnake().setVelocity(game.getSnake_left_right_speed(), 0);
		} else if (game.getSnakeToBeShiftedTo() < game.getSnake().positionX - 15) {
			game.getSnake().setVelocity(-1 * game.getSnake_left_right_speed(), 0);
		}
		game.getSnake().update(elapsedTime);

		game.getGc().setStroke(Color.WHITE);
		String pointsText = "Score: " + (game.getSnake().getScore());
		game.getGc().strokeText(pointsText, game.getScreenCoordinates()[1] - 70, game.getScreenCoordinates()[2] + 30);
		String coinText = "Coins : " + (game.getSnake().getCoins());
		game.getGc().strokeText(coinText, game.getScreenCoordinates()[0] + 10, game.getScreenCoordinates()[2] + 30);

		Font f = game.getGc().getFont();
		game.getGc().setFont(new Font("System Regular", 15));
		if (game.getSnake().isMagnetActive()) {
			displayMagnet();
		}
		if (game.getSnake().isShieldActive()) {
			displayShield();
		}
		if (game.getSnake().isDoubleScoreActive()) {
			displayDoubleScore();
		}
		game.getGc().setFont(f);
	}

	/**
	 * This method displays the remaining time for which the double score power up
	 * is active. It displays the double score power up image on the top of the
	 * screen as well. It is made private so that it cannot be called by another
	 * class except the Animation class itself.
	 */
	private void displayDoubleScore() {
		Shield s = new Shield(game.getScreenCoordinates());
		s.setImage(new Image("file:images/double_score.png", game.getBallSize() * 1.5, game.getBallSize() * 1.5, false,
				false));
		s.setImagePath("file:images/shield.png");
		s.setPosition(game.getScreenCoordinates()[0] + 175 + game.getBallSize() / 2 + 10,
				game.getScreenCoordinates()[2] + 20);
		s.setVelocity(0, 0);
		s.render(game.getGc());
		String doubleScoreText = String.valueOf(game.getSnake().getDoubleScoreTime());
		game.getGc().strokeText(doubleScoreText, game.getScreenCoordinates()[0] + 185 + 10 + game.getBallSize() / 2,
				game.getScreenCoordinates()[2] + 15);
	}

	/**
	 * This method displays the remaining time for which the shield power up is
	 * active. It displays the shield image on the top of the screen as well. It is
	 * made private so that it cannot be called by another class except the
	 * Animation class itself.
	 */
	private void displayShield() {
		Shield s = new Shield(game.getScreenCoordinates());
		s.setImage(
				new Image("file:images/shield.png", game.getBallSize() * 1.5, game.getBallSize() * 1.5, false, false));
		s.setImagePath("file:images/shield.png");
		s.setPosition(game.getScreenCoordinates()[0] + 250 + game.getBallSize() + 10,
				game.getScreenCoordinates()[2] + 20);
		s.setVelocity(0, 0);
		s.render(game.getGc());
		String shieldText = String.valueOf(game.getSnake().getShieldTime());
		game.getGc().strokeText(shieldText, game.getScreenCoordinates()[0] + 260 + 10 + game.getBallSize(),
				game.getScreenCoordinates()[2] + 15);
	}

	/**
	 * This method displays the remaining time for which the display magnet power up
	 * is active. It also displays the magnet image on the top of the screen as
	 * well. It is made private so that it cannot be called by another class except
	 * the Animation class itself.
	 */
	private void displayMagnet() {
		Magnet m = new Magnet(game.getScreenCoordinates());
		m.setImage(
				new Image("file:images/magnet.jpg", game.getBallSize() * 1.5, game.getBallSize() * 1.5, false, false));
		m.setImagePath("file:images/magnet_for_timer.gif");
		m.setPosition(game.getScreenCoordinates()[0] + 100, game.getScreenCoordinates()[2] + 20);
		m.setVelocity(0, 0);
		m.render(game.getGc());
		String magnetText = String.valueOf(game.getSnake().getMagnetTime());
		game.getGc().strokeText(magnetText, game.getScreenCoordinates()[0] + 110, game.getScreenCoordinates()[2] + 15);
	}

	/**
	 * It adds the elements to be added to the allElements list in addElementsList.
	 */
	private void addElements() {
		Iterator<Sprite> addElementsIter = addElements.iterator();
		while (addElementsIter.hasNext()) {
			Sprite element = addElementsIter.next();
			game.getAllElements().add(element);
			addElementsIter.remove();
		}
	}

	/**
	 * It is called when the snake collides with the block. It destroys the block
	 * and decreases the length of the snake depending upon various conditions.
	 * 
	 * @param temp            is the Block object which collides with the snake.
	 * @param allElementsIter is the iterator on allElements list.
	 */
	private void onBlockAction(Block temp, Iterator<Sprite> allElementsIter) {
		if (game.getSnake().isShieldActive()) {
			game.getSnake().incrementScore(temp.getNumber());
			temp.destroy();
			Media sound = new Media(new File("sounds/block_exploded.mp3").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
			allElementsIter.remove();
			addElements.add(Explosion.getExplosionObject(temp, game.getBlockExplosionPath(),
					game.getScreenCoordinates(), 0.89));
		} else {
			try {
				if (temp.getNumber() <= 5 && game.getSnake().getLength() > temp.getNumber()) {
					allElementsIter.remove();
					Media sound = new Media(new File("sounds/block_exploded.mp3").toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(sound);
					mediaPlayer.play();
					addElements.add(Explosion.getExplosionObject(temp, game.getBlockExplosionPath(),
							game.getScreenCoordinates(), 0.89));
					for (int i = 0; i < temp.getNumber(); i++) {
						game.getSnake().decreaseLength();
					}
					// game.getSnake().decreaseLength();
					game.getSnake().incrementScore(temp.getNumber());
					game.setSpeedToDefault();
				} else if (temp.getNumber() <= 5) {
					throw new SnakeLengthZeroException("Game Over");
				} else if (temp.canBeDestroyed(game.getSnake())) {
					allElementsIter.remove();
					Media sound = new Media(new File("sounds/block_exploded.mp3").toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(sound);
					mediaPlayer.play();
					addElements.add(Explosion.getExplosionObject(temp, game.getBlockExplosionPath(),
							game.getScreenCoordinates(), 0.89));
					game.getSnake().incrementScore(1);
					game.setSpeedToDefault();
				} else {
					Media sound = new Media(new File("sounds/length_decreased.mp3").toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(sound);
					mediaPlayer.play();
					game.getSnake().incrementScore(1);
					temp.collide(game.getSnake());
					game.setSpeed(0);
				}
			} catch (SnakeLengthZeroException s) {
				this.stop();
				deleteResumeFileIfExists();
				game.endGame();
			}
		}
	}

	/**
	 * This method deletes the resume file, which stores the state of game object
	 * when the game was abruptly closed by the player.
	 */
	private void deleteResumeFileIfExists() {
		try {
			Files.deleteIfExists(Paths.get("resume.txt"));
		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty.");
		} catch (IOException e) {
			System.out.println("Invalid permissions.");
		}
	}

	/**
	 * This methods simulates the movement of coin towards the snake, if the magnet
	 * power up is active.
	 * 
	 * @param coin contains the reference of the coin which needs to be attracted
	 *             towards the snake.
	 */
	private void attractCoinIfMagnetActive(Coin coin) {
		if (game.getSnake().magnetIntersects(coin)) {
			double disX = game.getSnake().positionX - coin.positionX;
			double disY = game.getSnake().positionY - coin.positionY;
			coin.setVelocity(0.9 * Math.signum(disX) * game.getSpeed(), 1.5 * Math.signum(disY) * game.getSpeed());
		} else {
			coin.setVelocity(0, game.getSpeed());
		}
	}

	/**
	 * This function return the current system time, recorded and saved in the
	 * currentNanoTime variable of the class.
	 * 
	 * @return the current system time, recorded and saved in the currentNanoTime
	 *         variable of the class.
	 */
	public long getCurrentNanoTime() {
		return currentNanoTime;
	}

	/**
	 * This method is used by other classes, mainly Game class, to set the value of
	 * the last system recorded time of the last frame.
	 * 
	 * @param last_nano_time
	 */
	public void setLastNanoTime(long last_nano_time) {
		lastNanoTime = last_nano_time;
	}

	/**
	 * This method is used by other classes, mainly Game class, to set the value of
	 * the flag variable, if the game is paused any time during the game play.
	 * 
	 * @param flag
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
