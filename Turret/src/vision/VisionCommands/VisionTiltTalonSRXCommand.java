package vision.VisionCommands;

import edu.wpi.first.wpilibj.command.Command;
import vision.VisionClass.VisionMaster;
import vision.VisionControllers.VisionController;

/**
 *
 */
public class VisionTiltTalonSRXCommand extends Command {
	
	private VisionController VC;
	private VisionMaster VM;

    public VisionTiltTalonSRXCommand(VisionMaster VM, VisionController VC) {
    	this.VC = VC;
    	this.VM = VM;
    	
    	requires(VC.getTiltSubsystem());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	VC.setTiltOutput(VM != null ? VM.getAngleAndDistanceToTarget().getPixelHeightToTarget() : 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
