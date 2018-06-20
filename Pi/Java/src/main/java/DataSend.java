
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class DataSend implements Runnable{

	private static final String FILE_PLACE = "/home/pi/Documents/vision/Java/Values";//fill place

	BlockingQueue<DataCollecter> detaCollecterQueue;

	private NetworkTable VisionTable = null;

	//stream port s
	private CvSource imageSource = null;
	private MjpegServer cvStream = null;

	private CvSource hsvSource = null;
	private MjpegServer hsvStream = null;

	public DataSend(BlockingQueue<DataCollecter> detaCollecterQueue) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(FILE_PLACE));//fill place to use

			int Width = Integer.parseInt(properties.getProperty("camera_Width", "320"));
			int Height = Integer.parseInt(properties.getProperty("camera_Height", "240"));

			int FPS = Integer.parseInt(properties.getProperty("camera_FPS", "30"));

			// This creates a CvSource to use. This will take in a Mat image that has had OpenCV operations
			// operations 
			this.imageSource = new CvSource("CV Image and detect Source", VideoMode.PixelFormat.kMJPEG, Width, Height, FPS);
			this.cvStream = new MjpegServer("CV Image and detect Stream", Integer.parseInt(properties.getProperty("frame_Port", "1185")));
			this.cvStream.setSource(imageSource);

			this.hsvSource= new CvSource("CV hsv Source", VideoMode.PixelFormat.kMJPEG, Width, Height, FPS);
			this.hsvStream = new MjpegServer("CV hsv Stream",  Integer.parseInt(properties.getProperty("HSV_Port", "1186")));
			this.hsvStream.setSource(hsvSource);

			this.VisionTable = NetworkTable.getTable(properties.getProperty("table", "SmartDashboard"));

			this.detaCollecterQueue = detaCollecterQueue;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		try {
			if(!this.detaCollecterQueue.isEmpty()){
				this.imageSource.putFrame(this.detaCollecterQueue.take().getImage());
				
				this.hsvSource.putFrame(this.detaCollecterQueue.take().getHSV());
				
				VisionTable.putString("TargetInfo", this.detaCollecterQueue.take().getTargetInfo());
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
