package management;

import java.util.concurrent.ConcurrentHashMap;

import exceptions.*;


public class CommandFactory {
ConcurrentHashMap<String, Command> map;

	public CommandFactory(){
		map = new ConcurrentHashMap<String, Command>();	
		map.put("!addStep", new AddStep());
		// TODO add cmds
	}
	
	public Command createCommand(String[] args) {
		return null;
	}
	
	// TODO check if exceptions is really thrown this way
	public boolean runCommand(String cmd) throws CommandNotFoundException{
		Object Command;
		// TODO check if it ever return null
		if(map.get(cmd.split(" ")[0])!=null){
			Command c = (Command) map.get(cmd.split(" ")[0]);
			c.execute(cmd);
		}
		else{
			throw new CommandNotFoundException();
		}
		return false;
	}
}
