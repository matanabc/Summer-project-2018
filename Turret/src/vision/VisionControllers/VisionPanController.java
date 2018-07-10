package vision.VisionControllers;

import edu.wpi.first.wpilibj.command.Subsystem;

public interface VisionPanController{

	public double getPanSource();

	public void setOutput(double output);

	public Subsystem getSubsystem();
}
