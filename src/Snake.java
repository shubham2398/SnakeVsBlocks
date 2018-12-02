import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This represents the Snake object. It is a child of sprite class and is
 * serializable. It stores an ArrayList of balls corresponding to its length.
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class Snake extends Sprite implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * len is the length of the snake with its head included. coins are the number
	 * of coins collected by the snake. score is the score made by snake shieldTime
	 * is the time for which shield is remaining. magnetTime is the time for which
	 * magnet is remaining. doubleScoreTime is the time for which double score power
	 * up is remaining. destroyAllBlocks tells if the snake can destroy all blocks.
	 * If it is true, then snake can destroy all blocks on screen, else not. s1, s2,
	 * s3, s4, s5 represent the previous 5 states of X coordinate of snake's head
	 * ArrayList of Ball objects forms the snake.
	 */
	private int len;
	private int coins;
	private int score;
	private double shieldTime = 0;
	private double magnetTime = 0;
	private double doubleScoreTime = 0;
	private boolean destroyAllBlocks;
	private double s1, s2, s3, s4;
	private ArrayList<Ball> balls = new ArrayList<Ball>();

	/**
	 * In the constructor, we take screenCoordinates and pass it to the super class
	 * constructor. Length of snake is initialized to 6 and 6 balls are added in its
	 * arrayList. coins and score are initialized to zero. destroyAllBlocks is set
	 * to fault.
	 * 
	 * @param screenCoordinates is an array of upper and lower bounds of the screen
	 *                          in XY plane. It is taken so that objects can know
	 *                          the boundaries of screen.
	 */
	public Snake(int[] screenCoordinates) {
		super(screenCoordinates);
		len = 6;
		for (int i = 0; i < len; i++) {
			balls.add(new Ball(screenCoordinates));
		}
		coins = 0;
		score = 0;
		destroyAllBlocks = false;
	}

	/**
	 * This method overrides the setImage(Image) method of Sprite class so that
	 * image can be set for all the balls in snake
	 */
	@Override
	public void setImage(Image i) {
		super.setImage(i);
		for (Ball ball : balls) {
			ball.setImage(i);
		}
	}

	/**
	 * This method overrides the setImage(String) method of Sprite class so that
	 * image path can be set for all the balls in snake
	 */
	@Override
	public void setImage(String filename) {
		super.setImage(filename);
		for (Ball ball : balls) {
			ball.setImage(filename);
		}
	}

	/**
	 * This method overrides the setImage() method of Sprite class so that all
	 * images can be synchronized with their path for all the balls in snake
	 */
	@Override
	public void setImage() {
		super.setImage();
		for (Ball ball : balls) {
			ball.setImage();
		}
	}

	/**
	 * This method overrides the setPosition(double,double) method of Sprite class
	 * so that position can be set for all the balls in the snake.
	 */
	@Override
	public void setPosition(double x, double y) {
		super.setPosition(x, y);
		double i = 0;
		for (Ball ball : balls) {
			ball.setPosition(x, y + i);
			ball.s1 = x;
			ball.s2 = x;
			ball.s3 = x;
			ball.s4 = x;
			i = i + height;
		}
		s1 = x;
		s2 = x;
		s3 = x;
		s4 = x;
	}

	/**
	 * This method overrides the setPositonX(double) method of Sprite class so that
	 * the positionX of all balls can be set in the snake.
	 */
	@Override
	public void setPositionX(double x) {
		s4 = s3;
		s3 = s2;
		s2 = s1;
		s1 = positionX;
		double temp = s4;
		super.setPosition(x, this.positionY);
		double i = 0;
		for (Ball ball : balls) {
			ball.setPositionX(temp);
			i = i + height;
			temp = ball.s4;
		}
	}

	/**
	 * This method overrides the setPositionY(double) method of Sprite class so that
	 * the positionY of all balls can be set in the snake.
	 */
	@Override
	public void setPositionY(double y) {
		super.setPosition(this.positionX, y);
		double i = y;
		for (Ball ball : balls) {
			ball.setPositionY(i);
			i = i + height;
		}
	}

	/**
	 * This method overrides the setVelocity(double,double) of Sprite class so that
	 * the velocities of all balls in the snake can be set.
	 */
	@Override
	public void setVelocity(double x, double y) {
		super.setVelocity(x, y);
		for (Ball ball : balls) {
			ball.setVelocity(x, y);
		}
	}

	/**
	 * This method overrides the addVelocity(double,double) of Sprite class so that
	 * the velocities of all balls in the snake can be added.
	 */
	@Override
	public void addVelocity(double x, double y) {
		super.addVelocity(x, y);
		for (Ball ball : balls) {
			ball.addVelocity(x, y);
		}
	}

	/**
	 * This method defines the abstract method update of Sprite class so that the
	 * positions of all balls are updated. To bring a smooth and wavy movement, it
	 * sets the positionX of a ball to the 4th previous state of positionX of the
	 * ball above it. This method also updates the time for shield, magnet and
	 * double score.
	 */
	@Override
	public void update(double time) {
		s4 = s3;
		s3 = s2;
		s2 = s1;
		s1 = positionX;
		positionX += velocityX * time;
		if (positionX < screenCoordinates[0])
			positionX = 0;
		else if (positionX > screenCoordinates[1] - width)
			positionX = screenCoordinates[1] - width;
		double temp = positionX;
		for (Ball ball : balls) {
			ball.setPositionX(temp);
			temp = ball.s4;
		}
		if (isShieldActive()) {
			shieldTime -= time;
		} else {
			shieldTime = 0;
		}
		if (isMagnetActive()) {
			magnetTime -= time;
		} else {
			magnetTime = 0;
		}
		if (doubleScoreTime > 0) {
			doubleScoreTime -= time;
		} else {
			doubleScoreTime = 0;
		}
	}

	/**
	 * This method overrides the render method of Sprite class so that all the
	 * images of balls are displayed along with the length of the snake on top. Note
	 * that this length displayed doesn't include the head. So if snake has 3 balls,
	 * then its length displayed will be 2 as 1 is head. But the actual len variable
	 * will have the value 3.
	 */
	@Override
	public void render(GraphicsContext gc) {
		for (Ball ball : balls) {
			ball.render(gc);
		}
		// System.out.println(positionX);
		gc.setStroke(Color.WHITE);
		gc.strokeText(Integer.toString(len - 1), positionX + width / 2.8, positionY);
	}

	/**
	 * This method override instersects of Sprite class and calls the intersect
	 * method on first ball as it is the one which will intersect.
	 */
	@Override
	public boolean intersects(Sprite s) {
		return balls.get(0).intersects(s);
	}

	/**
	 * This method overrides the toString method of Sprite class to display Snake's
	 * length also
	 */
	@Override
	public String toString() {
		return "Snake Object\nLength: " + len + " " + super.toString();
	}

	/**
	 * This method decreases length of the snake by 1 by removing one ball from the
	 * ArrayList of balls.
	 * 
	 * @throws SnakeLengthZeroException if snake has died
	 */
	public void decreaseLength() throws SnakeLengthZeroException {
		len = len - 1;
		try {
			balls.remove(len);
		} catch (IndexOutOfBoundsException e) {
			throw new SnakeLengthZeroException("Snake Died!");
		}
		if (len == 0) {
			throw new SnakeLengthZeroException("Snake Died!");
		}
	}

	/**
	 * This adds n balls to the length of the snake.
	 * 
	 * @param n is the number of balls to be added
	 */
	public void addBalls(int n) {
		double posY = len * height;
		double temp = 0;
		for (Ball ball : balls) {
			temp = ball.s4;
		}
		for (int i = 0; i < n; i++) {
			Ball new_ball = new Ball(screenCoordinates);
			new_ball.setImage(this.image);
			new_ball.setPosition(temp, positionY + posY);
			new_ball.s4 = temp;
			new_ball.s3 = temp;
			new_ball.s2 = temp;
			new_ball.s1 = temp;
			balls.add(new_ball);
			posY += height;
		}
		len += n;
	}

	/**
	 * This method activates the shield by setting shieldTime to 5 seconds
	 */
	public void activateShield() {
		shieldTime = 5;
	}

	/**
	 * This method checks if the shield is still active by comparing its time to
	 * zero.
	 * 
	 * @return true if the shield is active, else false
	 */
	public boolean isShieldActive() {
		if (shieldTime > 0) {
			return true;
		} else
			return false;
	}

	/**
	 * This method activates the magnet by setting magnetTime to 5 seconds
	 */
	public void activateMagnet() {
		magnetTime = 5;
	}

	/**
	 * This method checks if the magnet is still active by comparing its time to
	 * zero.
	 * 
	 * @return true if the magnet is active, else false
	 */
	public boolean isMagnetActive() {
		if (magnetTime > 0) {
			return true;
		} else
			return false;
	}

	/**
	 * This method activates the shield by setting doubleScoreTime to 5 seconds
	 */
	public void activateDoubleScore() {
		doubleScoreTime = 5;
	}

	/**
	 * This method increments the score. The score is incremented by num if
	 * doubleScorePowerUp is not active, else it is incremented by num*2
	 * 
	 * @param num is the number by which score needs to increase
	 */
	public void incrementScore(int num) {
		if (this.isDoubleScoreActive()) {
			score += num * 2;
		} else {
			score += num;
		}
	}

	/**
	 * This method is used to get the boundary for magnet attraction radius. This
	 * used isMagnetActive() method to check if magnet is even active.
	 * 
	 * @return Circle object which represents the area in which coin can be
	 *         attracted
	 */
	public Circle magnetBoundary() {
		if (isMagnetActive())
			return new Circle(positionX, positionY, 200);
		else
			return new Circle(positionX, positionY, 0);
	}

	/**
	 * This method is used to check if the coin is within the attractable region of
	 * the magnet, if the magnet is active.
	 * 
	 * @param coin is the Coin object which is being checked
	 * @return true if coin can be attracted, else false
	 */
	public boolean magnetIntersects(Coin coin) {
		double centerX = coin.positionX + coin.width;
		double centerY = coin.positionY + coin.height;
		Circle circle = magnetBoundary();
		double distance = Math
				.sqrt(Math.pow(circle.getCenterX() - centerX, 2) + Math.pow(circle.getCenterY() - centerY, 2));
		if (distance > (circle.getRadius() + coin.width / 2))
			return false;
		else
			return true;
	}

	/**
	 * This method returns the value of getter setter.
	 * 
	 * @return length of the snake
	 */
	public int getLength() {
		return len;
	}

	/**
	 * This method adds one coin to the coin variable
	 */
	public void addCoin() {
		this.coins += 1;
	}

	/**
	 * This methods returns the number of coins
	 * 
	 * @return coin variable's value
	 */
	public int getCoins() {
		return this.coins;
	}

	/**
	 * This method activates the DestroyAllBlock variable by making it true, thus
	 * allowing the snake to destroy all blocks.
	 */
	public void activateDestroyAllBlocks() {
		this.destroyAllBlocks = true;
	}

	/**
	 * This method consumes the DestroyAllBlock variable if it is active, i.e. makes
	 * it false.
	 * 
	 * @return true if destroyAllBlocks was true i.e allBlocks need to be destroyed,
	 *         else false
	 */
	public boolean consumeDestroyAllBlocks() {
		if (destroyAllBlocks) {
			destroyAllBlocks = false;
			return true;
		} else
			return false;
	}

	/**
	 * It returns the time left for which shield will still be active
	 * 
	 * @return shieldTime variable's value
	 */
	public int getShieldTime() {
		return (int) Math.ceil(shieldTime);
	}

	/**
	 * It returns the time left for which magnet will still be active
	 * 
	 * @return magnetTime variable's value
	 */
	public int getMagnetTime() {
		return (int) Math.ceil(magnetTime);
	}

	/**
	 * It returns the time left for which DoubleScorePowerUp will still be active
	 * 
	 * @return doubleScoreTime variable's value
	 */
	public int getDoubleScoreTime() {
		return (int) Math.ceil(doubleScoreTime);
	}

	/**
	 * It returns the score made by the snake
	 * 
	 * @return the value of score variable
	 */
	public int getScore() {
		return score;
	}

	/**
	 * This method checks if the DoubleScorePowerUp is still active by comparing its
	 * time to zero.
	 * 
	 * @return true if the doubleScorePowerUp is active, else false
	 */
	public boolean isDoubleScoreActive() {
		if (doubleScoreTime > 0) {
			return true;
		} else
			return false;
	}
}
