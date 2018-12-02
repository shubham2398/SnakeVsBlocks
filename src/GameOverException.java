
/**
 * This exception is thrown when the game gets over. This is invoked by the SnakeLengthZeroException
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class GameOverException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * The constructor takes message and passes it to super class constructor
	 * @param message is the message passed when this exception is thrown
	 */
	public GameOverException(String message) {
		super(message);
	}
}
