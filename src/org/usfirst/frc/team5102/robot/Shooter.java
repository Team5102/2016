package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Shooter extends RobotElement
{
	Talon shooterTiltMotor;
	CANTalon shooterMotor1, shooterMotor2, shooterTriggerMotor;
	
	DigitalInput ballLoaded;
	
	int shootCounter;
	boolean waitingToShoot;
	static boolean shooting;
	AnalogPotentiometer shooterTiltAngle;
		
	Timer shootTimer;
	
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
		waitingToShoot = false;
		
		shootTimer = new Timer();
		
		shooterTiltAngle = new AnalogPotentiometer(RobotMap.shooterTiltPot);
				
		//shooterPID = new PIDController(1.0,0.0,0.0,shooterTiltAngle,shooterTiltMotor);
		//shooterPID.enable();
	}
	
	public void startShootTimer()
	{
		waitingToShoot = true;
		shootTimer.start();
	}
	
	public void updateShootTimer()
	{
		if(shootTimer.get() > 1.5)
		{
			shootTimer.stop();
			shootTimer.reset();
			shootCounter++;
			waitingToShoot = false;
		}
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
		shooterTiltMotor.set(controller.applyDeadband(controller.getLeftStickY())/4);
		//shooterPID.setSetpoint((controller.applyDeadband(controller.getLeftStickY())+1)*2.5);
	}
	
	public void shootBall()
	{
		if(shooting)
		{
			if(!waitingToShoot)
			{
				switch(shootCounter)
				{
					case 0:
						shooterMotor1.set(1.0);
						shooterMotor2.set(1.0);
						System.out.println("shooter motors started");
						startShootTimer();
						break;
					case 1:
						shooterTriggerMotor.set(-1.0);
						System.out.println("trigger activated");
						startShootTimer();
						break;
					case 2:
						setDefault();
						shootCounter = 0;
						shooting = false;
						break;
				}
			}
			else
			{
				updateShootTimer();
			}
			
		}
		else
		{
			if(controller.getButtonA())
			{
				//if(ballLoaded.get())		//TODO add limit switch to robot
				{
					shooting = true;
					System.out.println("Shooting Ball");
				}
			}
		}
	}
	
	public void autonShootBall()
	{
		if(shooting)
		{
			if(!waitingToShoot)
			{
				switch(shootCounter)
				{
					case 0:
						shooterMotor1.set(1.0);
						shooterMotor2.set(1.0);
						System.out.println("shooter motors started");
						startShootTimer();
						break;
					case 1:
						shooterTriggerMotor.set(-1.0);
						System.out.println("trigger activated");
						startShootTimer();
						break;
					case 2:
						setDefault();
						shootCounter = 0;
						shooting = false;
						break;				}
			}
			else
			{
				updateShootTimer();
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
		shootBall();
		tiltShooter();
		
		if(!shooting)
		{
			intake();
		}
	}
	
	public void autonomous()
	{
		autonShootBall();
	}
}
