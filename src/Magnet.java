
public class Magnet extends Sprite implements Tokenizable {

	public Magnet(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}

	public void action(Snake snake) {
		snake.activateMagnet();
	}
}
