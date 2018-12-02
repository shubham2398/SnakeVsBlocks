import java.io.Serializable;

/**
 * This represents the Double Score Power Up. 
 * This is a child of Sprite and implements Tokenizable. It is also serializable.
 * This power up makes the score increase with double amount.
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *	
 */
public class DoubleScorePowerUp extends Sprite implements Tokenizable, Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * In the constructor, we take screenCoordinates and pass it to the super class constructor.
	 * @param screenCoordinates is an array of upper and lower bounds of the screen in XY plane. It is taken so that objects can know the boundaries of screen.
	 */
	public DoubleScorePowerUp(int[] screenCoordinates) {
		super(screenCoordinates);
	}
	
	/**
	 * This defines the action method of Tokenizable interface such that snake's score can increase with double rate.
	 */
	public void action(Snake s) {
		s.activateDoubleScore();
	}
	
	/**
	 * This defines the abstract update method of Sprite class such that this can only move in Y direction. 
	 */
	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}
	
}
