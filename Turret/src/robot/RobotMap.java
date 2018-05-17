/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//Joystick
	public static final int ADEL_JOYSTICK_CHANEL = 0;
	
	//shooter
	public static final int MASTER_SHOOTER_MOTOR_CAN = 0;
	public static final int SLAVE_SHOOTER_MOTOR_CAN = 1;
	public static final int SIDE_MOTOR_CAN = 2;
	
	//pwm
	public static final int DRIVE_RIGHT_PWM = 0;
	public static final int DRIVE_LEFT_PWM = 1;
	
	//drive
	public static final double DRIVE_SLOW = 0.5;
	
	//Shooter
	public static final double KP_SIDE = 0.006;
	public static final double KP_SPEEN_SPEED = 0.2;
	public static final double PULLS_PER_TIC = 4125;

}
