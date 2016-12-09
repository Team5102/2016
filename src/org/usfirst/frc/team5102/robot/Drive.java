package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.Shifter.Gear;
import org.usfirst.frc.team5102.robot.util.MultiSpeedController;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Drive extends RobotElement
{
	MultiSpeedController leftDriveMotors,rightDriveMotors;
	
	static RobotDrive robotDrive;
	public static Shifter shifter;
	static boolean aiming;
	
	int counter = 0;
	
	enum DriveMode
	{
		arcadeDrive,
		tankDrive
	}
	
	static DriveMode driveMode;
	
	static Thread aim;
	
	Drive()
	{
		super(0);
		
		driveMode = DriveMode.arcadeDrive;
		
		leftDriveMotors = new MultiSpeedController(
				new Talon(RobotMap.leftDriveMotor1),
				new Talon(RobotMap.leftDriveMotor2),
				new Talon(RobotMap.leftDriveMotor3));
		
		rightDriveMotors = new MultiSpeedController(
				new Talon(RobotMap.rightDriveMotor1),
				new Talon(RobotMap.rightDriveMotor2),
				new Talon(RobotMap.rightDriveMotor3));
		robotDrive = new RobotDrive(leftDriveMotors, rightDriveMotors);
		robotDrive.setSafetyEnabled(false);
		shifter = new Shifter();
		
		aiming = false;
		
		aim = new Thread();
	}
	
	public void drive()
	{
		if(driveMode == DriveMode.arcadeDrive)
		{
			robotDrive.arcadeDrive((-controller.getLeftStickY()), (-controller.getRightStickX()));
		}
		else if(driveMode == DriveMode.tankDrive)
		{
			robotDrive.tankDrive((-controller.getLeftStickY()), (-controller.getRightStickY()));
		}
	}
	
	public void TankDrive(double leftValue, double rightValue)
	{
		robotDrive.tankDrive(leftValue, rightValue);
	}
	
	public void setDriveMode()
	{
		if(controller.getButtonBACK())
		{
			System.out.println(counter);
			if(counter > 15)
			{
				if(driveMode == DriveMode.tankDrive)
				{
					driveMode = DriveMode.arcadeDrive;
					System.out.println("arcade drive mode enabled");
					//controller.rumble();
				}
				
				else if(driveMode == DriveMode.arcadeDrive)
				{
					driveMode = DriveMode.tankDrive;
					System.out.println("tank drive mode enabled");
					//controller.rumble();
				}
				counter = 0;
			}
			else
			{
				counter++;
			}
		}
		else
		{
			counter = 0;
		}
	}
	
	public void aim()
	{
		/*
		if(controller.getPOV() == 90)
		{
			robotDrive.arcadeDrive(0, (-.45));
		}
		else if(controller.getPOV() == 270)
		{
			robotDrive.arcadeDrive(0, (0.45));
		}
		else
		{
			robotDrive.arcadeDrive(0, 0);
		}
		*/
	}
	
	public void teleop()
	{		
		//==========Drive==========
		
		setDriveMode();
				
		if(!aiming)
		{
			//if(controller.applyDeadband(controller.getLeftStickY()) != 0 || controller.applyDeadband(controller.getRightStickX()) != 0)
			{
				drive();
			}
		}
		
		if(controller.getButtonY())
		{
			if(!aiming)
			{
				aim = new Aim();
				aim.start();					//start aim procedure
			}
		}
		
		//System.out.println(aim.isAlive());
		
		
		//==========Shifter==========
				
		if(controller.getLeftTriggerButton())
		{ 
			shifter.shiftGears(Gear.low);
			System.out.println("low gear");
		}
		
		if(controller.getRightTriggerButton())
		{ 
			shifter.shiftGears(Gear.high);
			System.out.println("high gear");
		}
	}
	
	public void autonomous()
	{
		
	}
}
