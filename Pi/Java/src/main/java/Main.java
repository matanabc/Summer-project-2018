

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Main {

	private static final String FILE_PLACE = "/home/pi/Documents/vision/Java/Values";//fill place
	
	public static void main(String[] args) {
		System.out.println("Starting! ");
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(FILE_PLACE));
			
			// Loads our OpenCV library. This MUST be included
			System.loadLibrary("opencv_java310");

			// Connect NetworkTables, and get access to the publishing table
			NetworkTable.setClientMode();
			NetworkTable.setIPAddress(properties.getProperty("roboRioIP", "roborio-3211-FRC.local"));//ip of the robot
			NetworkTable.initialize();
			NetworkTable VisionTable = NetworkTable.getTable(properties.getProperty("table", "SmartDashboard"));

			VisionTable.putBoolean("pi connect", true);//Upload to the robot that he connect to him 
			
			BlockingQueue<MatTime> queue = new ArrayBlockingQueue<MatTime>(1);//Queue for all the frmes whe collect

			//BlockingQueue<DataCollecter> dataCollecterQueue = new ArrayBlockingQueue<DataCollecter>(1);//Queue for all the frmes whe collect

			boolean showHSV = Boolean.parseBoolean(properties.getProperty("HSVShow", "false"));
			
			//taking frames from the camera and pot them to the Queue 
			FrameProducer producer = new FrameProducer(false, queue);
			
			//taking the frame from the Queue and do vision processing on them 
			FrameConsumer consumer1 = new FrameConsumer(queue, showHSV);
			FrameConsumer consumer2 = new FrameConsumer(queue, showHSV);
			
			//DataSend dataSend = new DataSend(dataCollecterQueue);
			
			//making them to work in Thread
			new Thread(producer).start();
			
			new Thread(consumer1).start();
			new Thread(consumer2).start();
			
			//new Thread(dataSend).start();
			
		}catch (Exception e) {
			System.out.println("Error: " + e);
		}

	}
}
