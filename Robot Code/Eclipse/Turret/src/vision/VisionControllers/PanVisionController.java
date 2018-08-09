package vision.VisionControllers;

import MotionProfiling.PID_Classes.PID_Gains;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.RobotMap;
import robot.subsystems.DriveSystem;
import vision.VisionClass.VisionControllerInterface;

public class PanVisionController implements VisionControllerInterface{

	@Override
	public double getSource() {
		// Need to return Source for PID
		return  DriveSystem.getInstance().getAngleNavx();
	}

	@Override
	public Subsystem getSubsystem() {
		// Need to return the subsystem who will do the tilt
		return  DriveSystem.getInstance();
	}

	@Override
	public void setOutput(double output) {
		// Need to put the output value for motors
		 DriveSystem.getInstance().tank(output, -output);
	}

	@Override
	public PID_Gains getGains() {
		// Need to return PID Gains for tilt system
		return  DriveSystem.getInstance().getVisoinPanPIDGains();
	}

	@Override
	public double getMaxOutput() {
		// Need to return pan max output
		return RobotMap.PAN_MAX_OUTPUT;
	}

	@Override
	public double getMaxerror() {
		// Need to return tilt max error
		return RobotMap.TILT_MAX_ERROR;
	}

	@Override
	public double castYpixel(double yPixel, double sourcePosition) {
		// TODO Auto-generated method stub
		return 0;
	}
}
