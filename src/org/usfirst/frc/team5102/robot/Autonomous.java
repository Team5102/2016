package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.Shifter.Gear;
import org.usfirst.frc.team5102.robot.util.CustomTimer;
import org.usfirst.frc.team5102.robot.util.CustomAutonomous;


public class Autonomous
{
	int autonCounter;
	double timeToWait;
	boolean active;
	CustomTimer timer;
	double leftCurrentSpeed, rightCurrentSpeed;
	
	CustomAutonomous auton;
	
	public Autonomous()
	{
		autonCounter = 0;
		timer = new CustomTimer();
		
		leftCurrentSpeed = 0;
		rightCurrentSpeed = 0;
		
		auton = new CustomAutonomous();
		
		auton.addAutonBlock(new Runnable()
		{
		    @Override
		    public void run()
		    {
		       System.out.println("test1");
		    }
		}, 1.0);
		
		auton.addAutonBlock(new Runnable()
		{
		    @Override
		    public void run()
		    {
		       System.out.println("test2");
		    }
		}, 1.0);
	}
	
	//interface Executable {
	  //  int execute();
	//}
	
	public void autonInit()
	{
		autonCounter = 0;
	}
	
	public void autonomousTest()
	{
		
	}
	
	public void autonomous1()
	{	
		Drive.robotDrive.tankDrive(leftCurrentSpeed, rightCurrentSpeed);
		
		if(!timer.isRunning())
		{
			switch(autonCounter)
			{
				case 0:		//first command in autonomous series
					Drive.shifter.shiftGears(Gear.low);
					leftCurrentSpeed = 0.55;
					rightCurrentSpeed = 0.55;
					System.out.println("motors started");
					autonCounter++;
					timer.waitFor(6.0);	//waits for the specified amount of time (seconds), then starts the next command.
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
