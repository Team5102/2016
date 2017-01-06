package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.Vision;

public class Aim extends Thread
{
	static int targetX = 335;
	static int targetY = 230;
	
	double MPM = 0.00;									//motor power multiplier
	
    // TODO Consider using ints defined public and static?
    // (e.g. "public static final int NOT_AIMING_STATE = 0; ")?
	enum AimState
	{
		notAiming,
		aimX,
		aimY,
		shoot
	}
	
	enum AimMode
	{
		fast,
		accurate
	}
	
	static AimState state = AimState.notAiming;
	
	public void run()
	{
		System.out.println("running thread");
	    // TODO Typically you want to use getter and setter methods to change
        // local variables. 
        Drive.aiming = true;
		
		state = AimState.aimX;
		boolean running = aimX(AimMode.fast);					//aims X-axis
		
        // TODO: Use states rather than repeating these if conditions
        // (e.g. aimY() should return a state and the following if should
        // should check for that state. )
		if(running)									//determines if thread was canceled (target lost)
		{
			running = aimY();						//drive forward/backward to aim Y-axis
			state = AimState.aimY;
		}
		
		if(running)									//determines if thread was canceled (target lost)
		{
			pause(300);
			
			running = aimX(AimMode.accurate);						//re-aims X-axis
			state = AimState.aimX;
		}
		
		if(running)									//determines if thread was canceled (target lost)
		{
			pause(500);
			
			running = aimX(AimMode.accurate);						//double checks X-axis aim
		}
        	
        System.out.println("aiming false");
        Drive.aiming = false;
        
        if(running)									//determines if thread was canceled (target lost)
		{
        	state = AimState.shoot;
        	Shooter.shooting = true;				//shoots ball
		}
        else
        {
        	state = AimState.notAiming;
        }
        
	}
	
	public boolean aimX(AimMode mode)
	{
		double currentX = Vision.getTargetX();		//read GRIP array
        
        if(Vision.targetFound())														//determines if target is in view
        {
        	while(!(currentX > (targetX-20) && currentX < (targetX+20)))
        	{
        		if(Thread.currentThread().isInterrupted()) {return false;}
        	
        		if(!Vision.targetFound())												//determines if target is in view
        		{
        			return false;													//cancels if no target is found
        		}
        		else
        		{
        			currentX = Vision.getTargetX();
        			
        			if(currentX < targetX)
        			{
        				Drive.robotDrive.tankDrive(-0.25-MPM, 0.36+MPM);
        			}
        			else if(currentX > targetX)
        			{
        				Drive.robotDrive.tankDrive(0.25+MPM, -0.38-MPM);
        			}
        		}
        	}
        	if(mode == AimMode.fast)
        	{
        		Drive.robotDrive.arcadeDrive(0.0, 0.0);
        		
        		return true;
        	}
        	
        	pause(500);
        	
        	while(!(currentX > (targetX-5) && currentX < (targetX+5)))
        	{
        		if(Thread.currentThread().isInterrupted()) {return false;}
        		
        		if(!Vision.targetFound())												//determines if target is in view
        		{
        			return false;													//cancels if no target is found
        		}
        		else
        		{
        			currentX = Vision.getTargetX();
        			
        			if(currentX < targetX)
        			{
        				Drive.robotDrive.tankDrive(-0.3-MPM, 0.36+MPM);
        				
        				pause(125);
        				
        				Drive.robotDrive.tankDrive(0.0, 0.0);
        				
        				pause(100);
           			}
        			else if(currentX > targetX)
        			{
        				Drive.robotDrive.tankDrive(0.3+MPM, -0.38-MPM);
        				
        				pause(100);
        				
        				Drive.robotDrive.tankDrive(0.0, 0.0);
        				
        				pause(100);
           			}
        		}
        	}
        	
        	Drive.robotDrive.arcadeDrive(0.0, 0.0);
        	
        	return true;
        }
        return false;
	}
	
	public boolean aimY()
	{
		double currentY = Vision.getTargetY();		//read GRIP array
        
        if(Vision.targetFound())														//determines if target is in view
        {
        	while(!(currentY > (targetY-10) && currentY < (targetY+10)))
        	{
        		if(Thread.currentThread().isInterrupted()) {return false;}
        		
        		if(!Vision.targetFound())												//determines if target is in view
        		{
        			return false;													//cancels if no target is found
        		}
        		else
        		{
        			currentY = Vision.getTargetY();
        			
        			if(currentY < targetY)
        			{
        				Drive.robotDrive.tankDrive(-0.35-MPM, -0.35-MPM);
        			}
        			else if(currentY > targetY)
        			{
        				Drive.robotDrive.tankDrive(0.35+MPM, 0.35+MPM);
        			}
        		}
        	}
        	
        	Drive.robotDrive.arcadeDrive(0.0, 0.0);
        	
        	return true;
        }
        return false;
	}
	
	public boolean pause(int millis)
	{
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			return true;
		}
		return false;
	}
}
