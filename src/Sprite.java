import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public abstract class Sprite {
	private Image image;
	protected double positionX;
	protected double positionY;
	protected double velocityX;
	protected double velocityY;
	protected double width;
	protected double height;
	protected int[] screenCoordinates;

	public Sprite(int[] screenCoordinates) {
		positionX = 0;
		positionY = 0;
		velocityX = 0;
		velocityY = 0;
		this.screenCoordinates = screenCoordinates;
	}

	public void setImage(Image i) {
		image = i;
		width = i.getWidth();
		height = i.getHeight();
		// System.out.println("Widht: " + width + " Height: " + height);
	}

	public void setImage(String filename) {
		Image i = new Image(filename);
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
		s.setPositionX(positionX + width+1);
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
}
