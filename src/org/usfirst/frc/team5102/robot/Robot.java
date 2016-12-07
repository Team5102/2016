
package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.Drive.DriveMode;
import org.usfirst.frc.team5102.robot.RobotElement.Mode;
import org.usfirst.frc.team5102.robot.Shifter.Gear;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot
{
	private Drive drive;
	private Shooter shooter;
	private Autonomous auton;
	private Suspension suspension;
	
	private SendableChooser autoChooser;
	private int autonMode;
		
    public void robotInit()				//runs when robot is turned on
    {
    	drive = new Drive();
    	shooter = new Shooter();
    	auton = new Autonomous();
    	suspension = new Suspension();
    	
    	autoChooser = new SendableChooser();
    	autoChooser.addDefault("Auton 1", 1);
    	autoChooser.addObject("Auton 2", 2);
    	autoChooser.addObject("Auton 3", 3);
    	SmartDashboard.putData("Autonomous Selector", autoChooser);
    }
    
    public void disabledInit()
    {
    	
    }
    
    public void disabledPeriodic()
    {
    	updateSmartDashboard(Mode.disabled);
    	drive.setDriveMode();
    	
    	drive.controller.updateRumbleTimer();
        shooter.controller.updateRumbleTimer();
    }

    public void autonomousInit()		//runs when autonomous mode is enabled
    {
    	auton.autonInit();
    	autonMode = (int) autoChooser.getSelected();
    }
    
    public void autonomousPeriodic()	//runs periodically during autonomous mode
    {    	
    	updateSmartDashboard(Mode.auton);
    	
    	shooter.autonomous();
    	drive.autonomous();
    	
    	switch(autonMode)
    	{
    		case 1:
    			auton.autonomous1();
    			break;
    		case 2:
    			auton.autonomous2();
    			break;
    		case 3:
    			auton.autonomous3();
    			break;
    	}
    }

    public void teleopInit()			//runs when teleop mode is enabled
    {
    	
    }
    
    public void teleopPeriodic()		//runs periodically during teleop mode
    {    	
    	updateSmartDashboard(Mode.teleop);
    	
        drive.teleop();
        shooter.teleop();
        suspension.teleop();
        drive.controller.updateRumbleTimer();
        shooter.controller.updateRumbleTimer();
    }
    
    public void testInit()				//runs when test mode is enabled
    {
    	
    }
    
    public void testPeriodic()			//runs periodically during test mode
    {
    
    }
    
    public void updateSmartDashboard(Mode mode)
	{
		SmartDashboard.putNumber("Stored Air Pressure", Drive.shifter.getStoredPSI());
		SmartDashboard.putNumber("Working Air Pressure", Drive.shifter.getWorkingPSI());
		
		SmartDashboard.putNumber("Stored PSI", Drive.shifter.getStoredPSI());
		SmartDashboard.putNumber("Working PSI", Drive.shifter.getWorkingPSI());
		
		SmartDashboard.putNumber("Shooter Angle", shooter.getAngle());
		
		if(mode == Mode.auton)
		{
			SmartDashboard.putString("DriveMode", "AUTONOMOUS");
		}
		else if(Drive.driveMode == DriveMode.arcadeDrive)
		{
			SmartDashboard.putString("DriveMode", "ArcadeDrive");
		}
		else if(Drive.driveMode == DriveMode.tankDrive)
		{
			SmartDashboard.putString("DriveMode", "TankDrive");
		}
		
		if(Drive.shifter.getCurrentGear() == Gear.low)
		{
			SmartDashboard.putString("Gear", "Low");
		}
		else if(Drive.shifter.getCurrentGear() == Gear.high)
		{
			SmartDashboard.putString("Gear", "High");
		}
	}
}
