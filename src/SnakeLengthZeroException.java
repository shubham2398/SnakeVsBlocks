/**
 * This exception is thrown when the snake is dead i.e. when it has no length
 * (i.e. no head)
 * 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public class SnakeLengthZeroException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * The constructor takes message and passes it to super class constructor
	 * 
	 * @param message is the message passed when this exception is thrown
	 */
	public SnakeLengthZeroException(String message) {
		super(message);
	}
}
