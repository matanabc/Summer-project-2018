package vision.VisionControllers;

public interface VisionTiltController extends VisionPanController{

	public double targetHightToDistance(double targetHight);
	
	public double getTiltSource();

}
