import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

import javafx.geometry.Rectangle2D;

/**
 * This is the class that has the basic features of all game objects. It is the
 * super class of all other game objects. It is used to render, update, identify
 * collisions and forms the basis of all game objects properties.
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 * @version 1.0
 */
public abstract class Sprite implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * It has various attributes which are protected so that they can be accessed by
	 * subclasses. The image attribute stores the image of type Image. Since Image
	 * type can't be serialized, hence it is made transient. The imagePath stores
	 * the image path. This is used to serialize the image. The positionX and
	 * positionY store the position of object on screen. The velocityX and velocityY
	 * store the velocity of object on screen. The width and height store the width
	 * and height of image. Screen coordinates store the upper and lower bounds of
	 * screen. The
	 */
	protected transient Image image;
	protected String imagePath;
	protected double positionX;
	protected double positionY;
	protected double velocityX;
	protected double velocityY;
	protected double width;
	protected double height;
	protected int[] screenCoordinates;

	/**
	 * In the constructor, we initialize both positions and velocities to zero.
	 * 
	 * @param screenCoordinates is an array of upper and lower bounds of the screen
	 *                          in XY plane. It is taken so that objects can know
	 *                          the boundaries of screen.
	 */
	public Sprite(int[] screenCoordinates) {
		positionX = 0;
		positionY = 0;
		velocityX = 0;
		velocityY = 0;
		this.screenCoordinates = screenCoordinates;
	}

	/**
	 * This method is used to set the image of the Sprite object. This method also
	 * initializes the width and height of image.
	 * 
	 * @param i is the Image object that is the sprite's image to be set.
	 */
	public void setImage(Image i) {
		image = i;
		width = i.getWidth();
		height = i.getHeight();
	}

	/**
	 * This method takes the file path of image as an argument, makes an image of
	 * it, stores the path in imagePath and the calls the {@link setImage(Image)}
	 * method to set the image.
	 * 
	 * @param filename is the name of image path
	 */
	public void setImage(String filename) {
		Image i = new Image(filename);
		this.imagePath = filename;
		setImage(i);
	}

	/**
	 * This method synchronizes the image with with the imagePath. This is generally
	 * used after deserializing.
	 */
	public void setImage() {
		Image i = new Image(this.imagePath, width, height, false, false);
		setImage(i);
	}

	/**
	 * This sets the position of sprite to x and y
	 * 
	 * @param x is the x coordinate of position
	 * @param y is the y coordinate of position
	 */
	public void setPosition(double x, double y) {
		positionX = x;
		positionY = y;
	}

	/**
	 * This sets the velocity to x and y
	 * 
	 * @param x is the x coordinate of velocity
	 * @param y is the y coordinate of velocity
	 */
	public void setVelocity(double x, double y) {
		velocityX = x;
		velocityY = y;
	}

	/**
	 * This adds the velocity x and y to current velocities respectively.
	 * 
	 * @param x is the x coordinate of velocity
	 * @param y is the y coordinate of velocity
	 */
	public void addVelocity(double x, double y) {
		velocityX += x;
		velocityY += y;
	}

	/**
	 * This is an abstract method that updates the position of the sprite according
	 * to its speed in the given time. It is made abstract as each sub class will
	 * implement its own way of updation.
	 * 
	 * @param time is duration for which we want to update
	 */
	public abstract void update(double time);

	/**
	 * This is the method used to render the image of sprite on canvas.
	 * 
	 * @param gc is the GraphicContext of Canvas where we draw the image
	 */
	public void render(GraphicsContext gc) {
		gc.drawImage(image, positionX, positionY);
	}

	/**
	 * This is the method that returns the boundary of the sprite. This can be used
	 * to detect collisions.
	 * 
	 * @return Rectangle2D object which represents the boundary of image.
	 */
	public Rectangle2D getBoundary() {
		return new Rectangle2D(positionX, positionY, width, height);// ,6.9*width/10,5*height/10);
	}

	/**
	 * This method returns the left boundary of the sprite. This can be used to
	 * detect left collisions.
	 * 
	 * @return Rectangle2D object which represents the left boundary of image.
	 */
	public Rectangle2D getLeftBoundary() {
		return new Rectangle2D(positionX - 0.5, positionY, 1, height);
	}

	/**
	 * This method returns the right boundary of the sprite. This can be used to
	 * detect right collisions.
	 * 
	 * @return Rectangle2D object which represents the right boundary of image.
	 */
	public Rectangle2D getRightBoundary() {
		return new Rectangle2D(positionX + width - 0.5, positionY, 1, height);
	}

	/**
	 * This method returns the bottom boundary of the sprite. This can be used to
	 * detect collisions at bottom.
	 * 
	 * @return Rectangle2D object which represents the bottom boundary of image.
	 */
	public Rectangle2D getBottomBoundary() {
		return new Rectangle2D(positionX, positionY + height - 0.5, width, 1);
	}

	/**
	 * This method returns the top boundary of the sprite. This can be used to
	 * detect collision as top.
	 * 
	 * @return Rectangle2D object which represents the top boundary of image.
	 */
	public Rectangle2D getTopBoundary() {
		return new Rectangle2D(positionX, positionY, width, 1);
	}

	/**
	 * This method returns the boundary of the top half of sprite. This can be used
	 * to detect collision with a margin of half body.
	 * 
	 * @return Rectangle2D object which represents the top half boundary of image.
	 */
	public Rectangle2D getTopHalfBoundary() {
		return new Rectangle2D(positionX, positionY, width, height / 2);
	}

	/**
	 * This method checks whether this sprite intersects with another sprite s. To
	 * check this, it used the function intersects of Rectangle2D class and calls it
	 * on s , passing it's own boundary as an argument.
	 * 
	 * @param s is the sprite with which collision needs to be checked
	 * @return true if the sprites intersects, else returns false
	 */
	public boolean intersects(Sprite s) {
		return s.getBoundary().intersects(this.getBoundary());
	}

	/**
	 * This method checks whether this sprite's left boundary intersects with
	 * another sprite s. To check this, it used the function intersects of
	 * Rectangle2D class and calls it on s , passing it's own left boundary as an
	 * argument.
	 * 
	 * @param s is the sprite with which collision needs to be checked
	 * @return true if the sprite intersects with left boundary of this sprite ,
	 *         else returns false
	 */
	public boolean leftIntersects(Sprite s) {
		return s.getBoundary().intersects(this.getLeftBoundary());
	}

	/**
	 * This method checks whether this sprite's right boundary intersects with
	 * another sprite s. To check this, it used the function intersects of
	 * Rectangle2D class and calls it on s , passing it's own right boundary as an
	 * argument.
	 * 
	 * @param s is the sprite with which collision needs to be checked
	 * @return true if the sprite intersects with right boundary of this sprite ,
	 *         else returns false
	 */
	public boolean rightIntersects(Sprite s) {
		return s.getBoundary().intersects(this.getRightBoundary());
	}

	/**
	 * This method checks whether this sprite's bottom boundary intersects with
	 * another sprite s. To check this, it used the function intersects of
	 * Rectangle2D class and calls it on s , passing it's own bottom boundary as an
	 * argument.
	 * 
	 * @param s is the sprite with which collision needs to be checked
	 * @return true if the sprite intersects with bottom boundary of this sprite ,
	 *         else returns false
	 */
	public boolean bottomIntersects(Sprite s) {
		return s.getTopBoundary().intersects(this.getBottomBoundary());
	}

	/**
	 * This method checks whether the sprite has gone out of frame of screen by
	 * using screen coordinates.
	 * 
	 * @return true if it is out of frame, else false
	 */
	public boolean outOfFrame() {
		return positionY > screenCoordinates[3];
	}

	/**
	 * The toString implementation of Sprite class which prints it's position and
	 * velocity
	 */
	public String toString() {
		return " Position: [" + positionX + "," + positionY + "]" + " Velocity: [" + velocityX + "," + velocityY + "]";
	}

	/**
	 * This method forces the given sprite s on the left of this sprite.
	 * 
	 * @param s is the sprite which needs to be forced left
	 */
	public void forceSpriteOnLeft(Sprite s) {
		s.setPositionX(positionX - s.width);
	}

	/**
	 * This method forces the given sprite s on the right of this sprite.
	 * 
	 * @param s is the sprite which needs to be forced right
	 */
	public void forceSpriteOnRight(Sprite s) {
		s.setPositionX(positionX + width + 2);
	}

	/**
	 * This method forces the given sprite s on the bottom of this sprite.
	 * 
	 * @param s is the sprite which needs to be forced bottom
	 */
	public void forceSpriteOnBottom(Sprite s) {
		s.setPositionY(positionY + height + 1);
	}

	/**
	 * This method forces the given sprite s correctly so that if it is intersecting
	 * on left side, then it is left forced and if it is intersecting on right side,
	 * then it is right forced. This is used so that the two sprites don't overlap
	 * on each other on collision.
	 * 
	 * @param s is the sprite which needs to be forced correctly
	 */
	public void forceSpriteCorrectly(Sprite s) {
		if (this.leftIntersects(s)) {
			this.forceSpriteOnLeft(s);
		} else if (this.rightIntersects(s)) {
			this.forceSpriteOnRight(s);
		} else if (this.bottomIntersects(s)) {
			if (positionX - width > screenCoordinates[0] && positionX + width < screenCoordinates[1]) {
				if (s.positionX + s.width / 2 >= positionX + width / 2) {
					forceSpriteOnRight(s);
				} else
					forceSpriteOnLeft(s);
			} else {
				if ((positionX + width / 2) > screenCoordinates[1] / 2) {
					forceSpriteOnLeft(s);
				} else {
					forceSpriteOnRight(s);
				}
			}
		}
	}

	/**
	 * This sets the positionX of this sprite
	 * 
	 * @param positionX represents the x coordinate to be put
	 */
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	/**
	 * This sets the positionY of this sprite
	 * 
	 * @param positionX represents the y coordinate to be put
	 */
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	/**
	 * This sets the image path of this sprite
	 * 
	 * @param path represents path of image
	 */
	public void setImagePath(String path) {
		imagePath = path;
	}
}
