import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Block extends Sprite{
	private int number;
	public Block(int[] screenCoordinates,int number)
    {
        super(screenCoordinates);
        this.number = number;
    }
	@Override
	public void update(double time) {
		positionY += velocityY*time;
	}
	@Override
	public void render(GraphicsContext gc)
    {	//System.out.println("in render"+number);
        super.render(gc);
    	gc.setStroke(Color.BLACK);
        gc.strokeText( Integer.toString(number), positionX+width/2.3, positionY+height/1.8 );
    }
	@Override
	public String toString() {
		return "Block Object\n"+super.toString();
	}
	@Override
	public boolean intersects(Sprite s)
    {
		return s.getTopBoundary().intersects( this.getBottomBoundary() );
    }
	public void collide(Snake snake) throws SnakeLengthZeroException {
    	snake.decreaseLength();
	}
	public boolean canBeDestroyed(Snake snake) throws SnakeLengthZeroException {
		if(number<=1) {
			snake.decreaseLength();
			return true;
		}
		else {
			number--;
			return false;
		}
	}
	public void destroy() {
		number = 0;
	}
}
