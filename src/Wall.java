import java.io.Serializable;

/**
 * This class represent a Wall. It is a child of Sprite class and is
 * Serializable. A snake can never pass through a wall or cross it.
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class Wall extends Sprite implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * In the constructor, we take screenCoordinates and pass it to the super class
	 * constructor.
	 * 
	 * @param screenCoordinates is an array of upper and lower bounds of the screen
	 *                          in XY plane. It is taken so that objects can know
	 *                          the boundaries of screen.
	 */
	public Wall(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	/**
	 * This method defines the abstract method of Sprite class so that walls can
	 * only move in Y direction.
	 */
	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}

	/**
	 * This method overrides the intersects method of Sprite class so that it
	 * returns false. This is done because for a wall, other type of intersections
	 * matter like bottom, left and right.
	 */
	@Override
	public boolean intersects(Sprite s) {
		return false;
	}
}
