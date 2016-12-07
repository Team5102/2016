package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.CustomTimer;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;

public class Suspension
{
	Solenoid suspension;
	CustomTimer suspensionTimer;
	
	Suspension()
	{
		suspension = new Solenoid(RobotMap.suspension);
		
		suspensionTimer = new CustomTimer();
	}
	
	public void updateSuspensionTimer()
	{
		if(!suspensionTimer.isRunning())
		{
			if(suspension.get())
			{
				suspension.set(false);
				
				suspensionTimer.waitFor(10.0);
			}
			else
			{
				suspension.set(true);
				
				suspensionTimer.waitFor(1.0);
			}
		}
	}
	
	public void teleop()
	{
		updateSuspensionTimer();
	}
}
