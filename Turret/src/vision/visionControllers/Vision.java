package vision.visionControllers;

import robot.RobotMap;
import vision.visionHistoryClass.VisionHistory;

public class Vision extends VisionHistory{

	public Vision() {
		//Remember to add those values to RobotMap
		super(RobotMap.MAX_DECODER_HOSTORY_SIZE, RobotMap.CAMERA_ANGLE, RobotMap.CAMERA_WIDTH, RobotMap.NT_VALUE_NAME);
	}

	@Override
	public double addToHistory() {
		//example:
		//return turretSystem.sideEncoderPosition();
		//return driveSystem.getGyroAngle();
		return 0;
	}

}
