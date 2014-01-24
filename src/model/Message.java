package model;

import java.io.Serializable;
/**
 * Interface for messages to send over network
 * @author Daniel
 *
 */
public interface Message extends Serializable{
	public String getName();
}
