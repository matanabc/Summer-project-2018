package robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.drive.DriveWithJoysticks;


public class DriveSystem extends Subsystem{

	private VictorSP leftMotor_ = new VictorSP(RobotMap.DRIVE_LEFT_PWM);
	private VictorSP rightMotor_ = new VictorSP(RobotMap.DRIVE_RIGHT_PWM);
	
	private PIDController leftMotorsPID;
	private PIDController rightMotorsPID;

	private DifferentialDrive driveSystem_ = new DifferentialDrive(leftMotor_, rightMotor_);
	
	public DriveSystem() {
		this.leftMotorsPID = new PIDController(0, 0, 0, null, this.leftMotor_);
		this.rightMotorsPID = new PIDController(0, 0, 0, null, this.rightMotor_);
		disablePIDTurn();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DriveWithJoysticks());
	}

	public void arcade(double speed, double turn){
		driveSystem_.arcadeDrive(speed, turn);
		
		SmartDashboard.putNumber("Right Output: !!!!!!!!!", rightMotor_.get());
		SmartDashboard.putNumber("Left Output: !!!!!!!!!", leftMotor_.get());
	}

	public void tank(double left, double right){
		rightMotor_.set(-right);
		leftMotor_.set(left);
		
		SmartDashboard.putNumber("Right Output: !!!!!!!!!", -right);
		SmartDashboard.putNumber("Left Output: !!!!!!!!!", left);
	}
	
	public void enablePIDTurn() {
		leftMotorsPID.reset();
		rightMotorsPID.reset();
		
		leftMotorsPID.enable();
		rightMotorsPID.enable();
	}
	
	public void disablePIDTurn() {
		leftMotorsPID.disable();
		rightMotorsPID.disable();
	}
	
	public void setSetpointPIDTurn(double setpoint) {
		leftMotorsPID.setSetpoint(setpoint);
		rightMotorsPID.setSetpoint(setpoint);
	}
}
