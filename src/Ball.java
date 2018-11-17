
public class Ball extends Sprite {
	public double s1,s2,s3,s4,s5;
	public Ball(int[] screenCoordinates) {
		super(screenCoordinates);
	}

	@Override
	public void update(double time) {
		positionX += velocityX * time;
		if (positionX < screenCoordinates[0])
			positionX = 0;
		else if (positionX > screenCoordinates[1] - width)
			positionX = screenCoordinates[1] - width;
	}

	@Override
	public String toString() {
		return "Ball Object\n" + super.toString();
	}
	@Override
	public void setPositionX(double x) {
		s5=s4;
		s4=s3;
		s3=s2;
		s2=s1;
		s1=positionX;
		positionX=x;
	}
}
