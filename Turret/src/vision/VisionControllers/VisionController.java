package vision.VisionControllers;

import MotionProfiling.PID_Classes.PID_Gains;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.Robot;
import robot.RobotMap;

public class VisionController implements VisionControllerInterface{

	@Override
	public double getPanSource() {
		// Need to return Source for PID
		return Robot.driveSystem.getAngleNavx();
	}

	@Override
	public double getTiltSource() {
		// Need to return Source for PID
		return 0;
	}

	@Override
	public Subsystem getPanSubsystem() {
		// Need to return the subsystem who will do the pan
		return Robot.driveSystem;
	}

	@Override
	public Subsystem getTiltSubsystem() {
		// Need to return the subsystem who will do the tilt
		return null;
	}

	@Override
	public void setPanOutput(double output) {
		// Need to put the output value for motors
		Robot.driveSystem.tank(output, -output);
	}

	@Override
	public void setTiltOutput(double output) {
		// Need to put the output value for motors
	}

	@Override
	public PID_Gains getPanGains() {
		// Need to return PID Gains for pan system
		return Robot.driveSystem.getVisoinPanPIDGains();
	}

	@Override
	public PID_Gains getTiltGains() {
		// Need to return PID Gains for tilt system
		return Robot.driveSystem.getVisoinTiltPIDGains();
	}

	@Override
	public double panMaxOutput() {
		// Need to return pan max output
		return RobotMap.PAN_MAX_OUTPUT;
	}

	@Override
	public double tiltMaxOutput() {
		// Need to return tilt max output
		return RobotMap.TILT_MAX_OUTPUT;
	}

	@Override
	public double panMaxerror() {
		// Need to return pan max error
		return RobotMap.PAN_MAX_ERROR;
	}

	@Override
	public double tiltMaxerror() {
		// Need to return tilt max error
		return RobotMap.TILT_MAX_ERROR;
	}

	@Override
	public double TargetHightToDistance(double targetHight) {
		// Need to return the calculate from target height to distance
		return targetHight;
	}

	@Override
	public double TargetHightToRPM(double targetHight) {
		// Need to return the calculate from target height to RPM
		return 0;
	}

	@Override
	public double TargetHightToAngle(double targetHight) {
		// Need to return the calculate from target height to angle
		return 0;
	}
}
