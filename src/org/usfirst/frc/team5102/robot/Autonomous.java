package org.usfirst.frc.team5102.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous
{
	int autonCounter;
	double timeToWait;
	boolean active;
	Timer timer;
	double leftCurrentSpeed, rightCurrentSpeed;
	
	public Autonomous()
	{
		autonCounter = 0;
		active = true;
		timer = new Timer();
		timeToWait = 0;
		
		leftCurrentSpeed = 0;
		rightCurrentSpeed = 0;
	}
	
	public void startTimer(double seconds)
	{
		active = false;			//stops program from running next command until active mode is re-enabled
		timer.start();			//starts the timer
		timeToWait = seconds;	//sets amount of time before next command is run
	}
	
	public void updateTimer()
	{
		if(timer.get() >= timeToWait)	//detects if timer has gotten to the specified time
		{
			timer.stop();		//stops timer
			timer.reset();		//resets timer
			timeToWait = 0;		//resets time to wait
			autonCounter++;		//sets program to run next command in series
			active = true;		//enables program to run next command in series
		}
	}
	
	public void autonInit()
	{
		autonCounter = 0;
		active = true;
		
		timer.stop();
		timer.reset();
	}
	
	public void autonomous1()
	{	
		Drive.robotDrive.tankDrive(leftCurrentSpeed, rightCurrentSpeed);
		
		if(active)
		{
			switch(autonCounter)
			{
				case 0:		//first command in autonomous series
					leftCurrentSpeed = 1.0;
					rightCurrentSpeed = 1.0;
					System.out.println("motors started");
					startTimer(5.0);	//waits for the specified amount of time (seconds), then starts the next command.
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
		else
		{
			updateTimer();		//check if timer has gotten to the specified time
		}
	}
	
	public void autonomous2()
	{	
		Drive.robotDrive.tankDrive(leftCurrentSpeed, rightCurrentSpeed);
		
		if(active)
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
		else
		{
			updateTimer();
		}
	}
	
	public void autonomous3()
	{		
		Drive.robotDrive.tankDrive(leftCurrentSpeed, rightCurrentSpeed);
		
		if(active)
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
		else
		{
			updateTimer();
		}
	}
}
