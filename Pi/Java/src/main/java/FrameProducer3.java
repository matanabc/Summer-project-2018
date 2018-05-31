
import java.util.concurrent.BlockingQueue;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import org.opencv.videoio.VideoCapture;

public class FrameProducer3 implements Runnable{
	
	protected BlockingQueue<Mat> queue = null;
	private Mat inputImage = null; 

	//camera sittings 
	private static final int FPS = 30	;
	private static final int Brightness = 0;
	private static final int Exposure = 0;

	//Resolution 
	private static final int Width = 320;
	private static final int Height = 240;

	//camera object 
	//private UsbCamera camera = null;
	VideoCapture camera = null;

	// This creates a CvSink for us to use. This grabs images from our selected camera, 
	// and will allow us to use those images in opencv
	CvSink imageSink = new CvSink("CV Image Grabber");

	public FrameProducer3(int cameraId, BlockingQueue<Mat> queue) {
		//creating camera object 
		//this.camera = setUsbCamera(cameraId);// is the camera port, Usually the camera will be on device 0

		//Seating the camera
		//this.camera.setResolution(Width,Height);//Usually this well be in low Resolution for faster process  
		//this.camera.setFPS(FPS);//These will be who you want or what the camera can do 
		//this.camera.setBrightness(Brightness);
		//this.camera.setExposureManual(Exposure);

		System.out.println("open camera");
		this.camera =  new VideoCapture(cameraId);
		//this.camera.set(3, Width);
		//this.camera.set(4, Height);
		//this.camera.set(15, Exposure);
		System.out.println("Exposure:" + this.camera.get(15));
		//this.camera.set(5, FPS);
		
		//this.imageSink.setSource(this.camera);
		
		this.inputImage = new Mat();
		
		this.queue = queue;
	}
	
	public void run() {
		while (true) {
			// Grab a frame. If it has a frame time of 0, there was an error.
			// Just skip and continue
			//long frameTime = this.camera.read(inputImage);
			//if (frameTime == 0) continue;
			try {
				this.camera.read(inputImage);
				this.queue.put(inputImage);
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
