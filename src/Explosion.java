import java.io.Serializable;

import javafx.scene.image.Image;

public class Explosion extends Sprite implements Serializable {
	private boolean destroy = false;
	private double destroy_time_left;
	private Explosion(int[] screenCoordinates,double time) {
		super(screenCoordinates);
		destroy_time_left = time; //in seconds
	}
	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}
	@Override
	public boolean intersects(Sprite s) {
		return false;
	}
	public boolean isDestroyable(double time) {
		destroy_time_left -= time;
		if(destroy_time_left>0) {
			return false;
		}
		else {
			return true;
		}
	}
	public static Explosion getExplosionObject(Sprite s, String path, int[] screenCoordinates, double time) {
		Explosion e = new Explosion(screenCoordinates,time);
		if(s instanceof Block) {
			e.setImage(new Image(path,s.width,s.height,false,false));
			e.setPosition(s.positionX, s.positionY);
		}
		else {
			e.setImage(new Image(path,s.width*5,s.height*5,false,false));
			e.setPosition(s.positionX-e.width/2.5, s.positionY-e.height/2.4);
		}
		e.setImagePath(path);
		e.addVelocity(0, s.velocityY);
		return e;
	}
}
