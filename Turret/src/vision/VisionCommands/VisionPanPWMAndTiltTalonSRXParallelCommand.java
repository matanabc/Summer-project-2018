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
public class VisionPanPWMAndTiltTalonSRXParallelCommand extends Command {

	private VisionController VC;
	private VisionMaster VM;

	private PID_Gains panGains_;
	private PID_Variables panPIDV_;

	public VisionPanPWMAndTiltTalonSRXParallelCommand(VisionMaster VM, VisionController VC) {
		this.VC = VC;
		this.VM = VM;

		this.panGains_ = VC.getPanGains();
		panPIDV_ = new PID_Variables();
		panPIDV_.maxOutput = VC.panMaxOutput();
		panPIDV_.maxErr = VC.panMaxerror();

		requires(VC.getPanSubsystem());
		requires(VC.getTiltSubsystem());
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		panPIDV_.resetVars();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//pidV_.pos = system_ != null ? system_.getPosition() : 0; 
		panPIDV_.pos = VC.getPanSource();

		panPIDV_.error = VM != null ? VM.getAngleAndDistanceToTarget().getAngleToTarget() - panPIDV_.pos : 0;	
		panPIDV_.output =	panPIDV_.error * panGains_.kp + panPIDV_.errorSum * panGains_.ki + (panPIDV_.error - panPIDV_.lastError) * panGains_.kd;	

		SmartDashboard.putNumber(VC.getPanSubsystem().toString() + " pid setpoint : " , VM != null ? VM.getAngleAndDistanceToTarget().getAngleToTarget() : 0);
		SmartDashboard.putNumber(VC.getPanSubsystem().toString() + " pid pos : ", panPIDV_.pos);

		SmartDashboard.putBoolean("VM is Null", VM == null);

		panPIDV_.errorSum += panPIDV_.error;

		if (panPIDV_.error > 0 ^ panPIDV_.lastError > 0){
			panPIDV_.errorSum = 0;
		}

		panPIDV_.lastError = panPIDV_.error;

		if (Math.abs(panPIDV_.output) > panPIDV_.maxOutput){
			panPIDV_.output = panPIDV_.maxOutput * (panPIDV_.output / Math.abs(panPIDV_.output));

		}

		VC.setPanOutput(panPIDV_.output);
		VC.setTiltOutput(VM != null ? VM.getAngleAndDistanceToTarget().getPixelHeightToTarget() : 0);
    	
		SmartDashboard.putNumber(VC.getTiltSubsystem().toString() + " pid setpoint : " , VM != null ? VM.getAngleAndDistanceToTarget().getPixelHeightToTarget() : 0);

		SmartDashboard.putNumber(VC.getPanSubsystem().toString() + " pid Error: = ", panPIDV_.error);
		SmartDashboard.putNumber(VC.getPanSubsystem().toString() + " pid Output: = ", panPIDV_.output);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		VC.setPanOutput(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
