package vision.VisionControllers;

import MotionProfiling.PID_Classes.PID_Gains;
import edu.wpi.first.wpilibj.command.Subsystem;

public interface VisionControllerInterface{

	public double getPanSource();

	public double getTiltSource();

	public void setPanOutput(double output);

	public void setTiltOutput(double output);

	public Subsystem getPanSubsystem();
	
	public Subsystem getTiltSubsystem();
	
	public PID_Gains getPanGains();
	
	public PID_Gains getTiltGains();
	
	public double panMaxOutput();
	
	public double tiltMaxOutput();
	
	public double panMaxerror();

	public double tiltMaxerror();
	
	public double TargetHightToDistance(double targetHight);
	
	public double TargetHightToRPM(double targetHight);
	
	public double TargetHightToAngle(double targetHight);
}
