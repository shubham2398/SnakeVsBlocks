
public class Magnet extends Sprite {

	public Magnet(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}

	public void activateMagnet(Snake snake) {
		snake.activateMagnet();
	}
}
