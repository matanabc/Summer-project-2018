package robot.commands.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.RobotMap;

public class ShooterCommand extends Command {

	double sideSpeed;
	double shooterSpeed;
	double lastTime;
	double errorAngle;
	double integral;
	double count;
	double time;

	public ShooterCommand(double speed) {
		requires(Robot.shooterSystem);
		sideSpeed = speed;
		integral = 0;
		count = 0;
		time = 0;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		shooterSpeed = 0;
		lastTime = 0.0;
		errorAngle = 0;

		Robot.shooterSystem.setSideToStay();

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		String [] TargetInfo = SmartDashboard.getString("TargetInfo", "0|").split(";");

		//if(Double.parseDouble(TargetInfo[3]) > lastTime){
		//lastTime = Double.parseDouble(TargetInfo[3]);
		int i;
		for(i = 0; i < TargetInfo.length; i++){
			String num = String.valueOf(i);
			SmartDashboard.putString(num , TargetInfo[i]);
		}
		SmartDashboard.putNumber("length", TargetInfo.length);
		/*
       	if(i ==4){
       		errorAngle = Robot.shooterSystem.getSideEncoderPosition() - ((Double.parseDouble(TargetInfo[1]) - 160) * 0.375) ;//+  Robot.shooterSystem.getSideEncoderPosition();//
       	}
       	SmartDashboard.putNumber("errorAngle", errorAngle);

       	//}
       	sideSpeed = -(errorAngle - Robot.shooterSystem.getSideEncoderPosition()) * 0.01;

       	SmartDashboard.putNumber("p", sideSpeed);*/

		Robot.shooterSystem.addEncoderPoditionToHistory();

		if(TargetInfo.length == 3){
			if(Long.parseLong(TargetInfo[2]) > lastTime){
				lastTime = Double.parseDouble(TargetInfo[2]);
				errorAngle = /*Robot.shooterSystem.getEncoderPositionFromTime((long) lastTime)*/Robot.shooterSystem.getSideEncoderPosition() - ((Double.parseDouble(TargetInfo[0]) - 160) * 0.375);
			}
		}

		//sideSpeed = -(errorAngle - Robot.shooterSystem.getSideEncoderPosition()) * RobotMap.KP_SIDE;

		Robot.shooterSystem.setShootRPM(-Robot.oi.AdelStick.getRawAxis(5));
		Robot.shooterSystem.setSideMotorSetPoint(errorAngle);

		/*if (Calendar.getInstance().getTimeInMillis() - time > 1000) {
			time = Calendar.getInstance().getTimeInMillis();
			System.out.println("loop times: " + count);
			count = 0;
		}else {
			count++;
		}*/

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.shooterSystem.setShootRPM(0);
		Robot.shooterSystem.setSideToStay();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
