package model;
/**
 * Message which is send when User lists auctions
 * @author Daniel
 *
 */
public class ListMessage implements Message{
	private String name;

	/**
	 * Creates new ListMessage
	 * @param name	Name of the user who wants it
	 */
	public ListMessage(String name){
		this.name=name;
	}
	/**
	 * Creates empty ListMessage
	 */
	public ListMessage() {
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