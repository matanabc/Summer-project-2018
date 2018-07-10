package vision.VisionCommands;

import MotionProfiling.PID_Classes.PID_Gains;
import MotionProfiling.PID_Classes.PID_Variables;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import vision.VisionClass.VisionMaster;
import vision.VisionControllers.VisionController;

/**
 *
 */
public class VisionPanPWMCommand extends Command {

	private VisionController VC;
	private PID_Gains gains_;
	private PID_Variables pidV_;
	private VisionMaster VM;

	public VisionPanPWMCommand(VisionMaster VM, VisionController VC, PID_Gains gains, double maxOutput, double maxErr) {

		this.VC = VC;
		this.gains_ = gains;
		this.VM = VM;
		pidV_ = new PID_Variables();
		pidV_.maxOutput = maxOutput;
		pidV_.maxErr = maxErr;

		requires(VC.getPanSubsystem());
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		pidV_.resetVars();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		//pidV_.pos = system_ != null ? system_.getPosition() : 0; 
		pidV_.pos = VC.getPanSource();

		pidV_.error = VM != null ? VM.getAngleAndDistanceToTarget().getAngleToTarget() - pidV_.pos : 0;	
		pidV_.output =	pidV_.error * gains_.kp + pidV_.errorSum * gains_.ki + (pidV_.error - pidV_.lastError) * gains_.kd;	

		SmartDashboard.putNumber(VC.getPanSubsystem().toString() + " pid setpoint : " , VM != null ? VM.getAngleAndDistanceToTarget().getAngleToTarget() : 0);
		SmartDashboard.putNumber(VC.getPanSubsystem().toString() + " pid pos : ", pidV_.pos);
		
		SmartDashboard.putBoolean("VM is Null", VM == null);

		pidV_.errorSum += pidV_.error;

		if (pidV_.error > 0 ^ pidV_.lastError > 0){
			pidV_.errorSum = 0;
		}

		pidV_.lastError = pidV_.error;

		if (Math.abs(pidV_.output) > pidV_.maxOutput){
			pidV_.output = pidV_.maxOutput * (pidV_.output / Math.abs(pidV_.output));

		}

		VC.setPanOutput(pidV_.output);

		SmartDashboard.putNumber(VC.getPanSubsystem().toString() + " pid Error: = ", pidV_.error);
		SmartDashboard.putNumber(VC.getPanSubsystem().toString() + " pid Output: = ", pidV_.output);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		VC.setOutput(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
