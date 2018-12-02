import java.io.Serializable;

/**
 * This class inherits Sprite class and is serializable. It represents a ball
 * and defines all its properties and additional attributes.
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class Ball extends Sprite implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Set of points s1, s2, s3, s4, s5 represents the last 5 x coordinate states of
	 * the ball. These points are used to implement the smooth and wavy movement of
	 * balls.
	 */
	protected double s1, s2, s3, s4;

	/**
	 * In the constructor, we take screenCoordinates and pass it to the super class
	 * constructor.
	 * 
	 * @param screenCoordinates is an array of upper and lower bounds of the screen
	 *                          in XY plane. It is taken so that objects can know
	 *                          the boundaries of screen.
	 */
	public Ball(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	/**
	 * This defines the abstract method update of Sprite class by giving
	 * functionality to it. It updates the position X such that the object never
	 * moves out of frame.
	 */
	@Override
	public void update(double time) {
		positionX += velocityX * time;
		if (positionX < screenCoordinates[0])
			positionX = 0;
		else if (positionX > screenCoordinates[1] - width)
			positionX = screenCoordinates[1] - width;
	}

	/**
	 * This overrides the toString method of Sprite class.
	 */
	@Override
	public String toString() {
		return "Ball Object\n" + super.toString();
	}

	/**
	 * This overrides the method of super class because here it needs to save
	 * previous states of X coordinate also
	 */
	@Override
	public void setPositionX(double x) {
		s4 = s3;
		s3 = s2;
		s2 = s1;
		s1 = positionX;
		positionX = x;
	}
}
