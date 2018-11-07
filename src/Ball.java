
public class Ball extends Sprite {
	public Ball(int[] screenCoordinates) {
		super(screenCoordinates);
	}
	@Override
	public void update(double time) {
		positionX += velocityX*time;
		if(positionX<screenCoordinates[0]) positionX=0;
		else if(positionX>screenCoordinates[1]-width) positionX=screenCoordinates[1]-width;
	}
	@Override
	public String toString() {
		return "Ball Object\n"+super.toString();
	}
}
