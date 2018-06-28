package vision.visionHistoryClass;

public class VisionData {
	
	private double angleToTarget;
	private double pixelHeightToTarget;
	
	public VisionData(double angleToTarget, double pixelHeightToTarget) {
		this.angleToTarget = angleToTarget;
		this.pixelHeightToTarget = pixelHeightToTarget;
	}
	
	public double getAngleToTarget() {
		return this.angleToTarget;
	}
	
	public double getPixelHeightToTarget() {
		return this.pixelHeightToTarget;
	}
}
