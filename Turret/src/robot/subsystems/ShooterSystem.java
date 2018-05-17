package robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.RobotMap;

/**
 *
 */
public class ShooterSystem extends Subsystem {

	private WPI_TalonSRX masterShootMotor_ = new WPI_TalonSRX(RobotMap.MASTER_SHOOTER_MOTOR_CAN);
    private WPI_TalonSRX slaveShootMotor_= new WPI_TalonSRX(RobotMap.SLAVE_SHOOTER_MOTOR_CAN);
    
    private VictorSP sideMotor_ = new VictorSP(RobotMap.SIDE_MOTOR_CAN);
    private WPI_TalonSRX sideEncoder_ = new WPI_TalonSRX(RobotMap.SIDE_MOTOR_CAN);

    public ShooterSystem(){
    	masterShootMotor_.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
		masterShootMotor_.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); 
		slaveShootMotor_.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
		
		sideEncoder_.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		sideEncoder_.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); 
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double getShooterVelocity(){
		return masterShootMotor_.getSelectedSensorVelocity(0) * 60 / RobotMap.PULLS_PER_TIC;
    }
    
    public void resetTalon(){
       	
    	masterShootMotor_.setSelectedSensorPosition(0, 0, 0);
    	sideEncoder_.setSelectedSensorPosition(0, 0, 0);
    	
		/*masterShootMotor_.set(com.ctre.phoenix.motorcontrol.ControlMode.Velocity, 0);
    	masterShootMotor_.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    	masterShootMotor_.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    	
    	masterShootMotor_.config_kF(0, 0.08, 10);
    	masterShootMotor_.config_kP(0, 0.15, 10);
    	masterShootMotor_.config_kI(0, 0, 10);
    	masterShootMotor_.config_kD(0, 0, 10);
    	
    	slaveShootMotor_.follow(masterShootMotor_);
    	
    	masterShootMotor_.setInverted(false);
    	slaveShootMotor_.setInverted(false);*/
    }
    
    public void setShootRPM(double rpm){
    	//rpm = rpm / RobotMap.PULLS_PER_TIC;
    	masterShootMotor_.set(rpm);
    	slaveShootMotor_.set(-rpm);
    }
    
    public void setSideMotorSpeed(double speed){
    	sideMotor_.set(speed);
    }
    
    public double getSideEncoderPosition(){
    	return sideEncoder_.getSelectedSensorPosition(0) * 0.03773584905660377358490566037736;
    }
}

