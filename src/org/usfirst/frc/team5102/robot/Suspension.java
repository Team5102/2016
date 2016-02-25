package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.CustomTimer;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;

public class Suspension
{
	Solenoid leftSuspension, rightSuspension;
	CustomTimer suspensionTimer;
	
	Suspension()
	{
		leftSuspension = new Solenoid(RobotMap.leftSuspension);
		rightSuspension = new Solenoid(RobotMap.rightSuspension);
		
		suspensionTimer = new CustomTimer();
	}
	
	public void updateSuspensionTimer()
	{
		if(!suspensionTimer.isRunning())
		{
			if(leftSuspension.get())
			{
				leftSuspension.set(false);
				rightSuspension.set(false);
				
				suspensionTimer.waitFor(10.0);
			}
			else
			{
				leftSuspension.set(true);
				rightSuspension.set(true);
				
				suspensionTimer.waitFor(1.0);
			}
		}
	}
	
	public void teleop()
	{
		updateSuspensionTimer();
	}
}
