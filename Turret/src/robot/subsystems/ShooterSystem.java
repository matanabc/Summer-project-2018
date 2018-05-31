package robot.subsystems;

import java.util.Calendar;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import robot.RobotMap;
import robot.commands.Shooter.AddEncoderPositionToHestoryCommand;

/**
 *
 */
public class ShooterSystem extends Subsystem {

	private WPI_TalonSRX masterShootMotor_ = new WPI_TalonSRX(RobotMap.MASTER_SHOOTER_MOTOR_CAN);
	private WPI_TalonSRX slaveShootMotor_= new WPI_TalonSRX(RobotMap.SLAVE_SHOOTER_MOTOR_CAN);

	//private VictorSP sideMotor_ = new VictorSP(RobotMap.SIDE_MOTOR_CAN);
	private WPI_TalonSRX sideEncoder_ = new WPI_TalonSRX(RobotMap.SIDE_MOTOR_CAN);

	//Tree of history position from encoder
	private TreeMap<Long,Double> encoderHistory = new TreeMap<Long,Double>();


	public ShooterSystem(){
		resetTalon();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new AddEncoderPositionToHestoryCommand());
	}

	public void resetTalon(){
		masterShootMotor_.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
		masterShootMotor_.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); 
		slaveShootMotor_.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);

		sideEncoder_.set(com.ctre.phoenix.motorcontrol.ControlMode.Position,0);
		sideEncoder_.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		sideEncoder_.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); 
		
    	sideEncoder_.config_kF(0, 0, 0);
    	sideEncoder_.config_kP(0, 0, 0);
    	sideEncoder_.config_kI(0, 0, 0);
    	sideEncoder_.config_kD(0, 0, 0);
    	
    	sideEncoder_.config_kP(0, RobotMap.KP_SIDE, 0);
    	sideEncoder_.config_kD(0, RobotMap.KD_SIDE, 0);
    	sideEncoder_.config_IntegralZone(0, RobotMap.KINTEGRAL_I_ZONE_SIDE, 0);

	}
	
	public void setSideToStay(){
		sideEncoder_.set(ControlMode.Position, (getSideEncoderPosition()/RobotMap.ENCODER_POSITION_ANGLE));
	}
	
	public double getShooterVelocity(){
		return masterShootMotor_.getSelectedSensorVelocity(0);// * 60 / RobotMap.PULLS_PER_TIC;
	}

	public void resetTalonsEncoders(){

		masterShootMotor_.setSelectedSensorPosition(0, 0, 0);
		sideEncoder_.setSelectedSensorPosition(0, 0, 0);

		/*masterShootMotor_.set(com.ctre.phoenix.motorcontrol.ControlMode.Velocity, 0);
    	masterShootMotor_.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    	masterShootMotor_.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

    	masterShootMotor_.config_kF(0, 0, 0);
    	masterShootMotor_.config_kP(0, 0, 0);
    	masterShootMotor_.config_kI(0, 0, 0);
    	masterShootMotor_.config_kD(0, 0, 0);

    	slaveShootMotor_.follow(masterShootMotor_);

    	masterShootMotor_.setInverted(false);
    	slaveShootMotor_.setInverted(false);*/
		
	}

	public void setShootRPM(double rpm){
		//rpm = rpm / RobotMap.PULLS_PER_TIC;
		masterShootMotor_.set(rpm);
		slaveShootMotor_.set(-rpm);
	}

	public void setSideMotorSetPoint(double position){
		//sideMotor_.set(speed);
		//sideEncoder_.set(0.2);
		sideEncoder_.set(ControlMode.Position, (position/RobotMap.ENCODER_POSITION_ANGLE));
	}

	public double getSideEncoderPosition(){
		return sideEncoder_.getSelectedSensorPosition(0) * RobotMap.ENCODER_POSITION_ANGLE;
	}

	public void addEncoderPoditionToHistory() {
		//Add encoder position and time to history
		encoderHistory.put(Calendar.getInstance().getTimeInMillis(), getSideEncoderPosition());
		
		// remove the lowest key so he don't grow forever...
		if(encoderHistory.size() > RobotMap.MAX_DECODER_HOSTORY_SIZE) {
			encoderHistory.remove(encoderHistory.firstKey());	
			//System.out.println("----Remove----");
		}
	}
	
	public double getEncoderPositionFromTime(Long time) {
		//check if is bigger then 0
		if(encoderHistory.size()==0) return 0;

		//Find the key value who closer to the time from camera  	
		Entry<Long, Double> encoderTime = encoderHistory.floorEntry(time);

		//If the key equals to null get the first key
		if(encoderTime == null) {
			encoderTime = encoderHistory.firstEntry();
		}
		
		//Return encoder position form the time
		return encoderTime.getValue();
	}

}

