/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.subsystems.DriveSystem;
import robot.subsystems.ShooterSystem;
import vision.VisionClass.VisionMaster;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	public static ShooterSystem shooterSystem;
	public static DriveSystem driveSystem;
	public static OI oi;

	//public static LinkedList<String> logFile;

	public static VisionMaster VM;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		//logFile = new LinkedList<>();
		//logFile.add("Robot is on");

		shooterSystem = new ShooterSystem();
		driveSystem = new DriveSystem();

		oi = new OI();
		

		VM = new VisionMaster(oi.VC, RobotMap.MAX_DECODER_HOSTORY_SIZE, RobotMap.CAMERA_ANGLE,
				RobotMap.CAMERA_WIDTH, RobotMap.NT_VALUE_NAME, RobotMap.OFFSEAT_CAMERA_FROM_CENTER);
		
		oi.loadOIs();

		//m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */	
	@Override
	public void disabledInit() {
		//logFile.add("Robot is disable");
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		print();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {

		//logFile.add("Robot start auto");

		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {

		//logFile.add("Robot is in driver control");

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		shooterSystem.resetTalon();
		shooterSystem.setSideMotorSetPoint(0);
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		print();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	public void print(){
		SmartDashboard.putNumber("shooter velocity:", shooterSystem.getShooterVelocity());
		SmartDashboard.putNumber("side position:", shooterSystem.getSideEncoderPosition());

		SmartDashboard.putNumber("Navx angle", driveSystem.getAngleNavx());

		SmartDashboard.putNumber("Error To Target", Math.abs(Robot.VM.getAngleAndDistanceToTarget().getAngleToTarget()) 
				- Math.abs(Robot.driveSystem.getAngleNavx()));

		SmartDashboard.putNumber("Target Angle", Robot.VM.getAngleAndDistanceToTarget().getAngleToTarget());

	}
}
