package robot.commands.TurretCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import vision.VisionClass.VisionMaster;
import vision.VisionCommands.VisionTalonSRXCommand;
import vision.VisionControllers.VisionControllerInterface;

/**
 *
 */
public class PanTiltAndShootCommand extends CommandGroup {

    public PanTiltAndShootCommand(VisionMaster VM, VisionControllerInterface VCPan,
    								VisionControllerInterface VCTilt, double maxErrorToShoot) {
       
    	addParallel(new VisionTalonSRXCommand(VM, VCPan));
        addParallel(new VisionTalonSRXCommand(VM, VCTilt));
        addSequential(new ShootCommand(VM, maxErrorToShoot));
    }
}
