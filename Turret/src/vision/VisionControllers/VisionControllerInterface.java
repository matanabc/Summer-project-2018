package vision.VisionControllers;

import edu.wpi.first.wpilibj.command.Subsystem;

public interface VisionControllerInterface{

	public double getPanSource();

	public double getTiltSource();

	public void setPanOutput(double output);

	public void setTiltOutput(double output);

	public Subsystem getPanSubsystem();
	
	public Subsystem getTiltSubsystem();
	
	public double TargetHightToDistance(double targetHight);
}
