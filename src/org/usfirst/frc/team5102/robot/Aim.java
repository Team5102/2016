package org.usfirst.frc.team5102.robot;

public class Aim extends Thread
{
	static int targetX = 335;
	static int targetY = 230;
	
	double MPM = 0.03;									//motor power multiplier
	
	enum AimState
	{
		notAiming,
		aimX,
		aimY,
		shoot
	}
	
	static AimState state = AimState.notAiming;
	
	public void run()
	{
		System.out.println("running thread");
		Drive.aiming = true;
		
		state = AimState.aimX;
		boolean running = aimX();					//aims X-axis
		
		if(running)									//determines if thread was canceled (target lost)
		{
			running = aimY();						//drive forward/backward to aim Y-axis
			state = AimState.aimY;
		}
		
		if(running)									//determines if thread was canceled (target lost)
		{
			pause(300);
			
			running = aimX();						//re-aims X-axis
			state = AimState.aimX;
		}
		
		if(running)									//determines if thread was canceled (target lost)
		{
			pause(500);
			
			running = aimX();						//double checks X-axis aim
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
	
	public boolean aimX()
	{
		double[] targets = Robot.grip.getNumberArray("centerX", new double[0]);		//read GRIP array
        
        if(targets.length > 0)														//determines if target is in view
        {
        	double currentX = targets[0];
        	
        	while(!(currentX > (targetX-20) && currentX < (targetX+20)))
        	{
        		if(Thread.currentThread().isInterrupted()) {return false;}
        	
        		targets = Robot.grip.getNumberArray("centerX", new double[0]);		//read GRIP array
        		if(targets.length < 1)												//determines if target is in view
        		{
        			return false;													//cancels if no target is found
        		}
        		else
        		{
        			currentX = targets[0];
        			
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
        	
        	pause(500);
        	
        	while(!(currentX > (targetX-5) && currentX < (targetX+5)))
        	{
        		if(Thread.currentThread().isInterrupted()) {return false;}
        		
        		targets = Robot.grip.getNumberArray("centerX", new double[0]);		//read GRIP array
        		if(targets.length < 1)												//determines if target is in view
        		{
        			return false;													//cancels if no target is found
        		}
        		else
        		{
        			currentX = targets[0];
        			
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
		double[] targets = Robot.grip.getNumberArray("centerY", new double[0]);		//read GRIP array
        
        if(targets.length > 0)														//determines if target is in view
        {
        	double currentY = targets[0];
        	
        	while(!(currentY > (targetY-10) && currentY < (targetY+10)))
        	{
        		if(Thread.currentThread().isInterrupted()) {return false;}
        		
        		targets = Robot.grip.getNumberArray("centerY", new double[0]);		//read GRIP array
        		if(targets.length < 1)												//determines if target is in view
        		{
        			return false;													//cancels if no target is found
        		}
        		else
        		{
        			currentY = targets[0];
        			
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
