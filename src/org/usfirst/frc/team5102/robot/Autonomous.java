package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.CustomTimer;

/**

	TODO: Rename autonomous methods to be more indicative of their task.
	(e.g. instead of "autonomous1" replace it with "shootSingleBallAuton")

	Consider making a single interface where driver set variables are accesed
	to reduce complexity? 
*/
public class Autonomous
{
	// TODO If you don't initialize local variables here, you usually want to
	// make sure you init all of them in your constructors.
	// Right now timeToWait and active aren't set.

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
			
		}
		else
		{
			timer.update();		//check if timer has gotten to the specified time
		}
	}
	
	public void autonomous2Init()
	{
		// TODO: I don't think this is how you should be creating new threads.
		new Thread()
		{
			public void run()
			{
				System.out.println("running thread");
				/*
				double[] targets = Robot.grip.getNumberArray("centerX", new double[0]);
		        
		        if(targets.length > 0)
		        {
		        	double currentX = targets[0];
		        	
		        	while(!(currentX > 280 && currentX < 300))
		        	{
		        		targets = Robot.grip.getNumberArray("centerX", new double[0]);
		        		if(targets.length < 1)
		        		{
		        			break;
		        		}
		        		else
		        		{
		        			currentX = targets[0];
		        			
		        			if(currentX < 290)
		        			{
		        				Drive.robotDrive.arcadeDrive(0.0, .41);
		        			}
		        			else if(currentX > 290)
		        			{
		        				Drive.robotDrive.arcadeDrive(0.0, -.42);
		        			}
		        		}
		        	}
		        	Drive.robotDrive.arcadeDrive(0.0, 0.0);
		        }
		        */
			}
		}.run();
	}
	
	public void autonomous2()
	{	
		//Drive.robotDrive.tankDrive(leftCurrentSpeed, rightCurrentSpeed);
		
		
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
		else
		{
			timer.update();
		}
	}
}
