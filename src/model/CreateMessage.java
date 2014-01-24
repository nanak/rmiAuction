package model;
/**
 * Message which is send, when user Creates an auction
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 *
 */
public class CreateMessage implements Message{
	private String name;
	private String desc;
	private Long duration; //auf long geaendert da ueberall verwendet tobias 
	
	public CreateMessage() {
	}
	
	public CreateMessage(String name,String desc,Long duration){
		this.name=name;
		this.desc=desc;
		this.duration=duration;
	}
	@Override
	public String getName() {
		return name;//null wurde vorher zurueckgeben -huang
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the duration
	 */
	public Long getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}