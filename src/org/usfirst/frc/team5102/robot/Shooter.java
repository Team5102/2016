package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.CustomTimer;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

public class Shooter extends RobotElement
{
	Talon shooterTiltMotor;
	CANTalon shooterMotor1, shooterMotor2, shooterTriggerMotor;
	
	DigitalInput ballLoaded;
	
	int shootCounter;
	static boolean shooting;
	AnalogPotentiometer shooterTiltAngle;
		
	CustomTimer shootTimer;
	
	PIDController shooterPID;
	
	Shooter()
	{
		super(1);
		
		shooterMotor1 = new CANTalon(RobotMap.shooterMotor1);
		shooterMotor2 = new CANTalon(RobotMap.shooterMotor2);
		shooterTiltMotor = new Talon(RobotMap.shooterTiltMotor);
		shooterTriggerMotor = new CANTalon(RobotMap.shooterTriggerMotor);
				
		ballLoaded = new DigitalInput(RobotMap.shooterLimit);
		
		shootCounter = 0;
		shooting = false;
		
		shootTimer = new CustomTimer();
		
		shooterTiltAngle = new AnalogPotentiometer(RobotMap.shooterTiltPot);
				
		//shooterPID = new PIDController(1.0,0.0,0.0,shooterTiltAngle,shooterTiltMotor);
		//shooterPID.enable();
	}
	
	public void setDefault()
	{
		shooterMotor1.set(0.0);
		shooterMotor2.set(0.0);
		System.out.println("shooter motors stopped");
		shooterTriggerMotor.set(0.0);
		System.out.println("trigger disabled");
	}
	
	public void tiltShooter()
	{
		shooterTiltMotor.set(controller.applyDeadband(controller.getLeftStickY())/2);
		//shooterPID.setSetpoint((controller.applyDeadband(controller.getLeftStickY())+1)*2.5);
	}
	
	public void shootBall(Mode mode)
	{
		if(shooting)
		{
			if(!shootTimer.isRunning())
			{
				switch(shootCounter)
				{
					case 0:
						shooterMotor1.set(1.0);
						shooterMotor2.set(1.0);
						System.out.println("shooter motors started");
						shootCounter++;
						shootTimer.waitFor(1.5);
						break;
					case 1:
						shooterTriggerMotor.set(-1.0);
						System.out.println("trigger activated");
						shootCounter++;
						shootTimer.waitFor(1.5);
						break;
					case 2:
						setDefault();
						shootCounter = 0;
						shooting = false;
						break;
				}
			}
		}
		else
		{
			if(controller.getButtonA() && mode == Mode.teleop)
			{
				//if(ballLoaded.get())		//TODO add limit switch to robot
				{
					shooting = true;
					System.out.println("Shooting Ball");
				}
			}
		}
	}
	
	public void intake()
	{
		if(controller.getButtonB() && ballLoaded.get())
		{
			shooterMotor1.set(-0.5);
			shooterMotor2.set(-0.5);
			shooterTriggerMotor.set(0.25);
		}
		else
		{
			shooterMotor1.set(0.0);
			shooterMotor2.set(0.0);
			shooterTriggerMotor.set(0.0);
		}
	}
	
	public double getAngle()
	{
		return shooterTiltAngle.get();
	}
	
	public void teleop()
	{		
		shootBall(Mode.teleop);
		tiltShooter();
		
		if(!shooting)
		{
			intake();
		}
	}
	
	public void autonomous()
	{
		shootBall(Mode.auton);
	}
}
