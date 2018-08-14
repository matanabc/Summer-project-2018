package MotionProfiling.MP_Controllers;

import robot.subsystems.DriveSystem;

public class NavxTurnDriveController extends MPDriveController{
	
	private static NavxTurnDriveController mInstance = new NavxTurnDriveController();
	
	private NavxTurnDriveController() {
		
	}
	
	public static NavxTurnDriveController getInstance() {
		return mInstance;
	}

	@Override
	public MPDoubleSidePos getPosition() {
		//double distance = (DriveSystem.getInstance().getLeftEncoderDistance() + DriveSystem.getInstance().getRightEncoderDistance()) / 2;
		//double SensorAngle =  Math.toRadians(Robot.climbSystem.GetYaw());
		
		//double diffrence = (SensorAngle) * RobotMap.ROBOT_WIDTH;
		
		MPDoubleSidePos pos = new MPDoubleSidePos();
		
		//pos.left = distance - (diffrence / 2);
		//pos.right = distance + (diffrence / 2);
		
		pos.left = Math.toRadians(DriveSystem.getInstance().getAngleNavx());
		pos.right = Math.toRadians(DriveSystem.getInstance().getAngleNavx());
		
		return pos;
	}

	@Override
	public void reset() {
		// reset only the incoders

	}

}
