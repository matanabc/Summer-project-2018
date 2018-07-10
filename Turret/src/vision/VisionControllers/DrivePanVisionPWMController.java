package vision.VisionControllers;

import edu.wpi.first.wpilibj.command.Subsystem;
import robot.Robot;

public class DrivePanVisionPWMController implements VisionPanController{

	@Override
	public double getPanSource() {
		// TODO Auto-generated method stub
		return Robot.driveSystem.getAngleNavx();
	}

	@Override
	public void setOutput(double output) {
		// TODO Auto-generated method stub
		Robot.driveSystem.tank(output, -output);
	}

	@Override
	public Subsystem getSubsystem() {
		// TODO Auto-generated method stub
		return Robot.driveSystem;
	}
}
