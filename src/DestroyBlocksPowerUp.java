import java.io.Serializable;

/**
 * This is the class representing Destroy All Blocks Power Up.
 * This is a child of Sprite class and implements Tokenizable. It is also serializable.
 * This power up destroy all the blocks on the screen when it intersects with the snake.
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 * 
 */
public class DestroyBlocksPowerUp extends Sprite implements Tokenizable, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * In the constructor, we take screenCoordinates and pass it to the super class constructor.
	 * @param screenCoordinates is an array of upper and lower bounds of the screen in XY plane. It is taken so that objects can know the boundaries of screen.
	 */
	public DestroyBlocksPowerUp(int[] screenCoordinates) {
		super(screenCoordinates);
	}
	
	/**
	 * This defines the abstract method of Sprite class such that this is allowed to only move in Y direction.
	 */
	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}
	
	/**
	 * This defines the action method of Tokenizable interface such that whenever snake intersects with it, all blocks can be destroyed.
	 */
	public void action(Snake snake) {
		snake.activateDestroyAllBlocks();
	}
}
