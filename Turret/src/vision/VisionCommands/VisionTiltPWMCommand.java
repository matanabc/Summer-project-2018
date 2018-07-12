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
public class VisionTiltPWMCommand extends Command {
	
	private VisionController VC;
	private PID_Gains gains_;
	private PID_Variables pidV_;
	private VisionMaster VM;
	private String chose;

	public VisionTiltPWMCommand(VisionMaster VM, VisionController VC, String chose) {
		
		this.VC = VC;
		this.gains_ = VC.getTiltGains();
		this.VM = VM;
		this.chose = chose;
		
		pidV_ = new PID_Variables();
		pidV_.maxOutput = VC.tiltMaxOutput();
		pidV_.maxErr = VC.tiltMaxerror();
		
		requires(VC.getTiltSubsystem());
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		pidV_.resetVars();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		pidV_.pos = VC.getTiltSource();

		pidV_.error = VM != null ? targetHightMath(VM.getAngleAndDistanceToTarget().getPixelHeightToTarget()) - pidV_.pos : 0;	
		pidV_.output =	pidV_.error * gains_.kp + pidV_.errorSum * gains_.ki + (pidV_.error - pidV_.lastError) * gains_.kd;	

		SmartDashboard.putNumber(VC.getTiltSubsystem().toString() + " pid setpoint : " , VM != null ? VM.getAngleAndDistanceToTarget().getPixelHeightToTarget() : 0);
		
		SmartDashboard.putBoolean("VM is Null", VM == null);

		pidV_.errorSum += pidV_.error;

		if (pidV_.error > 0 ^ pidV_.lastError > 0){
			pidV_.errorSum = 0;
		}

		pidV_.lastError = pidV_.error;

		if (Math.abs(pidV_.output) > pidV_.maxOutput){
			pidV_.output = pidV_.maxOutput * (pidV_.output / Math.abs(pidV_.output));

		}

		VC.setTiltOutput(pidV_.output);

		SmartDashboard.putNumber(VC.getTiltSubsystem().toString() + " pid Error: = ", pidV_.error);
		SmartDashboard.putNumber(VC.getTiltSubsystem().toString() + " pid Output: = ", pidV_.output);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		VC.setTiltOutput(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
	
	protected double targetHightMath(double targetHight) {
		if(chose.equals("RPM")) {
			return VC.TargetHightToRPM(targetHight);
			
		}else if (chose.equals("Distance")) {
			return VC.TargetHightToDistance(targetHight);
			
		}else if (chose.equals("Angle")) {
			return VC.TargetHightToAngle(targetHight);
		}else {
			return 0;
		}
	}
}
