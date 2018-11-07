
public class Shield extends Sprite{

	public Shield(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	@Override
	public void update(double time) {
		positionY += velocityY*time;
	}

	public void giveShield(Snake snake) {
		snake.activateShield();
	}
	
}
