package vision.VisionCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import vision.VisionClass.VisionMaster;
import vision.VisionControllers.VisionController;

/**
 *
 */
public class VisionPanTalonSRXAndTiltPWMSequentialCommand extends CommandGroup {

    public VisionPanTalonSRXAndTiltPWMSequentialCommand(VisionMaster VM, VisionController VC) {
    	addSequential(new VisionPanTalonSRXCommand(VM, VC));
    	addSequential(new VisionTiltPWMCommand(VM, VC));
    }
}
