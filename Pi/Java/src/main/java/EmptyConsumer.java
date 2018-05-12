
import java.util.Calendar;
import java.util.concurrent.BlockingQueue;

import org.opencv.core.Mat;

public class EmptyConsumer implements Runnable{

	protected BlockingQueue<MatTime> queue = null;
	
	public EmptyConsumer(BlockingQueue<MatTime> queue) {
		//creating camera object 
		this.queue = queue;
	}


	public void run() {


		Calendar cal = Calendar.getInstance();
		long time = cal.getTimeInMillis();
		int c=0;
		
			while(true){
			try {
				Mat inputImage = this.queue.take().getMat();

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
