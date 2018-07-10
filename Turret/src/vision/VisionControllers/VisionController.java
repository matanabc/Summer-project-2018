package vision.VisionControllers;

import edu.wpi.first.wpilibj.command.Subsystem;
import robot.Robot;

public class VisionController implements VisionControllerInterface{

	@Override
	public double getPanSource() {
		// TODO Auto-generated method stub
		return Robot.driveSystem.getAngleNavx();
	}

	@Override
	public double getTiltSource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Subsystem getPanSubsystem() {
		// TODO Auto-generated method stub
		return Robot.driveSystem;
	}

	@Override
	public Subsystem getTiltSubsystem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double TargetHightToDistance(double targetHight) {
		// TODO Auto-generated method stub
		return targetHight;
	}

	@Override
	public void setPanOutput(double output) {
		// TODO Auto-generated method stub
		Robot.driveSystem.tank(output, -output);
	}

	@Override
	public void setTiltOutput(double output) {
		// TODO Auto-generated method stub
		
	}

}
