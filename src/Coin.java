
public class Coin extends Sprite{

	public Coin(int[] screenCoordinates) {
		super(screenCoordinates);
	}
	@Override
	public void update(double time) {
		positionY += velocityY*time;
		positionX += velocityX*time;
	}

	public void addCoins(Snake snake) {
		
	}
}
