import java.io.Serializable;

/**
 * This represents the coin object. It is a child of sprite and implements Tokenizable interface. 
 * It is also serializable.
 * Snake can collect coin when they collide.
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class Coin extends Sprite implements Tokenizable, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * In the constructor, we take screenCoordinates and pass it to the super class constructor.
	 * @param screenCoordinates is an array of upper and lower bounds of the screen in XY plane. It is taken so that objects can know the boundaries of screen.
	 */
	public Coin(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	/**
	 * This defines the abstract method update of Sprite class such it is allowed to move in any direction in XY plane as it can be attracted by a magnet
	 */
	@Override
	public void update(double time) {
		positionY += velocityY * time;
		positionX += velocityX * time;
	}
	
	/**
	 * This defines the action method of Tokenizable interface so that we can add a coin to snake if snake collects coin
	 */
	@Override
	public void action(Snake snake) {
		snake.addCoin();
	}
}
