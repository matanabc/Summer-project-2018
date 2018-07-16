package vision.VisionCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import vision.VisionClass.VisionControllerInterface;
import vision.VisionClass.VisionMaster;


public class VisionTalonSRXCommand extends Command {

	private VisionControllerInterface VC;
	private VisionMaster VM;
	private boolean panTtiltF;

	
	/**
	 *@param 
	 * VM	-		this need to be vision controller you use for pan or tilt.
	 * 
	 * @param 
	 * VC	-		this need to be object of VisionControllerInterface.
	 * 
	 * @param
	 * panTtiltF	-		this need to be true or false, true if you want it to pan and false if you want it to tilt. 
	 */
	public VisionTalonSRXCommand(VisionMaster VM, VisionControllerInterface VC, boolean panTtiltF) {

		this.VC = VC;
		this.VM = VM;
		this.panTtiltF = panTtiltF;

		requires(VC.getSubsystem());
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//SmartDashboard.putBoolean("on", true);
		if(panTtiltF) {
			pan();
		}else {
			tilt();
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		SmartDashboard.putBoolean("on", false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

	protected void pan() {
		VC.setOutput(VM != null ? VM.getTargetPosition().getTargetAngle() : 0);
		SmartDashboard.putNumber(VC.getSubsystem().toString() + " pid setpoint : " , VM != null ? VM.getTargetPosition().getTargetAngle() : 0);
	}

	protected void tilt() {
		VC.setOutput(VM != null ? VM.getTargetPosition().getTargetHeight() : 0);
		SmartDashboard.putNumber(VC.getSubsystem().toString() + " pid setpoint : " , VM != null ? VM.getTargetPosition().getTargetHeight() : 0);
	}
}
