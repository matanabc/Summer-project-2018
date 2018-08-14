package robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import MotionProfiling.MP_Classes.MPGains;
import MotionProfiling.PID_Classes.PID_Gains;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.RobotMap;
import robot.commands.drive.DriveWithJoystickCommanWrite;
import robot.commands.drive.DriveWithJoysticksAcc;


public class DriveSystem extends Subsystem{

	private VictorSP leftMotor_ = new VictorSP(RobotMap.DRIVE_LEFT_PWM);
	private VictorSP rightMotor_ = new VictorSP(RobotMap.DRIVE_RIGHT_PWM);
	
	//private PIDController leftMotorsPID;
	//private PIDController rightMotorsPID;

	private DifferentialDrive driveSystem_ = new DifferentialDrive(leftMotor_, rightMotor_);
	
	private AHRS navX;
	
	//private PID_Gains visionGains = new PID_Gains(0.05, 0, 0.3);
	
	private static DriveSystem mInstance = new DriveSystem();
	
	private DriveSystem() {
		CreatNavX();
		
		/*this.leftMotorsPID = new PIDController(0.1, 0, 0, navX, this.leftMotor_);
		this.rightMotorsPID = new PIDController(0.1, 0, 0, navX, this.rightMotor_);
		this.leftMotorsPID.setOutputRange(-0.3, 0.3);
		this.rightMotorsPID.setOutputRange(-0.3, 0.3);
		disablePIDTurn();*/
	}
	
	public static DriveSystem getInstance() {
		return mInstance;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticksAcc());
	}

	public void arcade(double speed, double turn){
		driveSystem_.arcadeDrive(speed, turn);
		
		putMotorsOutputInNT();
	}

	public void tank(double left, double right){
		rightMotor_.set(-right);
		leftMotor_.set(left);
		
		putMotorsOutputInNT();
	}
	
	public void putMotorsOutputInNT() {
		SmartDashboard.putNumber("Right Motors Output: ", rightMotor_.get());
		SmartDashboard.putNumber("Left Motors Output: ", leftMotor_.get());
	}
	
	public PID_Gains getVisoinPanPIDGains(){
		return RobotMap.DRIVE_PAN_ENCODER_PID_GAINS;
	}
	
	public MPGains getVisoinPanMPGains(){
		return RobotMap.DRIVE_PAN_NAVX_MP_GAINS;
	}
	
	public void CreatNavX() {

		try {
			/***********************************************************************
			 * navX-MXP:
			 * - Communication via RoboRIO MXP (SPI, I2C, TTL UART) and USB.            
			 * - See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
			 * 
			 * navX-Micro:
			 * - Communication via I2C (RoboRIO MXP or Onboard) and USB.
			 * - See http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
			 * 
			 * Multiple navX-model devices on a single robot are supported.
			 ************************************************************************/
			navX = new AHRS(SPI.Port.kMXP); 
			SmartDashboard.putBoolean("NavX on:", true);

		} catch (RuntimeException ex ) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
			SmartDashboard.putBoolean("NavX on:", false);
			CreatNavX(); //try to re-connect
		}		
	}
	
	public float getAngleNavx(){
		return navX.getYaw();
	}
	
	public boolean getReadyToShoot() {
		return Math.abs(Robot.VM.getTargetPosition().getTargetAngle() - getAngleNavx()) <= RobotMap.DRIVE_PAN_MAX_ERROR;
	}
	
	/*public void enablePIDTurn() {
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
	}*/
}
