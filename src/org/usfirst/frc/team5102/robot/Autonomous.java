package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.CustomTimer;

public class Autonomous
{
	int autonCounter;
	double timeToWait;
	boolean active;
	CustomTimer timer;
	double leftCurrentSpeed, rightCurrentSpeed;
	
	public Autonomous()
	{
		autonCounter = 0;
		timer = new CustomTimer();
		
		leftCurrentSpeed = 0;
		rightCurrentSpeed = 0;
	}
	
	public void autonInit()
	{
		autonCounter = 0;
	}
	
	public void autonomous1()
	{	
		Drive.robotDrive.tankDrive(leftCurrentSpeed, rightCurrentSpeed);
		
		if(!timer.isRunning())
		{
			switch(autonCounter)
			{
				case 0:		//first command in autonomous series
					leftCurrentSpeed = 1.0;
					rightCurrentSpeed = 1.0;
					System.out.println("motors started");
					autonCounter++;
					timer.waitFor(5.0);	//waits for the specified amount of time (seconds), then starts the next command.
					break;
				case 1:		//second command in autonomous series
					leftCurrentSpeed = 0.0;
					rightCurrentSpeed = 0.0;
					System.out.println("motors stopped");
					autonCounter++;
					break;
				case 2:		//third command in autonomous series
					
					break;
			}
		}
	}
	
	public void autonomous2()
	{	
		Drive.robotDrive.tankDrive(leftCurrentSpeed, rightCurrentSpeed);
		
		if(!timer.isRunning())
		{
			switch(autonCounter)
			{
				case 0:
					
					break;
				case 1:
					
					break;
				case 2:
					
					break;
			}
		}
	}
	
	public void autonomous3()
	{		
		Drive.robotDrive.tankDrive(leftCurrentSpeed, rightCurrentSpeed);
		
		if(!timer.isRunning())
		{
			switch(autonCounter)
			{
				case 0:
					
					break;
				case 1:
					
					break;
				case 2:
					
					break;
			}
		}
	}
}
