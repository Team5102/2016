package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Climber extends RobotElement
{
	Talon liftMotor;
	Solenoid liftLock, shooterPositioner, shooterTrigger;
	
	Climber()
	{
		super(1);
		
		liftMotor = new Talon(RobotMap.climberLiftMotor);
		
		liftLock = new Solenoid(RobotMap.liftLock);
		shooterPositioner = new Solenoid(RobotMap.gunPosition);
		shooterTrigger = new Solenoid(RobotMap.gunTrigger);
	}
	
	public void teleop()
	{
		
	}
}
