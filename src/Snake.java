import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Snake extends Sprite implements Serializable {
	private int len;
	private int coins;
	private double shieldTime = 0;
	private double magnetTime = 0;
	private boolean destroyAllBlocks ;
	private double s1,s2,s3,s4,s5;
	private ArrayList<Ball> balls = new ArrayList<Ball>(5);

	public Snake(int[] screenCoordinates) {
		super(screenCoordinates);
		len = 6;
		balls.add(new Ball(screenCoordinates));
		balls.add(new Ball(screenCoordinates));
		balls.add(new Ball(screenCoordinates));
		balls.add(new Ball(screenCoordinates));
		balls.add(new Ball(screenCoordinates));
		balls.add(new Ball(screenCoordinates));
		coins=0;
		destroyAllBlocks=false;
	}

	@Override
	protected void setImage(Image i) {
		super.setImage(i);
		for (Ball ball : balls) {
			ball.setImage(i);
		}
	}

	@Override
	public void setImage(String filename) {
		super.setImage(filename);
		for (Ball ball : balls) {
			ball.setImage(filename);
		}
	}
	
	@Override
	public void setImage() {
		super.setImage();
		for(Ball ball: balls) {
			ball.setImage();
		}
	}

	@Override
	public void setPosition(double x, double y) {
		super.setPosition(x, y);
		double i = 0;
		for (Ball ball : balls) {
			ball.setPosition(x, y + i);
			ball.s1=x;
			ball.s2=x;
			ball.s3=x;
			ball.s4=x;
			ball.s5=x;
			i = i + height;
		}
		s1=x;
		s2=x;
		s3=x;
		s4=x;
		s5=x;
	}

	@Override
	public void setPositionX(double x) {
		s5=s4;
		s4=s3;
		s3=s2;
		s2=s1;
		s1=positionX;
		double temp=s4;
		super.setPosition(x, this.positionY);
		double i = 0;
		for (Ball ball : balls) {
			ball.setPositionX(temp);
			i = i + height;
			temp=ball.s4;
		}
	}

	@Override
	public void setPositionY(double y) {
		super.setPosition(this.positionX, y);
		double i = y;
		for (Ball ball : balls) {
			ball.setPositionY(i);
			i = i + height;
		}
	}

	@Override
	public void setVelocity(double x, double y) {
		super.setVelocity(x, y);
		for (Ball ball : balls) {
			ball.setVelocity(x, y);
		}
	}

	@Override
	public void addVelocity(double x, double y) {
		super.addVelocity(x, y);
		for (Ball ball : balls) {
			ball.addVelocity(x, y);
		}
	}

	@Override
	public void update(double time) {
		s5=s4;
		s4=s3;
		s3=s2;
		s2=s1;
		s1=positionX;
		positionX += velocityX * time;
		if (positionX < screenCoordinates[0])
			positionX = 0;
		else if (positionX > screenCoordinates[1] - width)
			positionX = screenCoordinates[1] - width;
		double temp=positionX;
		for (Ball ball : balls) {
			ball.setPositionX(temp);
			temp=ball.s4;
		}
		if (isShieldActive()) {
			shieldTime -= time;
		} else {
			shieldTime = 0;
		}
		if (isMagnetActive()) {
			magnetTime -= time;
		} else {
			magnetTime = 0;
		}
	}

	@Override
	public void render(GraphicsContext gc) {
		for (Ball ball : balls) {
			ball.render(gc);
		}
		// System.out.println(positionX);
		gc.setStroke(Color.WHITE);
		gc.strokeText(Integer.toString(len - 1), positionX + width / 2.8, positionY);
	}

	@Override
	public boolean intersects(Sprite s) {
		return balls.get(0).intersects(s);
	}

	@Override
	public String toString() {
		return "Snake Object\nLength: " + len + " " + super.toString();
	}

	public void decreaseLength() throws SnakeLengthZeroException {
		len = len - 1;
		try {
			balls.remove(len);
		} catch (IndexOutOfBoundsException e) {
			throw new SnakeLengthZeroException("Snake Died!");
		}
		if (len == 0) {
			throw new SnakeLengthZeroException("Snake Died!");
		}
	}

	public void addBalls(int n) {
		double posY = len * height;
		double temp=0;
		for(Ball ball:balls) {
			temp=ball.s4;
		}
		for (int i = 0; i < n; i++) {
			Ball new_ball = new Ball(screenCoordinates);
			new_ball.setImage(this.image);
			new_ball.setPosition(temp, positionY + posY);
			new_ball.s4=temp;
			new_ball.s3=temp;
			new_ball.s2=temp;
			new_ball.s1=temp;
			new_ball.s5=temp;
			balls.add(new_ball);
			posY += height;
		}
		len += n;
	}

	public void activateShield() {
		shieldTime = 5;
	}

	public boolean isShieldActive() {
		if (shieldTime > 0) {
			return true;
		} else
			return false;
	}

	public void activateMagnet() {
		magnetTime = 5;
	}

	public boolean isMagnetActive() {
		if (magnetTime > 0) {
			return true;
		} else
			return false;
	}

	public Circle magnetBoundary() {
		if (isMagnetActive())
			return new Circle(positionX, positionY, 200);
		else
			return new Circle(positionX, positionY, 0);
	}

	public boolean magnetIntersects(Coin coin) {
		double centerX = coin.positionX + coin.width;
		double centerY = coin.positionY + coin.height;
		Circle circle = magnetBoundary();
		double distance = Math
				.sqrt(Math.pow(circle.getCenterX() - centerX, 2) + Math.pow(circle.getCenterY() - centerY, 2));
		if (distance > (circle.getRadius() + coin.width / 2))
			return false;
		else
			return true;
	}

	public int getLength() {
		return len;
	}
	public void addCoin() {
		this.coins+=1;
	}
	public int getCoins() {
		return this.coins;
	}
	public void activateDestroyAllBlocks(){
		this.destroyAllBlocks=true;
	}
	public boolean consumeDestroyAllBlocks() {
		if(destroyAllBlocks) {
			destroyAllBlocks = false;
			return true;
		}
		else 
			return false;
	}
}
