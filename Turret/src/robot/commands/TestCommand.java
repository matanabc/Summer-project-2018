package robot.commands;

import java.util.LinkedList;

import robot.Robot;

public class TestCommand extends CommandWrite {

	private double speed_, turn_;
	
	public TestCommand() {
		super("Test Command", new LinkedList<String>());
		
		requires(Robot.driveSystem);
	}

	@Override
	protected void initializeWrite() {
		
	}

	@Override
	protected void executeWrite() {
		speed_ = Robot.oi.AdelStick.getRawAxis(1);
		turn_ = Robot.oi.AdelStick.getRawAxis(2);//4	-
	
		Robot.driveSystem.arcade(-speed_, turn_);	
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void endWrite() {
		Robot.driveSystem.tank(0,0);
	}
}