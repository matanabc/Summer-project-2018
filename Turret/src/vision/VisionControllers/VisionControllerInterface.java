package vision.VisionControllers;

import MotionProfiling.PID_Classes.PID_Gains;
import edu.wpi.first.wpilibj.command.Subsystem;

public interface VisionControllerInterface{
	
	/**
	 * ...
	 * This method is similar to {@link #getSubsystem()}, with the following differences:
	 * ...
	 */
	
	public Subsystem getSubsystem();
	
	public PID_Gains getGains();
	
	public double getSource();

	public void setOutput(double output);
	
	public double getMaxOutput();

	public double getMaxerror();
	
	public double castYpixel(double yPixel, double sourcePosition);
	
	/*public double TargetHightToDistance(double targetHight);
	
	public double TargetHightToRPM(double targetHight);
	
	public double TargetHightToAngle(double targetHight);*/
}
