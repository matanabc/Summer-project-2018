package vision.visionControllers;

import robot.Robot;
import robot.RobotMap;
import vision.visionHistoryClass.VisionHistory;

public class Vision extends VisionHistory{

	public Vision() {
		//Remember to add those values to RobotMap!!
		
		super(RobotMap.MAX_DECODER_HOSTORY_SIZE, RobotMap.CAMERA_ANGLE,
			  RobotMap.CAMERA_WIDTH, RobotMap.NT_VALUE_NAME, RobotMap.OFFSEAT_CAMERA_FROM_CENTER);
		
		super.startAddToHistoryTrhad();
	}

	@Override
	protected double addToHistory() {
		
		/*	
		 * 	it cold be what you want, for example:
		 * 	return turretSystem.sideEncoderPosition();
		 * 	return driveSystem.getGyroAngle();
		*/
	
		return Robot.driveSystem.getAngleNavx();
	}

	@Override
	protected double targetHeightToDistance(double targetHeight) {
		
		/*
		 *	it cold be what you want, for example:
		 *	it can return the RPM or distance, but you need to write for the function.
		 *
		 *	it should be something like that but you can do it different:
		 *	targetHeight * 2 - targetHeight;  
		*/
		
		return targetHeight;
	}
}
