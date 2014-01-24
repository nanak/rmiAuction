package Client;
/**
 * The implementations of this class define the output
 * 
 * @author Dominik Valka <dvalka@student.tgm.ac.at>
 * @version 2014-01-07
 */
public interface UI {
	/**
	 * Prints output without Backspace
	 * @param output	Text
	 */
	public void out(String output);
	/**
	 * Prints output with new Line
	 * @param output	Text
	 */
	public void outln(String output);
}
