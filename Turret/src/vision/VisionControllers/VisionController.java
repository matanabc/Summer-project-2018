package vision.VisionControllers;

import MotionProfiling.PID_Classes.PID_Gains;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.Robot;
import robot.RobotMap;

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

	@Override
	public PID_Gains getPanGains() {
		// TODO Auto-generated method stub
		return Robot.driveSystem.getVisoinPanPIDGains();
	}

	@Override
	public PID_Gains getTiltGains() {
		// TODO Auto-generated method stub
		return Robot.driveSystem.getVisoinTiltPIDGains();
	}

	@Override
	public double panMaxOutput() {
		// TODO Auto-generated method stub
		return RobotMap.PAN_MAX_OUTPUT;
	}

	@Override
	public double tiltMaxOutput() {
		// TODO Auto-generated method stub
		return RobotMap.TILT_MAX_OUTPUT;
	}

	@Override
	public double panMaxerror() {
		// TODO Auto-generated method stub
		return RobotMap.PAN_MAX_ERROR;
	}

	@Override
	public double tiltMaxerror() {
		// TODO Auto-generated method stub
		return RobotMap.TILT_MAX_ERROR;
	}
}
