/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import robot.commands.ShooterCommand;
import robot.commands.drive.DriveSpeed;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick AdelStick = new Joystick(RobotMap.ADEL_JOYSTICK_CHANEL);

	public Button[] AdelBtns = new Button[12];

	public OI(){
		for (int i = 0; i < 12; i ++){
			AdelBtns[i] = new JoystickButton(AdelStick, i + 1);
		}
	}

	public void loadOIs(){		

		//Adel turn slow speed 
		//AdelBtns[5].whileHeld(new DriveSpeed(-RobotMap.DRIVE_SLOW, RobotMap.DRIVE_SLOW));//Turn right
		//AdelBtns[4].whileHeld(new DriveSpeed(RobotMap.DRIVE_SLOW, -RobotMap.DRIVE_SLOW));//Turn left
		
		AdelBtns[0].whileHeld(new ShooterCommand(-0.25));
		AdelBtns[1].whileHeld(new ShooterCommand(0));
		AdelBtns[2].whileHeld(new ShooterCommand(0.25));//Right
	}
}
