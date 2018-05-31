
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.BlockingQueue;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class EmptyConsumer implements Runnable{

	protected BlockingQueue<Mat> queue = null;
	
	public EmptyConsumer(BlockingQueue<Mat> queue) {
		//creating camera object 
		
		this.queue = queue;
	}


	public void run() {


		Calendar cal = Calendar.getInstance();
		long time = cal.getTimeInMillis();
		int c=0;
		
			while(true){
			try {
				Mat inputImage = this.queue.take();

				//print fps
				if (Calendar.getInstance().getTimeInMillis() - time > 1000) {
					time = Calendar.getInstance().getTimeInMillis();
					System.out.println(Thread.currentThread().getName() + " fps: " + c);
					c = 0;
				}else {
					c++;
				}

				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
