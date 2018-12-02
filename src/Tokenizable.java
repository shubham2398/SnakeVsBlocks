/**
 * This interface represents all the tokens have a common method action.
 * This gives a functionality of actionable to all the tokens. 
 * @author SHUBHAM THAKRAL, TANMAY BANSAL
 *
 */
public interface Tokenizable {
	/**
	 * This method can be defined by classes which implement this interface to perform an action when snake gets the token
	 * @param s is the snake object on which we want to perform action
	 */
	public void action(Snake s);
}
