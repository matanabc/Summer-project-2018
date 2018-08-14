package robot.commands.TurretCommands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.subsystems.Turret.TurretPanSystem;

/**
 *
 */
public class ResetTalonsEncodersCommand extends Command {

    public ResetTalonsEncodersCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(TurretPanSystem.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		TurretPanSystem.getInstance().resetTalon();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		TurretPanSystem.getInstance().resetTalon();
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
