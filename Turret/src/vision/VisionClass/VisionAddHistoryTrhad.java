package vision.VisionClass;

import robot.Robot;

public class VisionAddHistoryTrhad  implements Runnable{

	@Override
	public void run() {
		while(true){
			//Robot.vision.addEncoderPositionToHistory();
			Robot.VM.addPanAndTiltPositionToHistory();
		}
	}

}
