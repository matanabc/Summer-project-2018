package vision.VisionCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import vision.VisionClass.VisionMaster;
import vision.VisionControllers.VisionController;

/**
 *
 */
public class VisionPanPWMAndTiltTalonSRXSequentialCommand extends CommandGroup {

	public VisionPanPWMAndTiltTalonSRXSequentialCommand(VisionMaster VM, VisionController VC) {
		addSequential(new VisionPanPWMCommand(VM, VC));

		addSequential(new VisionTiltTalonSRXCommand(VM, VC));
	}
}
