package org.usfirst.frc.team5102.robot.util;

import edu.wpi.first.wpilibj.Joystick;

public class LaunchPad
{
	private Joystick launchpad;
	
	public LaunchPad()
	{
		launchpad = new Joystick(2);
		//launchpad.setOutput(1, true);
	}
	
	public void light(boolean state)
	{
		launchpad.setOutput(1, state);
	}
	
	public double getAxisAX()
	{
		return launchpad.getRawAxis(1);
	}
	
	public boolean getButton1()
	{
		return launchpad.getRawButton(1);
	}
	
	public boolean getButton2()
	{
		return launchpad.getRawButton(2);
	}
	
	public boolean getButton3()
	{
		return launchpad.getRawButton(3);
	}
	
	public boolean getButton4()
	{
		return launchpad.getRawButton(4);
	}
	
	public boolean getButton5()
	{
		return launchpad.getRawButton(5);
	}
	
	public boolean getButton6()
	{
		return launchpad.getRawButton(6);
	}
	
	public boolean getButton7()
	{
		return launchpad.getRawButton(7);
	}
	
	public boolean getButton8()
	{
		return launchpad.getRawButton(8);
	}
	
	public boolean getButton9()
	{
		return launchpad.getRawButton(9);
	}
	
	public boolean getButton10()
	{
		return launchpad.getRawButton(10);
	}
	
	public boolean getButton11()
	{
		return launchpad.getRawButton(11);
	}
}
