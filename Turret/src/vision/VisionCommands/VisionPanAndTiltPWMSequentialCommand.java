package vision.VisionCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import vision.VisionClass.VisionMaster;
import vision.VisionControllers.VisionController;

/**
 *
 */
public class VisionPanAndTiltPWMSequentialCommand extends CommandGroup {

    public VisionPanAndTiltPWMSequentialCommand(VisionMaster VM, VisionController VC, double maxOutput, double maxError) {
       addSequential(new VisionPanPWMCommand(VM, VC, Robot.driveSystem.getVisoinPanPIDGains(),
    		   									maxOutput, maxError));
       
       addSequential(new VisionTiltPWMCommand());
    }
}