import java.io.Serializable;

public class Wall extends Sprite implements Serializable{

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
