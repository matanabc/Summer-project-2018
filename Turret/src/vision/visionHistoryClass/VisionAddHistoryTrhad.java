package vision.visionHistoryClass;

import robot.Robot;

public class VisionAddHistoryTrhad  implements Runnable{

	@Override
	public void run() {
		while(true){
			Robot.vision.addEncoderPositionToHistory();
		}
	}

}
