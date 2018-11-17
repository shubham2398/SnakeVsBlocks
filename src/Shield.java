
public class Shield extends Sprite implements Tokenizable {

	public Shield(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}

	public void action(Snake snake) {
		snake.activateShield();
	}

}
