package org.usfirst.frc.team5102.robot.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.Timer;

public class Xbox
{
	private Joystick stick;
	
	private double deadband = 0.1;
	
	private Timer rumbleTimer;
	
	public Xbox(int port)
	{
		stick = new Joystick(port);
		rumbleTimer = new Timer();
	}
	
	/*
	public void rumble(boolean rumbleState)
	{
		if(rumbleState == true)
		{
			stick.setRumble(RumbleType.kLeftRumble, 1);
			stick.setRumble(RumbleType.kRightRumble, 1);
		}
		else
		{
			stick.setRumble(RumbleType.kLeftRumble, 0);
			stick.setRumble(RumbleType.kRightRumble, 0);
		}
	}
	*/
	
	public double getLeftStickX()
	{
		return stick.getRawAxis(0);
	}
	
	public double getLeftStickY()
	{
		return stick.getRawAxis(1);
	}
	
	public double getRightStickX()
	{
		return stick.getRawAxis(4);
	}
	
	public double getRightStickY()
	{
		return stick.getRawAxis(5);
	}
	
	public boolean getButtonX()
	{
		return stick.getRawButton(3);
	}
	
	public boolean getButtonA()
	{
		return stick.getRawButton(1);
	}
	
	public boolean getButtonB()
	{
		return stick.getRawButton(2);
	}
	
	public boolean getButtonY()
	{
		return stick.getRawButton(4);
	}
	
	public boolean getButtonBACK()
	{
		return stick.getRawButton(7);
	}
	
	public boolean getLeftTriggerButton()
	{
		return stick.getRawButton(5);
	}
	
	public boolean getRightTriggerButton()
	{
		return stick.getRawButton(6);
	}
	
	public double getLeftTriggerAxis()
	{
		return stick.getRawAxis(2);
	}
	
	public double getRightTriggerAxis()
	{
		return stick.getRawAxis(3);
	}
	
	public double applyDeadband(double magnitude)
	{
		
		if(Math.abs(magnitude) > deadband)
		{
			return magnitude;
		}
		
		return 0.00;
	}
	
	public void rumble()
	{
		stick.setRumble(RumbleType.kLeftRumble, 1);
		stick.setRumble(RumbleType.kRightRumble, 1);
		
		rumbleTimer.start();
	}
	
	public void updateRumbleTimer()
	{
		if(rumbleTimer.get() > 0.5)
		{
			rumbleTimer.stop();
			rumbleTimer.reset();
			
			stick.setRumble(RumbleType.kLeftRumble, 0);
			stick.setRumble(RumbleType.kRightRumble, 0);
		}
	}
}
