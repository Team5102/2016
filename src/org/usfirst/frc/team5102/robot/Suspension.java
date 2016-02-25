package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Suspension
{
	Solenoid leftSuspension, rightSuspension;
	Timer suspensionTimer;
	
	Suspension()
	{
		leftSuspension = new Solenoid(RobotMap.leftSuspension);
		rightSuspension = new Solenoid(RobotMap.rightSuspension);
		
		suspensionTimer = new Timer();
		suspensionTimer.start();
	}
	
	public void updateSuspensionTimer()
	{
		if(leftSuspension.get())
		{
			if(suspensionTimer.get() > 1)
			{
				suspensionTimer.stop();
				suspensionTimer.reset();
				
				leftSuspension.set(false);
				rightSuspension.set(false);
				
				suspensionTimer.start();
			}
		}
		else
		{
			if(suspensionTimer.get() > 10)
			{
				suspensionTimer.stop();
				suspensionTimer.reset();
				
				leftSuspension.set(true);
				rightSuspension.set(true);
				
				suspensionTimer.start();
			}
		}
	}
	
	public void teleop()
	{
		updateSuspensionTimer();
	}
}
