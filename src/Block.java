import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

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
        Font f = gc.getFont();
        TextAlignment t = gc.getTextAlign();
        VPos v = gc.getTextBaseline();
        gc.setFont(new Font("System Regular",30));
    	gc.setStroke(Color.BLACK);
    	gc.setFill(Color.BLACK);
    	gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.strokeText( Integer.toString(number), positionX+width/2, positionY+height/2);
        gc.fillText( Integer.toString(number), positionX+width/2, positionY+height/2);
        gc.setFont(f);
        gc.setTextAlign(t);
        gc.setTextBaseline(v);
    }
	@Override
	public String toString() {
		return "Block Object\n"+super.toString();
	}
	@Override
	public boolean intersects(Sprite s)
    {
		return s.getTopHalfBoundary().intersects( this.getBottomBoundary() );
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
