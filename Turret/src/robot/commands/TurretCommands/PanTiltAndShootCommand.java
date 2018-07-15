package robot.commands.TurretCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import vision.VisionClass.VisionControllerInterface;
import vision.VisionClass.VisionMaster;
import vision.VisionCommands.VisionTalonSRXCommand;

/**
 *
 */
public class PanTiltAndShootCommand extends CommandGroup {

    public PanTiltAndShootCommand(VisionMaster VM, VisionControllerInterface VCPan,
    								VisionControllerInterface VCTilt, double maxErrorToShoot) {
       
    	addParallel(new VisionTalonSRXCommand(VM, VCPan, true));
        addParallel(new VisionTalonSRXCommand(VM, VCTilt, false));
        addSequential(new ShootCommand(VM, maxErrorToShoot, VCPan));
    }
}
