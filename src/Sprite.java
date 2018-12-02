import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

import javafx.geometry.Rectangle2D;

/**
 * This is the class that has the basic features of all game objects. 
 * It is the super class of all other game objects.
 * It is used to render, update, identify collisions and forms the basis of all game objects properties.
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 * @version 1.0
 */
public abstract class Sprite implements Serializable{
	/**
	 * It has various attributes which are protected so that they can be accessed by subclasses. 
	 * The image attribute stores the image of type Image. Since Image type can't be serialized, hence it is made transient.
	 * The imagePath stores the image path. This is used to serialize the image.
	 * The positionX and positionY store the position of object on screen.
	 * The velocityX and velocityY store the velocity of object on screen.
	 * The width and height store the width and height of image.
	 * Screen coordinates store the upper and lower bounds of screen.
	 * The 
	 */
	private static final long serialVersionUID = 1L;
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
	 * @param screenCoordinates is an array of upper and lower bounds of the screen in XY plane. It is taken so that objects can know the boundaries of screen.
	 */
	public Sprite(int[] screenCoordinates) {
		positionX = 0;
		positionY = 0;
		velocityX = 0;
		velocityY = 0;
		this.screenCoordinates = screenCoordinates;
	}
	
	/**
	 * This method is used to set the image of the Sprite object.
	 * This method also initializes the width and height of image.
	 * @param i is the Image object that is the sprite's image to be set.
	 */
	public void setImage(Image i) {
		image = i;
		width = i.getWidth();
		height = i.getHeight();
	}
	
	/**
	 * This method takes the file path of image as an argument, makes an image of it,
	 * stores the path in imagePath and the calls the {@link setImage(Image)} method to set the image.
	 * @param filename
	 */
	public void setImage(String filename) {
		Image i = new Image(filename);
		this.imagePath=filename;
		setImage(i);
	}
	
	/**
	 * This method synchronizes the image with with the imagePath.
	 * This is generally used after deserializing.
	 */
	public void setImage() {
		Image i = new Image(this.imagePath,width,height,false,false);
		setImage(i);
	}

	public void setPosition(double x, double y) {
		positionX = x;
		positionY = y;
	}

	public void setVelocity(double x, double y) {
		velocityX = x;
		velocityY = y;
	}

	public void addVelocity(double x, double y) {
		velocityX += x;
		velocityY += y;
	}

	public abstract void update(double time);

	public void render(GraphicsContext gc) {
		gc.drawImage(image, positionX, positionY);
	}

	public Rectangle2D getBoundary() {
		return new Rectangle2D(positionX, positionY, width, height);// ,6.9*width/10,5*height/10);
	}

	public Rectangle2D getLeftBoundary() {
		return new Rectangle2D(positionX - 0.5, positionY, 1, height);
	}

	public Rectangle2D getRightBoundary() {
		return new Rectangle2D(positionX + width - 0.5, positionY, 1, height);
	}

	public Rectangle2D getBottomBoundary() {
		return new Rectangle2D(positionX, positionY + height - 0.5, width, 1);
	}

	public Rectangle2D getTopBoundary() {
		return new Rectangle2D(positionX, positionY, width, 1);
	}

	public Rectangle2D getTopHalfBoundary() {
		return new Rectangle2D(positionX, positionY, width, height / 2);
	}

	public boolean intersects(Sprite s) {
		return s.getBoundary().intersects(this.getBoundary());
	}

	public boolean leftIntersects(Sprite s) {
		return s.getBoundary().intersects(this.getLeftBoundary());
	}

	public boolean rightIntersects(Sprite s) {
		return s.getBoundary().intersects(this.getRightBoundary());
	}

	public boolean bottomIntersects(Sprite s) {
		return s.getTopBoundary().intersects(this.getBottomBoundary());
	}

	public boolean outOfFrame() {
		return positionY > screenCoordinates[3];
	}

	public String toString() {
		return " Position: [" + positionX + "," + positionY + "]" + " Velocity: [" + velocityX + "," + velocityY + "]";
	}

	public void forceSpriteOnLeft(Sprite s) {
		s.setPositionX(positionX - s.width);
	}

	public void forceSpriteOnRight(Sprite s) {
		s.setPositionX(positionX + width+2);
	}

	public void forceSpriteOnBottom(Sprite s) {
		s.setPositionY(positionY + height + 1);
	}

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

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public void setImagePath(String path) {
		imagePath = path;
	}
}
