
public class DestroyBlocksPowerUp extends Sprite implements Tokenizable {

	public DestroyBlocksPowerUp(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}
	
	public void action(Snake snake) {
		snake.activateDestroyAllBlocks();
	}
}
