package vision.VisionCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import vision.VisionClass.VisionMaster;
import vision.VisionControllers.VisionController;

/**
 *
 */
public class VisionPanPWMAndTiltTalonSRXSequentialCommand extends CommandGroup {

	public VisionPanPWMAndTiltTalonSRXSequentialCommand(VisionMaster VM, VisionController VC, double maxOutputPan, double maxErrorPan) {
		addSequential(new VisionPanPWMCommand(VM, VC, Robot.driveSystem.getVisoinPanPIDGains(),
				maxOutputPan, maxErrorPan));

		addSequential(new VisionTiltTalonSRXCommand());
	}
}
