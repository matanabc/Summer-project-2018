package robot.commands.drive;

import LogFile.CommandWrite;
import robot.Robot;
import robot.subsystems.DriveSystem;

public class DriveWithJoystickCommanWrite extends CommandWrite {

	private double speed_, turn_;
	
	public DriveWithJoystickCommanWrite() {
		super("Drive with joystick command", Robot.logFile);
		
		requires( DriveSystem.getInstance());
	}

	@Override
	protected void initializeWrite() {
		
	}

	@Override
	protected void executeWrite() {
		speed_ = Robot.oi.AdelStick.getRawAxis(1);
		turn_ = Robot.oi.AdelStick.getRawAxis(3) - Robot.oi.AdelStick.getRawAxis(2);
	
		 DriveSystem.getInstance().arcade(-speed_, turn_);	
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void endWrite() {
		 DriveSystem.getInstance().tank(0,0);
	}
	
	
	//---If you whant you can add them---\\
	protected String whenInitializeWrite(){
		return "geting reade";
	}
	
	protected String whenExecuteWrite(){
		return "Starting!!!!!!";
	}
	
	protected String whenEndWrite(){
		return "Good by";
	}
}
