/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot.commands.drive;

import MotionProfiling.MP_Constants;
import dashboard.Dashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.subsystems.DriveSystem;

public class DriveWithJoysticksAcc extends Command {

	private double time, dt, turn, speed, right, left, maxAdd, sl, sr;

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
    	time = Timer.getFPGATimestamp() ;
		dt = time - lastTime_;
		
		turn = getAxisValue(Robot.oi.AdelStick.getRawAxis(4), 0.02);//4
		speed = -getAxisValue(Robot.oi.AdelStick.getRawAxis(1), 0.02);

		maxAdd = (dt * MP_Constants.MAX_EXELRATION_ACC * 10) / MP_Constants.MAX_SPEED;

		left = speed + turn;
		right = speed - turn;
		
		if (Math.abs(left) > 1){
			left = left / Math.abs(left);
		}
		
		if (Math.abs(right) > 1){
			right = right / Math.abs(right);
		}
		
		sl = left - lastLeft_ > 0 ? 1 : -1;//turn / Math.abs(turn);
		sr = right - lastRight_ > 0 ? 1 : -1;
		
		left = Math.abs(left - lastLeft_) > maxAdd ? lastLeft_ + maxAdd * sl : left;
		right = Math.abs(right - lastRight_) > maxAdd ? lastRight_ + maxAdd * sr : right;

		DriveSystem.getInstance().tank(left, right);

		Dashboard.putNumber(dashboard.DashboardPanels.DRIVER_PANEL, "right", right);
		Dashboard.putNumber(dashboard.DashboardPanels.DRIVER_PANEL, "left", left);

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
	
	private double getAxisValue(double axisValue, double minValue){
		return Math.abs(axisValue) <= minValue ? 0 : axisValue;
	}
}

