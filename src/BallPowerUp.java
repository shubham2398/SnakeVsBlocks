import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BallPowerUp extends Ball implements Tokenizable {
	int len;

	public BallPowerUp(int[] screenCoordinates, int len) {
		super(screenCoordinates);
		this.len = len;
	}

	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}

	@Override
	public void render(GraphicsContext gc) {
		super.render(gc);
		gc.setStroke(Color.WHITE);
		gc.strokeText(Integer.toString(len), positionX + width / 2.8, positionY);
	}

	public void action(Snake snake) {
		snake.addBalls(len);
	}
}
