
public class DoubleScorePowerUp extends Sprite implements Tokenizable{

	public DoubleScorePowerUp(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	@Override
	public void action(Snake s) {
		s.activateDoubleScore();
	}

	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}
	
}
