package robot.commands.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

/**
 *
 */
public class ResetTalonsEncodersCommand extends Command {

    public ResetTalonsEncodersCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooterSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.shooterSystem.resetTalonsEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.shooterSystem.resetTalonsEncoders();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
