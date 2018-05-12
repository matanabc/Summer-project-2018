
import java.util.Calendar;
import java.util.concurrent.BlockingQueue;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;

public class FrameProducer implements Runnable{

	protected BlockingQueue<MatTime> queue = null;
	//private Mat inputImage = null; 

	//camera sittings 
	private static final int FPS = 30;
	private static final int Brightness = 50;
	private static final int Exposure = 0;

	//Resolution 
	private static final int Width = 320;
	private static final int Height = 240;

	//camera object 
	private UsbCamera camera = null;

	// This creates a CvSink for us to use. This grabs images from our selected camera, 
	// and will allow us to use those images in opencv
	CvSink imageSink = new CvSink("CV Image Grabber");

	public FrameProducer(int cameraId, BlockingQueue<MatTime> queue) {
		//creating camera object 
		this.camera = setUsbCamera(cameraId);// is the camera port, Usually the camera will be on device 0

		//Seating the camera
		this.camera.setResolution(Width,Height);//Usually this well be in low Resolution for faster process  
		this.camera.setFPS(FPS);//These will be who you want or what the camera can do 
		this.camera.setBrightness(Brightness);
		this.camera.setExposureManual(Exposure);

		this.imageSink.setSource(this.camera);
		
		//this.inputImage = new Mat();
		
		this.queue = queue;
	}
	
	public void run() {
		while (true) {
			// Grab a frame. If it has a frame time of 0, there was an error.
			// Just skip and continue
			
			Mat inputImage = new Mat();
			
			long frameTime = imageSink.grabFrame(inputImage);
			
			MatTime matTimeToQueue = new (inputImage, Calendar.getInstance().getTimeInMillis());
			
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
