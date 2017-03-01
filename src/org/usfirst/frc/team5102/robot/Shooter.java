package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.Aim.AimState;
import org.usfirst.frc.team5102.robot.RobotElement.Mode;
import org.usfirst.frc.team5102.robot.util.CustomTimer;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
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
	
	boolean tilting;
	
	Shooter()
	{
		super(1);
		
		shooterMotor1 = new CANTalon(RobotMap.shooterMotor1);
		shooterMotor1.setSafetyEnabled(false);
		
		shooterMotor2 = new CANTalon(RobotMap.shooterMotor2);
		shooterMotor2.setSafetyEnabled(false);
		shooterMotor2.changeControlMode(TalonControlMode.Follower);
		shooterMotor2.set(shooterMotor1.getDeviceID());
		
		shooterTiltMotor = new Talon(RobotMap.shooterTiltMotor);
		shooterTriggerMotor = new CANTalon(RobotMap.shooterTriggerMotor);
		
		ballLoaded = new DigitalInput(RobotMap.shooterLimit);
		
		shootCounter = 0;
		shooting = false;
		
		shootTimer = new CustomTimer();
		
		shooterTiltAngle = new AnalogPotentiometer(RobotMap.shooterTiltPot);
		
		tilting = false;
	}
	
	public void setDefault()
	{
		shooterMotor1.set(0.0);
		shooterTriggerMotor.set(0.0);
	}
	
	public void tiltShooter()
	{
		
		if(getAngle() < 0)
		{
			//shooterTiltMotor.set(controller.applyDeadband(controller.getLeftStickY())/2);
			//System.out.println(getAngle());
			
			if(controller.applyDeadband(controller.getLeftStickY()) < 0)
			{
				//System.out.println(controller.applyDeadband(controller.getLeftStickY()));
				shooterTiltMotor.set(controller.applyDeadband(controller.getLeftStickY())/4);
			}
			else
			{
				shooterTiltMotor.set(0.0);
			}
		}
		
		else if(getAngle() > 3.1
				)
		{
			if(controller.applyDeadband(controller.getLeftStickY()) > 0)
			{
				//System.out.println(controller.applyDeadband(controller.getLeftStickY()));
				shooterTiltMotor.set(controller.applyDeadband(controller.getLeftStickY())/4);
			}
			else
			{
				shooterTiltMotor.set(0.0);
			}			
		}
		
		else
		{
			//shooterTiltMotor.set(0.0);
			shooterTiltMotor.set(controller.applyDeadband(controller.getLeftStickY())/4);
		}
		
		//shooterTiltMotor.set(controller.applyDeadband(controller.getLeftStickY())/2);
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
						Aim.state = AimState.notAiming;
						shootCounter = 0;
						shooting = false;
						break;
				}
			}
			else
			{
				shootTimer.update();
			}
			
		}
		else
		{
			if(controller.getButtonA() && mode == Mode.teleop)
			{
				if(!ballLoaded.get())		//TODO add limit switch to robot
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
			shooterTriggerMotor.set(0.25);
		}
		else
		{
			shooterMotor1.set(0.0);
			shooterTriggerMotor.set(0.0);
		}
	}
	
	public double getAngle()
	{
		return ((shooterTiltAngle.get()*-100)+50)+1.2;
	}
	
	public void teleop()
	{		
		shootBall(Mode.teleop);
		
		if(!tilting)
		{
			tiltShooter();
		}
		
		if(!shooting)
		{
			intake();
		}
		
		/*
		if(controller.getButtonX())
		{
			if(!tilting)
			{
				gotoShootAngle();
				tilting = true;
			}
		}
		*/
	}
	
	public void gotoShootAngle()
	{
		if(getAngle() < 1.5)
		{
			shooterTiltMotor.set(-0.3);
			while(getAngle() < 1.5) {}
			shooterTiltMotor.set(0.0);
		}
		else if(getAngle() > 1.5)
		{
			shooterTiltMotor.set(0.3);
			while(getAngle() > 1.5) {}
			shooterTiltMotor.set(0.0);
		}
		tilting = false;
	}
	
	public void autonomous()
	{
		shootBall(Mode.auton);
	}
}
