package robot.commands;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandWrite extends Command {

	protected LinkedList<String> write;
	protected String commandName;
	protected boolean isExecute;

	public CommandWrite(String commadName, LinkedList<String> write) {
		this.commandName = commadName;
		this.write = write;
		this.isExecute = false;
	}

	protected abstract void initializeWrite();
	
	protected String whenInitializeWrite(){
		return "";
	}

	protected void initialize() {   	
		//write.add(commandName + " is initialize");

		this.isExecute = false;
		System.out.println(commandName + " is initialize, " + whenInitializeWrite());

		initializeWrite();
	}

	protected abstract void executeWrite();
	
	protected String whenExecuteWrite(){
		return "";
	}

	protected void execute() {
		//write.add(commandName + " is execute");
		if(!this.isExecute){	
			System.out.println(commandName + " is execute, " + whenExecuteWrite());
			this.isExecute = true;
		}
		
		executeWrite();
	}

	protected abstract boolean isFinished();

	protected abstract void endWrite();
	
	protected String whenEndWrite(){
		return "";
	}

	protected void end() {
		//write.add(commandName + " is end");
		this.isExecute = false;
		
		System.out.println(commandName + " is end, " + whenEndWrite());

		endWrite();
	}

	protected void interrupted() {
		//write.add(commandName + " is interrupted");
		this.isExecute = false;
		
		System.out.println(commandName + " is interrupted");

		endWrite();
	}
}
