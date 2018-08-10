package robot.commands.TurretCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import vision.VisionClass.VisionControllerInterface;
import vision.VisionClass.VisionMaster;
import vision.VisionCommands.VisionPIDCommand;

/**
 *
 */
public class PanTiltAndShootCommand extends CommandGroup {

    public PanTiltAndShootCommand(VisionMaster VM, VisionControllerInterface VCPan, VisionControllerInterface VCTilt,
    								VisionControllerInterface VCShoot) {
       
    	addParallel(new VisionPIDCommand(VM, VCPan, true, false));
        addParallel(new VisionPIDCommand(VM, VCTilt, false, false));
        addSequential(new VisionPIDCommand(VM, VCShoot, false, false));
    }
}
