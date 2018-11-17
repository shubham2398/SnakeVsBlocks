
public class Wall extends Sprite {

	public Wall(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}

	@Override
	public boolean intersects(Sprite s) {
		return false;
	}
}
