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

public class Animation extends AnimationTimer implements Serializable {
	Game game;
	public LongValue lastNanoTime;
	public int repeat;
	public LongValue delay;
	public IntValue flag;
	private double last;
	public long currentNanoTime;
	private int[] layer;
	private ArrayList<Sprite> addElements = new ArrayList<Sprite>();
	private transient Random rnd;

	public Animation(Game g) {
		rnd = new Random();
		repeat = 3 + rnd.nextInt(2);
		layer = new int[5];
		for (int i = 0; i < 5; i++)
			layer[i] = 1;
		last = 0;
		game = g;
		lastNanoTime = new LongValue(System.nanoTime());
		delay = new LongValue(0);
		flag = new IntValue(0);
	}

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
		}
	}

	private void genWalls() {
		int how_many = rnd.nextInt(3);
		if (how_many == 1) {
			int l1 = rnd.nextInt(4);
			// System.err.println(l1+" "+layer[l1]);
			if (layer[l1] == 1)
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 50 + rnd.nextInt(40), 0));
			else
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 180 + rnd.nextInt(91), 0));
		} else if (how_many == 2) {
			int l1 = rnd.nextInt(4);
			int l2 = rnd.nextInt(4);
			while (l1 == l2)
				l2 = rnd.nextInt(4);
			// System.err.println(l1+" "+layer[l1]+" "+l2+" "+layer[l2]);

			if (layer[l1] == 1)
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 50 + rnd.nextInt(40), 0));
			else
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 180 + rnd.nextInt(91), 0));

			if (layer[l2] == 1)
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l2 + l2 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 50 + rnd.nextInt(40), 0));
			else
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l2 + l2 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 180 + rnd.nextInt(91), 0));
		}
	}

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

	private void genSingleBlock(ArrayList<Integer> object_exist) {
		int l1 = rnd.nextInt(5);
		int ball_count = rnd.nextInt(4);

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

	private void generatePowerUps(ArrayList<Integer> object_exist) {
		int numOfPowerUps = 1 + rnd.nextInt(2);
		int x_dist = rnd.nextInt(5);
		while (object_exist.contains(x_dist)) {
			x_dist = rnd.nextInt(5);
		}
		object_exist.add(x_dist);

		int which_powerUp = rnd.nextInt(20);
		displayPowerUp(which_powerUp, x_dist);
		
		if(which_powerUp>=1 && which_powerUp<=7) {
			which_powerUp = 1;
		}

		if (numOfPowerUps == 2) {

			if (object_exist.size() < 5) {
				while (object_exist.contains(x_dist)) {
					x_dist = rnd.nextInt(5);
				}
				int secondPowerUp = rnd.nextInt(20);
				while (secondPowerUp == which_powerUp) {
					secondPowerUp = rnd.nextInt(20);
				}
				displayPowerUp(secondPowerUp, x_dist);
			}
		}
	}

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

			if (object_exist.size() < 5 && rnd.nextInt(2)==1)
				generatePowerUps(object_exist);
		}
	}

	@Override
	public void handle(long currentNanoTime) {
		game.addSpeed(2 * game.getSnake().getScore());
		game.setSpeedToDefault();
		this.currentNanoTime = currentNanoTime;
		double elapsedTime;
		if (flag.value == 1) {
			delay.value = currentNanoTime - lastNanoTime.value;
			flag.value = 0;
			elapsedTime = (currentNanoTime - lastNanoTime.value - delay.value) / 1000000000.0;
		} else {
			elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
		}
		last += elapsedTime;

		if ((last * game.getSpeed()) >= 300) {
			randomizeScreen();
		}

		lastNanoTime.value = currentNanoTime;

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
		game.getGc().setFont(f);
	}

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

	private void addElements() {
		Iterator<Sprite> addElementsIter = addElements.iterator();
		while (addElementsIter.hasNext()) {
			Sprite element = addElementsIter.next();
			game.getAllElements().add(element);
			addElementsIter.remove();
		}
	}

	private void onBlockAction(Block temp, Iterator<Sprite> allElementsIter) {
		if (game.getSnake().isShieldActive()) {
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
					//game.getSnake().decreaseLength();
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

	private void attractCoinIfMagnetActive(Coin coin) {
		if (game.getSnake().magnetIntersects(coin)) {
			double disX = game.getSnake().positionX - coin.positionX;
			double disY = game.getSnake().positionY - coin.positionY;
			coin.setVelocity(0.9 * Math.signum(disX) * game.getSpeed(), 1.5 * Math.signum(disY) * game.getSpeed());
		} else {
			coin.setVelocity(0, game.getSpeed());
		}
	}
}
