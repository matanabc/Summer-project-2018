package vision.VisionControllers;

import MotionProfiling.MP_Classes.MPGains;
import MotionProfiling.PID_Classes.PID_Gains;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.Robot;
import robot.subsystems.Turret.TurretTiltSystem;
import vision.VisionClass.VisionControllerInterface;

public class TurretTiltVisionController implements VisionControllerInterface{

	@Override
	public double getSource() {
		// Need to return Source for PID
		return 0;
	}

	@Override
	public Subsystem getSubsystem() {
		// Need to return the subsystem who will do the tilt
		return TurretTiltSystem.getInstance();
	}

	@Override
	public void setOutput(double output) {
		// Need to put the output value for motors
		TurretTiltSystem.getInstance().setpointPosition(castYpixel(output, 0));
	}

	@Override
	public double getMaxOutput() {
		// Need to return pan max output
		return 0;
	}

	@Override
	public double getMaxerror() {
		// Need to return tilt max error
		return 0;
	}

	@Override
	public double castYpixel(double yPixel, double sourcePosition) {
		// TODO Auto-generated method stub
		return yPixel;
	}

	@Override
	public PID_Gains getPIDGains() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MPGains getMPGains() {
		// TODO Auto-generated method stub
		return null;
	}
}
