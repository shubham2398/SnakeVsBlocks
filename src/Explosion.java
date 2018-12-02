import java.io.Serializable;

import javafx.scene.image.Image;

/**
 * This class represents the Explosion object that gets created whenever block
 * gets destroyed or snake gets a token It is a child of Sprite class and is
 * serializable.
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class Explosion extends Sprite implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * destroy defines if this object can be destroyed. destroy_time_left defines
	 * the time left to destroy this object. This object gets destroyed after its
	 * explosion has been shown fully.
	 */
	private double destroy_time_left;

	/**
	 * In the constructor, we take screenCoordinates and pass it to the super class
	 * constructor. The constructor is private so that user cannot call it directly
	 * as there are certain properties that need to be defined according to which
	 * object has exploded
	 * 
	 * @param screenCoordinates is an array of upper and lower bounds of the screen
	 *                          in XY plane. It is taken so that objects can know
	 *                          the boundaries of screen.
	 * @param time              represents the time for the explosion to complete
	 */
	private Explosion(int[] screenCoordinates, double time) {
		super(screenCoordinates);
		destroy_time_left = time; // in seconds
	}

	/**
	 * This defines the abstract update method of Sprite class such that this can
	 * only move in Y direction
	 */
	@Override
	public void update(double time) {
		positionY += velocityY * time;
	}

	/**
	 * This overrides the intersects method of sprite class so that this object can
	 * never intersect with anything.
	 */
	@Override
	public boolean intersects(Sprite s) {
		return false;
	}

	/**
	 * This methods checks if the object can be destroyed or not based on the
	 * destroy time left.
	 * 
	 * @param time is the time which has passed since last check
	 * @return true if object can be destroyed, else false
	 */
	public boolean isDestroyable(double time) {
		destroy_time_left -= time;
		if (destroy_time_left > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This gives the Explosion object based on the sprite which has exploded
	 * 
	 * @param s                 is the sprite which has exploded
	 * @param path              is the path to the explosion image
	 * @param screenCoordinates is an array of upper and lower bounds of the screen
	 *                          in XY plane. It is taken so that objects can know
	 *                          the boundaries of screen.
	 * @param time              is the duration it takes for this explosion to
	 *                          complete
	 * @return Explosion object that is valid for that sprite's collision
	 */
	public static Explosion getExplosionObject(Sprite s, String path, int[] screenCoordinates, double time) {
		Explosion e = new Explosion(screenCoordinates, time);
		if (s instanceof Block) {
			e.setImage(new Image(path, s.width, s.height, false, false));
			e.setPosition(s.positionX, s.positionY);
		} else {
			e.setImage(new Image(path, s.width * 5, s.height * 5, false, false));
			e.setPosition(s.positionX - e.width / 2.5, s.positionY - e.height / 2.4);
		}
		e.setImagePath(path);
		e.addVelocity(0, s.velocityY);
		return e;
	}
}
