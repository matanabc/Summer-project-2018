
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Main {

	public static void main(String[] args) {

		try {
			
			System.out.println("Starting! ");
			
			// Loads our OpenCV library. This MUST be included
			System.loadLibrary("opencv_java310");

			// Connect NetworkTables, and get access to the publishing table
			NetworkTable.setClientMode();
			NetworkTable.setIPAddress("roborio-3211-FRC.local");//ip of the robot
			NetworkTable.initialize();
			NetworkTable VisionTable = NetworkTable.getTable("SmartDashboard");

			VisionTable.putBoolean("pi connect", true);//Upload to the robot that he connect to him 
			
			BlockingQueue<MatTime> queue = new ArrayBlockingQueue<MatTime>(1);//Queue for all the frmes whe collect
			
			//taking frames from the camera and pot them to the Queue 
			FrameProducer producer = new FrameProducer(0, queue);
			
			//taking the frame from the Queue and do vision processing on them 
			EmptyConsumer consumer = new EmptyConsumer(queue);
			//FrameConsumer consumer1 = new FrameConsumer(queue);
			//FrameConsumer consumer2 = new FrameConsumer(queue);
			
			//making them to work in Thread
			new Thread(producer).start();
			new Thread(consumer).start();
			//new Thread(consumer1).start();
			//new Thread(consumer2).start();
			
		}catch (Exception e) {
			System.out.println("Error: " + e);
		}

	}
}
