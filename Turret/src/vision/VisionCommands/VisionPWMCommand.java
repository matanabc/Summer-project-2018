package vision.VisionCommands;

import MotionProfiling.PID_Classes.PID_Gains;
import MotionProfiling.PID_Classes.PID_Variables;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import vision.VisionClass.VisionControllerInterface;
import vision.VisionClass.VisionMaster;

/**
 *
 */
public class VisionPWMCommand extends Command {

	private VisionControllerInterface VC;
	private PID_Gains gains_;
	private PID_Variables pidV_;
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
	public VisionPWMCommand(VisionMaster VM, VisionControllerInterface VC, boolean panTTiltF) {

		this.VC = VC;
		this.gains_ = VC.getGains();
		this.VM = VM;
		this.panTtiltF = panTTiltF;

		pidV_ = new PID_Variables();
		pidV_.maxOutput = VC.getMaxOutput();
		pidV_.maxErr = VC.getMaxerror();

		requires(VC.getSubsystem());
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		pidV_.resetVars();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
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
		VC.setOutput(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

	protected void pan() {
		//pidV_.pos = system_ != null ? system_.getPosition() : 0; 
		pidV_.pos = VC.getSource();

		pidV_.error = VM != null ? VM.getTargetPosition().getTargetAngle()- pidV_.pos : 0;	
		pidV_.output =	pidV_.error * gains_.kp + pidV_.errorSum * gains_.ki + (pidV_.error - pidV_.lastError) * gains_.kd;	

		SmartDashboard.putNumber(VC.getSubsystem().toString() + " pid setpoint : " , VM != null ? VM.getTargetPosition().getTargetAngle() : 0);
		SmartDashboard.putNumber(VC.getSubsystem().toString() + " pid pos : ", pidV_.pos);

		SmartDashboard.putBoolean("VM is Null", VM == null);

		pidV_.errorSum += pidV_.error;

		if (pidV_.error > 0 ^ pidV_.lastError > 0){
			pidV_.errorSum = 0;
		}

		pidV_.lastError = pidV_.error;

		if (Math.abs(pidV_.output) > pidV_.maxOutput){
			pidV_.output = pidV_.maxOutput * (pidV_.output / Math.abs(pidV_.output));

		}

		VC.setOutput(pidV_.output);

		SmartDashboard.putNumber(VC.getSubsystem().toString() + " pid Error: = ", pidV_.error);
		SmartDashboard.putNumber(VC.getSubsystem().toString() + " pid Output: = ", pidV_.output);
	}

	protected void tilt() {
		//pidV_.pos = system_ != null ? system_.getPosition() : 0; 
		pidV_.pos = VC.getSource();

		pidV_.error = VM != null ? VM.getTargetPosition().getTargetHeight() - pidV_.pos : 0;	
		pidV_.output =	pidV_.error * gains_.kp + pidV_.errorSum * gains_.ki + (pidV_.error - pidV_.lastError) * gains_.kd;	

		SmartDashboard.putNumber(VC.getSubsystem().toString() + " pid setpoint : " , VM != null ? VM.getTargetPosition().getTargetHeight() : 0);
		SmartDashboard.putNumber(VC.getSubsystem().toString() + " pid pos : ", pidV_.pos);

		//SmartDashboard.putBoolean("VM is Null", VM == null);

		pidV_.errorSum += pidV_.error;

		if (pidV_.error > 0 ^ pidV_.lastError > 0){
			pidV_.errorSum = 0;
		}

		pidV_.lastError = pidV_.error;

		if (Math.abs(pidV_.output) > pidV_.maxOutput){
			pidV_.output = pidV_.maxOutput * (pidV_.output / Math.abs(pidV_.output));

		}

		VC.setOutput(pidV_.output);

		SmartDashboard.putNumber(VC.getSubsystem().toString() + " pid Error: = ", pidV_.error);
		SmartDashboard.putNumber(VC.getSubsystem().toString() + " pid Output: = ", pidV_.output);
	}
}
