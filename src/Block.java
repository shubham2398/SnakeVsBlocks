import java.io.Serializable;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * This is the class that represents a block. It is a child of Sprite and is
 * serializable. Snake cannot pass through a block unless it makes the value of
 * that block to zero.
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class Block extends Sprite implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * number is the number on the block that is required to break it.
	 */
	private int number;

	/**
	 * In the constructor, we take screenCoordinates and pass it to the super class
	 * constructor.
	 * 
	 * @param screenCoordinates is an array of upper and lower bounds of the screen
	 *                          in XY plane. It is taken so that objects can know
	 *                          the boundaries of screen.
	 * @param number            is the number that needs to set to the block
	 */
	public Block(int[] screenCoordinates, int number) {
		super(screenCoordinates);
		this.number = number;
	}

	/**
	 * This method defines the abstract method update of Sprite class. It allows its
	 * movement only in Y direction.
	 */
	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}

	/**
	 * This method overrides the render method because we also want to display the
	 * number on the block along with its image.
	 */
	@Override
	public void render(GraphicsContext gc) { // System.out.println("in render"+number);
		super.render(gc);
		Font f = gc.getFont();
		TextAlignment t = gc.getTextAlign();
		VPos v = gc.getTextBaseline();
		gc.setFont(new Font("System Regular", 30));
		gc.setStroke(Color.BLACK);
		gc.setFill(Color.BLACK);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.strokeText(Integer.toString(number), positionX + width / 2, positionY + height / 2);
		gc.fillText(Integer.toString(number), positionX + width / 2, positionY + height / 2);
		gc.setFont(f);
		gc.setTextAlign(t);
		gc.setTextBaseline(v);
	}

	/**
	 * This method overrides the toString method of Sprite class
	 */
	@Override
	public String toString() {
		return "Block Object\n" + super.toString();
	}

	/**
	 * This method overrides the intersects method of block and changes it so that
	 * we check the tophalf boundary of s with the bottom boundary of the block
	 */
	@Override
	public boolean intersects(Sprite s) {
		return s.getTopHalfBoundary().intersects(this.getBottomBoundary());
	}

	/**
	 * This method collides the block and snake and decreases the length of the
	 * snake
	 * 
	 * @param snake is the snake object with which block has collided
	 * @throws SnakeLengthZeroException when snake dies while decreasing its length
	 */
	public void collide(Snake snake) throws SnakeLengthZeroException {
		snake.decreaseLength();
	}

	/**
	 * This method checks if the block can be destroyed. This only check whether the
	 * number on block is 1 and it can be destroyed in one iteration. This method
	 * also decreases the length of the snake if the block can be destroyed by it.
	 * 
	 * @param snake is the snake object with which block has collided
	 * @return true if block can be destroyed, else false.
	 * @throws SnakeLengthZeroException if snake dies while decreasing its length
	 */
	public boolean canBeDestroyed(Snake snake) throws SnakeLengthZeroException {
		if (number <= 1) {
			snake.decreaseLength();
			return true;
		} else {
			number--;
			return false;
		}
	}

	/**
	 * This method instantly destroys the block by making it's number = 0;
	 */
	public void destroy() {
		number = 0;
	}

	/**
	 * This method returns the number on block
	 * 
	 * @return number on block
	 */
	public int getNumber() {
		return number;
	}
}
