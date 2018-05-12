

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;

public class FrameProducer implements Runnable{

	private static final String FILE_PLACE = "/home/pi/Documents/vision/Java/Values";//fill place

	protected BlockingQueue<MatTime> queue = null;
	//private Mat inputImage = null; 

	//camera object 
	private UsbCamera camera = null;

	// This creates a CvSink for us to use. This grabs images from our selected camera, 
	// and will allow us to use those images in opencv
	CvSink imageSink = new CvSink("CV Image Grabber");

	public FrameProducer(int cameraId, BlockingQueue<MatTime> queue) {
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(FILE_PLACE));//fill place to use
			//creating camera object 
			this.camera = setUsbCamera(Integer.parseInt(properties.getProperty("camera_Id", "0")));// is the camera port, Usually the camera will be on device 0

			//Seating the camera
			this.camera.setResolution(Integer.parseInt(properties.getProperty("camera_Width", "320")), Integer.parseInt(properties.getProperty("camera_Height", "240")));//Usually this well be in low Resolution for faster process  
			this.camera.setFPS(Integer.parseInt(properties.getProperty("camera_FPS", "30")));//These will be who you want or what the camera can do 
			this.camera.setBrightness(Integer.parseInt(properties.getProperty("camera_Brightness", "50")));
			this.camera.setExposureManual(Integer.parseInt(properties.getProperty("camera_Exposure", "0")));

			this.imageSink.setSource(this.camera);
			
			//this.inputImage = new Mat();
			
			this.queue = queue;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			// Grab a frame. If it has a frame time of 0, there was an error.
			// Just skip and continue
			
			Mat inputImage = new Mat();
			
			long frameTime = imageSink.grabFrame(inputImage);
			
			MatTime matTimeToQueue = new MatTime(inputImage, Calendar.getInstance().getTimeInMillis());
			
			if (frameTime == 0) continue;
			try {
				this.queue.put(matTimeToQueue);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static UsbCamera setUsbCamera(int cameraId) {
		UsbCamera camera = new UsbCamera("CoprocessorCamera", cameraId);
		return camera;
	}

}
