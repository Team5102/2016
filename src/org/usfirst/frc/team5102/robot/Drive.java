package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.Shifter.Gear;
import org.usfirst.frc.team5102.robot.util.MultiSpeedController;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Drive extends RobotElement
{
	MultiSpeedController leftDriveMotors,rightDriveMotors;
	
	static RobotDrive robotDrive;
	public static Shifter shifter;
	boolean aiming;
	
	int counter = 0;
	
	enum DriveMode
	{
		arcadeDrive,
		tankDrive
	}
	
	static DriveMode driveMode;
	
	Thread aim;
	
	Drive()
	{
		super(0);
		
		driveMode = DriveMode.arcadeDrive;
		
		leftDriveMotors = new MultiSpeedController(
				new Talon(RobotMap.leftDriveMotor1),
				new Talon(RobotMap.leftDriveMotor2),
				new Talon(RobotMap.leftDriveMotor3));
		
		rightDriveMotors = new MultiSpeedController(
				new Talon(RobotMap.rightDriveMotor1),
				new Talon(RobotMap.rightDriveMotor2),
				new Talon(RobotMap.rightDriveMotor3));
		robotDrive = new RobotDrive(leftDriveMotors, rightDriveMotors);
		robotDrive.setSafetyEnabled(false);
		shifter = new Shifter();
		
		aiming = false;
		
		aim = new Thread();
	}
	
	public void drive()
	{
		if(driveMode == DriveMode.arcadeDrive)
		{
			robotDrive.arcadeDrive((-controller.getLeftStickY()), (-controller.getRightStickX()));
		}
		else if(driveMode == DriveMode.tankDrive)
		{
			robotDrive.tankDrive((-controller.getLeftStickY()), (-controller.getRightStickY()));
		}
	}
	
	public void TankDrive(double leftValue, double rightValue)
	{
		robotDrive.tankDrive(leftValue, rightValue);
	}
	
	public void setDriveMode()
	{
		if(controller.getButtonBACK())
		{
			System.out.println(counter);
			if(counter > 15)
			{
				if(driveMode == DriveMode.tankDrive)
				{
					driveMode = DriveMode.arcadeDrive;
					System.out.println("arcade drive mode enabled");
					//controller.rumble();
				}
				
				else if(driveMode == DriveMode.arcadeDrive)
				{
					driveMode = DriveMode.tankDrive;
					System.out.println("tank drive mode enabled");
					//controller.rumble();
				}
				counter = 0;
			}
			else
			{
				counter++;
			}
		}
		else
		{
			counter = 0;
		}
	}
	
	public void aim()
	{
		/*
		if(controller.getPOV() == 90)
		{
			robotDrive.arcadeDrive(0, (-.45));
		}
		else if(controller.getPOV() == 270)
		{
			robotDrive.arcadeDrive(0, (0.45));
		}
		else
		{
			robotDrive.arcadeDrive(0, 0);
		}
		*/
		
		if(!aiming)
		{
			aim = new Thread()
			{
				public void run()
				{
					System.out.println("running thread");
					aiming = true;
					
					boolean running = aimX();
					/*
					if(running)
					{
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						running = aimX();
					}
					*/
					if(running)
					{
						running = aimY();
					}
					
					if(running)
					{
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						running = aimX();
					}
					
					if(running)
					{
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						running = aimX();
					}
			        	
			        System.out.println("aiming false");
			        aiming = false;
			        
			        if(running)
					{
			        	Shooter.shooting = true;
					}
			        
				}
			};
			aim.run();
		}
	}
	
	public boolean aimX()
	{
		double[] targets = Robot.grip.getNumberArray("centerX", new double[0]);
        
        if(targets.length > 0)
        {
        	double currentX = targets[0];
        	
        	while(!(currentX > 305 && currentX < 345))
        	{
        		targets = Robot.grip.getNumberArray("centerX", new double[0]);
        		if(targets.length < 1)
        		{
        			return false;
        		}
        		else
        		{
        			currentX = targets[0];
        			
        			if(currentX < 325)
        			{
        				Drive.robotDrive.tankDrive(-0.3, 0.36);
        				//Drive.robotDrive.arcadeDrive(0.0, .41);
        			}
        			else if(currentX > 325)
        			{
        				Drive.robotDrive.tankDrive(0.3, -0.38);
        				//Drive.robotDrive.arcadeDrive(0.0, -.42);
        			}
        		}
        		
        		if(!aiming)
        		{
        			System.out.println("thread broken");
        			
        			return false;
        		}
        	}
        	
        	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	while(!(currentX > 320 && currentX < 330))
        	{
        		targets = Robot.grip.getNumberArray("centerX", new double[0]);
        		if(targets.length < 1)
        		{
        			return false;
        		}
        		else
        		{
        			currentX = targets[0];
        			
        			if(currentX < 325)
        			{
        				Drive.robotDrive.tankDrive(-0.3, 0.36);
        				
        				try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				
        				Drive.robotDrive.tankDrive(0.0, 0.0);
        				
        				try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				
        				//Drive.robotDrive.arcadeDrive(0.0, .41);
        			}
        			else if(currentX > 325)
        			{
        				Drive.robotDrive.tankDrive(0.3, -0.38);
        				
        				try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				
        				Drive.robotDrive.tankDrive(0.0, 0.0);
        				
        				try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				
        				//Drive.robotDrive.arcadeDrive(0.0, -.42);
        			}
        		}
        		
        		if(!aiming)
        		{
        			System.out.println("thread broken");
        			
        			return false;
        		}
        	}
        	
        	Drive.robotDrive.arcadeDrive(0.0, 0.0);
        	
        }
        
        return true;
	}
	
	public boolean aimY()
	{
		double[] targets = Robot.grip.getNumberArray("centerY", new double[0]);
        
        if(targets.length > 0)
        {
        	double currentY = targets[0];
        	
        	while(!(currentY > 200 && currentY < 220))
        	{
        		targets = Robot.grip.getNumberArray("centerY", new double[0]);
        		if(targets.length < 1)
        		{
        			return false;
        		}
        		else
        		{
        			currentY = targets[0];
        			
        			if(currentY < 210)
        			{
        				Drive.robotDrive.tankDrive(-0.4, -0.4);
        				//Drive.robotDrive.arcadeDrive(0.0, .41);
        			}
        			else if(currentY > 210)
        			{
        				Drive.robotDrive.tankDrive(0.4, 0.4);
        				//Drive.robotDrive.arcadeDrive(0.0, -.42);
        			}
        		}
        		
        		if(!aiming)
        		{
        			System.out.println("thread broken");
        			
        			return false;
        		}
        	}
        	
        	Drive.robotDrive.arcadeDrive(0.0, 0.0);
        	
        }
        
        return true;
	}
	
	public void teleop()
	{		
		//==========Drive==========
		
		setDriveMode();
		
		//System.out.println((controller.getRightStickX()));
		
		if(!aiming)
		{
			//if(controller.applyDeadband(controller.getLeftStickY()) != 0 || controller.applyDeadband(controller.getRightStickX()) != 0)
			{
				drive();
			}
			
		}
		
		if(controller.getButtonY())
		{
			if(!aiming)
			{
				aim();
			}
		}
		
		
		//==========Shifter==========
				
		if(controller.getLeftTriggerButton())
		{ 
			shifter.shiftGears(Gear.low);
			System.out.println("low gear");
		}
		
		if(controller.getRightTriggerButton())
		{ 
			shifter.shiftGears(Gear.high);
			System.out.println("high gear");
		}
	}
	
	public void autonomous()
	{
		
	}
}
