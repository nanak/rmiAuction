package management;

import java.util.concurrent.ConcurrentHashMap;


public class CommandFactory {
ConcurrentHashMap<String, Command> map;

	public CommandFactory(){
		map = new ConcurrentHashMap<String, Command>();	
	}
	
	public Command createCommand(String[] args) {
		return null;
	}
	
	public boolean runCommand(String cmd){
		Command c = (Command) map.get(cmd.split(" ")[0]);
		return false;
	}
}
