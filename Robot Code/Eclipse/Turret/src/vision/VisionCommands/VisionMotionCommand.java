package vision.VisionCommands;

import MotionProfiling.MP_Classes.MP_Radius;
import MotionProfiling.MP_Commands.MP_DrivePathFollower;
import MotionProfiling.MP_Controllers.NavxTurnDriveController;
import MotionProfiling.PID_Classes.PID_Gains;
import MotionProfiling.PID_Classes.PID_Variables;
import edu.wpi.first.wpilibj.command.Command;
import robot.subsystems.DriveSystem;
import vision.VisionClass.VisionControllerInterface;
import vision.VisionClass.VisionMaster;

/**
 *
 */
public class VisionMotionCommand extends Command {
	
	private VisionControllerInterface VC;
	private VisionMaster VM;
	
	private boolean panTtiltF;

    public VisionMotionCommand(VisionMaster VM, VisionControllerInterface VC, boolean panTtiltF) {
        //requires(chassis);
    	
    	this.VC = VC;
		this.VM = VM;
		
		this.panTtiltF = panTtiltF;
		
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(panTtiltF) {
    		motionPan();
		}else {
			motionTilt();
		}
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
    }
    
    protected void motionPan() {
    	new MP_DrivePathFollower(NavxTurnDriveController.getInstance(), 
    							new MP_Radius(0, VM.getTargetPosition().getTargetAngle(), 2, 0, 0, 2, 2),
    							VC.getMPGains());
    }
    
    protected void motionTilt() {  	
    	new MP_DrivePathFollower(NavxTurnDriveController.getInstance(), 
				new MP_Radius(0, VM.getTargetPosition().getTargetHeight(), 2, 0, 0, 2, 2),
				VC.getMPGains());
    }
}
