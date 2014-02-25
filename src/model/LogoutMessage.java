package model;
/**
 * Message which is send when user logs out
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 *
 */
public class LogoutMessage implements Message{
	private String name;
	
	/**
	 * Creates a message to log out the User
	 * @param name	Name of the User
	 */
	public LogoutMessage(String name){
		this.name=name;
	}
	@Override
	public String getName() {
		return name;//null wurde vorher zurueckgeben -huang
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}