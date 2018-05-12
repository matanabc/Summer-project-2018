

import org.opencv.core.Mat;

public final class MatTime {
	private Mat mat;
	private double time;
	
	public MatTime(Mat mat, double time) {
		super();
		this.mat = mat;
		this.time = time;
	}
	@Override
	public String toString() {
		return "MatTime [mat=" + mat + ", time=" + time + "]";
	}
	public Mat getMat() {
		return mat;
	}
	public double getTime() {
		return time;
	}	
}
