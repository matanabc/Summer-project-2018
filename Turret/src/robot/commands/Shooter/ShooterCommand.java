package robot.commands.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;

public class ShooterCommand extends Command {

	double sideSpeed;
	double shooterSpeed;
	double lastTime;
	double wantedPosition;
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
		wantedPosition = 0;

		Robot.shooterSystem.setSideToStay();
		Robot.shooterSystem.addEncoderPoditionToHistory();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		String [] TargetInfo = SmartDashboard.getString("TargetInfo", "0|").split(";");
		/*
		int i;
		for(i = 0; i < TargetInfo.length; i++){
			String num = String.valueOf(i);
			SmartDashboard.putString(num , TargetInfo[i]);
		}
		SmartDashboard.putNumber("length", TargetInfo.length);
		/*
       	if(i ==4){
       		wantedPosition = Robot.shooterSystem.getSideEncoderPosition() - ((Double.parseDouble(TargetInfo[1]) - 160) * 0.375) ;//+  Robot.shooterSystem.getSideEncoderPosition();//
       	}
       	SmartDashboard.putNumber("wantedPosition", wantedPosition);

       	//}
       	sideSpeed = -(wantedPosition - Robot.shooterSystem.getSideEncoderPosition()) * 0.01;

       	SmartDashboard.putNumber("p", sideSpeed);*/

		Robot.shooterSystem.addEncoderPoditionToHistory();

		if(TargetInfo.length == 3){
			if(Long.parseLong(TargetInfo[2]) > lastTime){
				lastTime = Double.parseDouble(TargetInfo[2]);
				//wantedPosition = /*Robot.shooterSystem.getEncoderPositionFromTime((long) lastTime)*/Robot.shooterSystem.getSideEncoderPosition() - ((Double.parseDouble(TargetInfo[0]) - 160) * 0.375);
				wantedPosition = Robot.shooterSystem.getEncoderPositionFromTime(Long.parseLong(TargetInfo[2])) - ((Double.parseDouble(TargetInfo[0]) - 160) * 0.375);
			}
		}
		
		if(wantedPosition > 90){
			wantedPosition = 90;
		}else if(wantedPosition < -90){
			wantedPosition = -90;
		}

		//sideSpeed = -(wantedPosition - Robot.shooterSystem.getSideEncoderPosition()) * RobotMap.KP_SIDE;

		Robot.shooterSystem.setShootRPM(-Robot.oi.AdelStick.getRawAxis(5));
		SmartDashboard.putNumber("Wanted Position", wantedPosition);
		Robot.shooterSystem.setSideMotorSetPoint(wantedPosition);

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