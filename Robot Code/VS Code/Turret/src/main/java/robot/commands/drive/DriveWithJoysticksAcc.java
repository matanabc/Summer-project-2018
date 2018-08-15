/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot.commands.drive;

import MotionProfiling.MP_Constants;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.subsystems.DriveSystem;

public class DriveWithJoysticksAcc extends Command {

	double rightSpeed_, leftSpeed_;
	private double lastLeft_, lastRight_, lastTime_;
	
    public DriveWithJoysticksAcc() {
    	requires(DriveSystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lastLeft_ = 0;
		lastRight_ = 0;
		lastTime_ = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double time = Timer.getFPGATimestamp() ;
		double dt = time - lastTime_;
		
		double turn = Robot.oi.AdelStick.getRawAxis(4);//4
		double speed = -Robot.oi.AdelStick.getRawAxis(1);

		double right = 0;
		double left = 0;

		if(Math.abs(turn) > 0.015 || Math.abs(speed) > 0.015 || Math.abs(lastRight_) > 0.05 || Math.abs(lastLeft_) > 0.05){
			
			if (Math.abs(speed) < 0.1){
				turn *= 0.6;
			}else{
				turn *= 0.7;			
			}
			
			double maxAdd = (dt * MP_Constants.MAX_EXELRATION_ACC * 8 /*10*/) / MP_Constants.MAX_SPEED;
			left = speed + turn;
			right = speed - turn;
			
			if (Math.abs(left) > 1){
				left = left / Math.abs(left);
			}
			
			if (Math.abs(right) > 1){
				right = right / Math.abs(right);
			}
			
			double sl = left - lastLeft_ > 0 ? 1 : -1;//turn / Math.abs(turn);
			double sr = right - lastRight_ > 0 ? 1 : -1;
			
			left = Math.abs(left - lastLeft_) > maxAdd ? lastLeft_ + maxAdd * sl : left;
			right = Math.abs(right - lastRight_) > maxAdd ? lastRight_ + maxAdd * sr : right;
			
			//SmartDashboard.putNumber("left Speed", left);
			//double right = Math.abs(speed - turn - lastRight_) > maxAdd ? speed - maxAdd * s : speed - turn;
		
			DriveSystem.getInstance().tank(left, right);
		} else {
			DriveSystem.getInstance().tank(0, 0);
		}

		lastLeft_ = left;
		lastRight_ = right;
		lastTime_ = time;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	DriveSystem.getInstance().tank(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

