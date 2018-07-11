package vision.VisionCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import vision.VisionClass.VisionMaster;
import vision.VisionControllers.VisionController;

/**
 *
 */
public class VisionPanAndTiltTalonSRXSequentialCommand extends CommandGroup {

    public VisionPanAndTiltTalonSRXSequentialCommand(VisionMaster VM, VisionController VC) {
    	addSequential(new VisionPanTalonSRXCommand(VM, VC));
    	addSequential(new VisionTiltTalonSRXCommand(VM, VC));
    }
}
