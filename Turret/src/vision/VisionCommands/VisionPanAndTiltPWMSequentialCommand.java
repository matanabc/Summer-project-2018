package vision.VisionCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import vision.VisionClass.VisionMaster;
import vision.VisionControllers.VisionController;

/**
 *
 */
public class VisionPanAndTiltPWMSequentialCommand extends CommandGroup {

    public VisionPanAndTiltPWMSequentialCommand(VisionMaster VM, VisionController VC) {
    	
       addSequential(new VisionPanPWMCommand(VM, VC));
       
       addSequential(new VisionTiltPWMCommand(VM, VC));
    }
}