package JUnitTests;

import java.util.ArrayList;

import management.ClientInterface;
import Event.Event;

public class MockClientInterface implements ClientInterface{
	public ArrayList<Event> events;
	
	public MockClientInterface(){
		events = new ArrayList<>();
	}
	@Override
	public void notify(Event e) {
		System.out.println("Added new Event");
		events.add(e);	
	}
	
	public ArrayList<Event> getEvents(){
		return events;
	}

}
