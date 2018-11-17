
public class DestroyBlocksPowerUp extends Sprite {

	public DestroyBlocksPowerUp(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}
}
