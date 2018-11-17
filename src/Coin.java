
public class Coin extends Sprite implements Tokenizable {

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
