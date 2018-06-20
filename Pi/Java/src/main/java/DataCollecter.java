
import org.opencv.core.Mat;

public class DataCollecter {
	
	private Mat image;
	private Mat HSV;
	private String targetInfo;
	
	public void update(Mat image, Mat HSV, String targetInfo) {
		this.targetInfo = targetInfo;
		this.HSV = HSV;
		this.image = image;
	}

	public String getTargetInfo() {
		return targetInfo;
	}
	
	public Mat getImage() {
		return image;
	}
	
	public Mat getHSV() {
		return HSV;
	}
}
