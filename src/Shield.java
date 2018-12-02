import java.io.Serializable;

/**
 * This class represents a shield object. This is a child of Sprite class and
 * implements Tokenizable. It is also serializable. This power up enables a
 * snake to pass through any block without its length being decreased.
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class Shield extends Sprite implements Tokenizable, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * In the constructor, we take screenCoordinates and pass it to the super class
	 * constructor.
	 * 
	 * @param screenCoordinates is an array of upper and lower bounds of the screen
	 *                          in XY plane. It is taken so that objects can know
	 *                          the boundaries of screen.
	 */
	public Shield(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	/**
	 * This method defines the abstract method update of Sprite class such that this
	 * can move only in Y direction.
	 */
	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}

	/**
	 * This method implements the action method of Tokenizable interface so that
	 * whenever this token and snake collide, the shield is activated for snake.
	 */
	public void action(Snake snake) {
		snake.activateShield();
	}

}
