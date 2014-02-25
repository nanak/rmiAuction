package jUnitTests;

import java.util.ArrayList;

import event.Event;
import management.ClientInterface;

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
