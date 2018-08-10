package vision.VisionControllers;

import MotionProfiling.MP_Classes.MPGains;
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
	public double getMaxOutput() {
		// Need to return pan max output
		return RobotMap.DRIVE_PAN_MAX_OUTPUT;
	}

	@Override
	public double getMaxerror() {
		// Need to return tilt max error
		return RobotMap.DRIVE_TILT_MAX_ERROR;
	}

	@Override
	public double castYpixel(double yPixel, double sourcePosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PID_Gains getPIDGains() {
		// TODO Auto-generated method stub
		return DriveSystem.getInstance().getVisoinPanPIDGains();
	}

	@Override
	public MPGains getMPGains() {
		// TODO Auto-generated method stub
		return DriveSystem.getInstance().getVisoinPanMPGains();
	}
}
