import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This is the ball power up class that implements Tokenizable and extends Ball
 * class. This is also serializable. This power up adds len number of balls to
 * the snake with which it collides.
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class BallPowerUp extends Ball implements Tokenizable, Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * The len represents the number of balls that this power up adds to the snake
	 */
	int len;

	/**
	 * It calls super class constructor with screenCoordinates and assign len to len
	 * 
	 * @param screenCoordinates is an array of upper and lower bounds of the screen
	 *                          in XY plane. It is taken so that objects can know
	 *                          the boundaries of screen.
	 * @param len               is the number of balls this power up will add to the
	 *                          snake's length
	 */
	public BallPowerUp(int[] screenCoordinates, int len) {
		super(screenCoordinates);
		this.len = len;
	}

	/**
	 * This overrides the update method of Ball class because this power up doesn't
	 * move left right but in downward direction.
	 */
	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}

	/**
	 * This overrides the render method of Sprite class as along with the image of
	 * sprite, we also want to display its length.
	 */
	@Override
	public void render(GraphicsContext gc) {
		super.render(gc);
		gc.setStroke(Color.WHITE);
		gc.strokeText(Integer.toString(len), positionX + width / 2.8, positionY);
	}

	/**
	 * This implements the action method of Tokenizable interface which calls
	 * snake.addBalls(len) method to increase its length.
	 */
	public void action(Snake snake) {
		snake.addBalls(len);
	}
}
