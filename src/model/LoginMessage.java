package model;
/**
 * Message which is send when user logs in
 * @author Tobias Schuschnig <tschuschnig@student.tgm.ac.at>
 *
 */
public class LoginMessage implements Message{
	private String name;
	private String adresse;
	private int tcpPort, udpPort;
	
	/**
	 * LoginMessage for a user to log in
	 * @param name	Name of the user
	 * @param adresse	Hostname of the Client
	 * @param tcpPort	TCPPort of the Client
	 * @param udpPort	UDP Port the client listens
	 */
	public LoginMessage(String name,String adresse,int tcpPort,int udpPort){
		this.name=name;
		this.adresse=adresse;
		this.tcpPort=tcpPort;
		this.udpPort=udpPort;
	}
	/**
	 * Creates empty loginMessage
	 */
	public LoginMessage() {
		// TODO Auto-generated constructor stub
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
	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}
	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	/**
	 * @return the tcpPort
	 */
	public int getTcpPort() {
		return tcpPort;
	}
	/**
	 * @param tcpPort the tcpPort to set
	 */
	public void setTcpPort(int tcpPort) {
		this.tcpPort = tcpPort;
	}
	/**
	 * @return the udpPort
	 */
	public int getUdpPort() {
		return udpPort;
	}
	/**
	 * @param udpPort the udpPort to set
	 */
	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}
}