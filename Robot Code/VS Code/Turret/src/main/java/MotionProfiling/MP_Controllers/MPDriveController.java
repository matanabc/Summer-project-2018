package MotionProfiling.MP_Controllers;

import robot.subsystems.DriveSystem;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class MPDriveController {

	public void setOutput(double left, double right){
		//SmartDashboard.putNumber("encoder engle: ", Robot.driveSystem.getEncodersAngle());
		DriveSystem.getInstance().tank(left, right);
	}
	
	public abstract MPDoubleSidePos getPosition();
	public abstract void reset(); 
}
