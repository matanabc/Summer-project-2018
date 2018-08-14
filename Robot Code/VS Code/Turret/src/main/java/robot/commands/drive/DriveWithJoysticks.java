 package robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.subsystems.DriveSystem;

public class DriveWithJoysticks extends Command{

	private double speed_, turn_;
	//private double lastSpeed_ = 0;
	
	public DriveWithJoysticks() {
		// TODO Auto-generated constructor stub
		requires( DriveSystem.getInstance());
	}
	
	
	protected void initialize() {
		// TODO Auto-generated method stub
		//lastSpeed_ = 0;
	}

	protected void execute() {
		speed_ = Robot.oi.AdelStick.getRawAxis(1);
		turn_ = Robot.oi.AdelStick.getRawAxis(3) - Robot.oi.AdelStick.getRawAxis(2);//4	-
	
		 DriveSystem.getInstance().arcade(-speed_, turn_);
	}

	
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		 DriveSystem.getInstance().tank(0,0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
