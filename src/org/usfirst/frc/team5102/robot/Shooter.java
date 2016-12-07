package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.CustomTimer;
import org.usfirst.frc.team5102.robot.util.RobotMap;
import org.usfirst.frc.team5102.robot.util.Toggle;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Shooter extends RobotElement
{
	Talon shooterTiltMotor;
	CANTalon shooterMotor1, shooterMotor2, shooterTriggerMotor;
	//Talon shooterMotor1, shooterMotor2, shooterTriggerMotor;
	
	DigitalInput ballLoaded;
	
	int shootCounter, modeCounter;
	static boolean shooting, shootState, waitingToToggle;
	AnalogPotentiometer shooterTiltAngle;
		
	CustomTimer shootTimer;
	
	Toggle shooterMotorToggle;
	
	enum ShootMode
	{
		Automatic,
		Manual
	}
	
	ShootMode shootMode;
	
	Shooter()
	{
		super(1);
		
		shooterMotor1 = new CANTalon(RobotMap.shooterMotor1);
		//shooterMotor1 = new Talon(8);
		shooterMotor2 = new CANTalon(RobotMap.shooterMotor2);
		shooterTiltMotor = new Talon(RobotMap.shooterTiltMotor);
		shooterTriggerMotor = new CANTalon(RobotMap.shooterTriggerMotor);
		//shooterTriggerMotor = new Talon(9);
				
		ballLoaded = new DigitalInput(RobotMap.shooterLimit);
		
		shootCounter = 0;
		shooting = false;
		
		shootTimer = new CustomTimer();
		
		shooterMotorToggle = new Toggle(false, 0.0);
		
		shooterTiltAngle = new AnalogPotentiometer(RobotMap.shooterTiltPot);
		
		shootMode = ShootMode.Automatic;
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
		shooterTiltMotor.set((controller.applyDeadband(controller.getLeftStickY())/2)-0.2);
	}
	
	public void shootBall()
	{
		if(shooting)
		{
			if(!shootTimer.isRunning())
			{
				switch(shootCounter)
				{
					case 0:
						shooterMotor1.set(1.0);
						shooterMotor2.set(-1.0);
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
			if(controller.getButtonA() && Robot.getRobotMode() == Mode.teleop)
			{
				//if(ballLoaded.get())		//TODO add limit switch to robot
				{
					shooting = true;
					System.out.println("Shooting Ball");
				}
			}
			else if(Robot.getRobotMode() == Mode.teleop)
			{
				intake();
			}
		}
	}
	
	public void manualShoot()
	{
		shooterMotorToggle.toggle(controller.getButtonA());
		
		if(shooterMotorToggle.getToggleState())
		{
			shooterMotor1.set(1.0);
			shooterMotor2.set(-1.0);
			
			if(controller.getButtonB())
			{
				shooterTriggerMotor.set(-1.0);
			}
			else
			{
				shooterTriggerMotor.set(0.0);
			}
		}
		else
		{
			intake();
		}
	}
	
	public void intake()
	{
		if(controller.getButtonX() && ballLoaded.get())
		{
			shooterMotor1.set(-0.5);
			shooterMotor2.set(0.5);
			shooterTriggerMotor.set(1.0);
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
		switch(shootMode)
		{
			case Automatic:
				shootBall();
				break;
			case Manual:
				manualShoot();
				break;
		}
		
		tiltShooter();
	}
	
	public void autonomous()
	{
		shootBall();
	}
}
