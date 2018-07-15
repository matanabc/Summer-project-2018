package robot.commands.TurretCommands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import vision.VisionClass.VisionControllerInterface;
import vision.VisionClass.VisionMaster;

/**
 *
 */
public class ShootCommand extends Command {

	protected VisionMaster VM;
	protected double maxError;
	protected VisionControllerInterface VCPan;
	
	public ShootCommand(VisionMaster VM, double maxError, VisionControllerInterface VCPan) {
		requires(Robot.turretShooterSystem);
		
		this.VM = VM;
		this.maxError = maxError;
		this.VCPan = VCPan;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.turretShooterSystem.resetTalon();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
				
		if(Math.abs(VM.getTargetPosition().getTargetAngle()- VCPan.getSource()) <= maxError) {
			Robot.turretShooterSystem.setShootRPM(VM.getTargetPosition().getTargetHeight());
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.turretShooterSystem.setShootRPM(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
