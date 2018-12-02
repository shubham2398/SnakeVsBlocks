import java.io.Serializable;

/**
 * This class represents a Magnet. It is a child of Sprite and implements
 * Tokenizable interface. It is also Serializable. It gives snake the ability to
 * attract coins in a given radius
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class Magnet extends Sprite implements Tokenizable, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * In the constructor, we take screenCoordinates and pass it to the super class
	 * constructor.
	 * 
	 * @param screenCoordinates is an array of upper and lower bounds of the screen
	 *                          in XY plane. It is taken so that objects can know
	 *                          the boundaries of screen.
	 */
	public Magnet(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	/**
	 * This method defines the abstract update method of Sprite class such that this
	 * can only move in Y direction
	 */
	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}

	/**
	 * This method implements the action method of Tokenizable interface such that
	 * snake's magnet is activated when they collide
	 */
	public void action(Snake snake) {
		snake.activateMagnet();
	}
}
