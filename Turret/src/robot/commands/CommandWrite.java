package robot.commands;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandWrite extends Command {

	protected LinkedList<String> write;
	protected String commandName;
	
    public CommandWrite(String commadName, LinkedList<String> write) {
    	this.commandName = commadName;
    	this.write = write;
    }
    
    protected abstract void initializeWrite();
    
    protected void initialize() {   	
    	//write.add(commandName + " is initialize");
    	
    	System.out.println(commandName + " is initialize");
    	
    	initializeWrite();
    }

    protected abstract void executeWrite();
    
    protected void execute() {
    	//write.add(commandName + " is execute");
    	
    	System.out.println(commandName + " is execute");
    	
    	executeWrite();
    }

    protected abstract boolean isFinished();

    protected abstract void endWrite();
    
    protected void end() {
    	//write.add(commandName + " is end");
    	
    	System.out.println(commandName + " is end");
    	
    	endWrite();
    }

    protected void interrupted() {
    	//write.add(commandName + " is interrupted");
    	
    	System.out.println(commandName + " is interrupted");
    	
    	endWrite();
    }
}
