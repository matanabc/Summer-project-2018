package vision.VisionCommands;

import MotionProfiling.MP_Classes.MPGains;
import MotionProfiling.MP_Classes.MP_Path;
import MotionProfiling.PID_Classes.Setpoint;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import vision.VisionClass.VisionControllerInterface;
import vision.VisionClass.VisionMaster;

/**  
  * @author Matan Steinmetz.
  */
public class VisionMotionCommand extends Command {
	
	private VisionControllerInterface VC;
	//private VisionMaster VM;
	
	private boolean panTtiltF;

	private MP_Path motion_;
	private double 	startingTime_, time_;
	private MPGains gains_;
	private double errorSum_, lastError_;
	private double position_, Vmax_, Vend_, acc_;
	private double startingPos_;
	
	private boolean end = false;

	public VisionMotionCommand(VisionMaster VM, VisionControllerInterface VC, int sum, double Vmax, double Vend, double acc) {
		
		this.VC = VC;
		//this.VM = VM;
		
		requires(VC.getSubsystem());

		if(panTtiltF) {
			position_ = VM.getTargetPosition().getTargetAngle();
		}else {
			position_ = VM.getTargetPosition().getTargetHeight();
		}
	
		Vmax_ = Math.abs(Vmax);
		Vend_ = Math.abs(Vend);
		acc_ = acc;
		gains_ = VC.getMPGains();
	}

	@Override
	protected void initialize() {
		end = false;
		
		//Setpoint currState = system_.getCurrState();

		Setpoint currState = new Setpoint(VC.getSource(), 0, 0); 
				
		startingPos_ = VC.getSource();
		double distance = position_ - startingPos_;

		if (distance < 0){
			motion_ = new MP_Path(distance, -Vmax_, currState.vel, -Vend_, acc_, acc_);
		} else{
			motion_ = new MP_Path(distance, Vmax_, currState.vel, Vend_, acc_, acc_);
		}
		
		if (distance == 0){
			end = true; 
		}

		startingTime_ = Timer.getFPGATimestamp();
		errorSum_ = 0;
		lastError_ = 0;
		time_ = 0;
	}

	@Override
	protected void execute() {
		time_ = Timer.getFPGATimestamp() - startingTime_;

		Setpoint setpoint = motion_.getCurrentState(time_);

		double error = (startingPos_ + setpoint.pos) - VC.getSource();

		double output = gains_.kv * setpoint.vel + gains_.ka * setpoint.acc +
				error * gains_.kp + errorSum_ * gains_.ki + (error - lastError_) * gains_.kd;

		errorSum_ += error;

		if (error > 0 ^ lastError_ > 0){
			errorSum_ = 0;
		}

		lastError_ = error;

		/*
		if(output > maxSpeed_ || output < -maxSpeed_){

			output = maxSpeed_ * (output / Math.abs(output));
		}*/
		
		VC.setOutput(output);

		/*SmartDashboard.putNumber(system_.toString() + " output MP:", output);
		SmartDashboard.putNumber(system_.toString() + " error MP:", error);
		SmartDashboard.putNumber(system_.toString() + " setpoint.pos + startingPos", (startingPos_ + setpoint.pos));
		SmartDashboard.putNumber(system_.toString() + " setpoint.pos", setpoint.pos);*/

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return time_ >= motion_.getTotalTime() || end;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		time_ = Timer.getFPGATimestamp() - startingTime_;
		//system_.setCurrState(motion_.getCurrentState(time_));
		
		Setpoint tmp = motion_.getCurrentState(time_);
		tmp.pos = position_;
		//system_.setCurrState(tmp);

		//SmartDashboard.putNumber("motion curr state", motion_.getCurrentState(time_).pos);

		VC.setOutput(0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}
}
