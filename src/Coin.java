import java.io.Serializable;

public class Coin extends Sprite implements Tokenizable, Serializable {

	public Coin(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	@Override
	public void update(double time) {
		positionY += velocityY * time;
		positionX += velocityX * time;
	}

	public void action(Snake snake) {
		snake.addCoin();
	}
}
