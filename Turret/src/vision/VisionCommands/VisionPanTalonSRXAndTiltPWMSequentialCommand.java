package vision.VisionCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import vision.VisionClass.VisionMaster;
import vision.VisionControllers.VisionController;

/**
 *
 */
public class VisionPanTalonSRXAndTiltPWMSequentialCommand extends CommandGroup {

    public VisionPanTalonSRXAndTiltPWMSequentialCommand(VisionMaster VM, VisionController VC, double maxOutputPan, double maxErrorPan) {
    	addSequential(new VisionPanTalonSRXCommand(VM, VC));
    	addSequential(new VisionTiltPWMCommand());
    }
}
