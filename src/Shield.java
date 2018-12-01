import java.io.Serializable;

public class Shield extends Sprite implements Tokenizable, Serializable {

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
