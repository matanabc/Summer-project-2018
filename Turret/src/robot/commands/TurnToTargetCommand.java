package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

/**
 *
 */
public class TurnToTargetCommand extends Command {

    public TurnToTargetCommand() {
    	requires(Robot.driveSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSystem.setSetpointPIDTurn(Robot.vision.getAngleToTargetAndY().getAngleToTarget());
    	Robot.driveSystem.enablePIDTurn();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSystem.setSetpointPIDTurn(Robot.vision.getAngleToTargetAndY().getAngleToTarget());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSystem.disablePIDTurn();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
