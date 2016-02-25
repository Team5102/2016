
package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.Drive.DriveMode;
import org.usfirst.frc.team5102.robot.RobotElement.Mode;
import org.usfirst.frc.team5102.robot.Shifter.Gear;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot
{
	private Drive drive;
	private Shooter shooter;
	private Autonomous auton;
	private ScissorLift lift;
	private Suspension suspension;
	
	private SendableChooser autoChooser;
	private int autonMode;
	
	//int session;
    //Image frame;
    
    NetworkTable table;
	
    public void robotInit()				//runs when robot is turned on
    {
    	drive = new Drive();
    	shooter = new Shooter();
    	auton = new Autonomous();
    	lift = new ScissorLift();
    	suspension = new Suspension();
    	
    	autoChooser = new SendableChooser();
    	autoChooser.addDefault("Auton 1", 1);
    	autoChooser.addObject("Auton 2", 2);
    	autoChooser.addObject("Auton 3", 3);
    	SmartDashboard.putData("Autonomous Selector", autoChooser);
    	
    	//frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        //session = NIVision.IMAQdxOpenCamera("cam1",NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        //NIVision.IMAQdxConfigureGrab(session);
        
        //table = NetworkTable.getTable("GRIP/myContoursReport");
    }
    
    public void disabledInit()
    {
    	//drive.launchpad.light(true);
    }
    
    public void disabledPeriodic()
    {
    	updateCamera();
    	
    	//System.out.println(table.getNumber("width"));
    	
    	updateSmartDashboard(Mode.disabled);
    	drive.setDriveMode();
    	
    	drive.controller.updateRumbleTimer();
        shooter.controller.updateRumbleTimer();
        
        //System.out.println(ControllerPower.getVoltage5V());
        
        //System.out.println(shooter.shooterTiltAngle.get());
    }

    public void autonomousInit()		//runs when autonomous mode is enabled
    {
    	auton.autonInit();
    	autonMode = (int) autoChooser.getSelected();
    }
    
    public void autonomousPeriodic()	//runs periodically during autonomous mode
    {
    	updateCamera();
    	
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
    	drive.launchpad.light(false);
    }
    
    public void teleopPeriodic()		//runs periodically during teleop mode
    {
    	updateCamera();
    	
    	updateSmartDashboard(Mode.teleop);
    	
        drive.teleop();
        shooter.teleop();
        lift.teleop();
        suspension.teleop();
        drive.controller.updateRumbleTimer();
        shooter.controller.updateRumbleTimer();
        
        //System.out.println(shooter.shooterMotor1.getEncVelocity());
    }
    
    public void testInit()				//runs when test mode is enabled
    {
    	
    }
    
    public void testPeriodic()			//runs periodically during test mode
    {
    
    }
    
    public void updateSmartDashboard(Mode mode)
	{
		//SmartDashboard.putNumber("Left Drive Speed", Drive.leftDriveMotor.getSpeed());
		//SmartDashboard.putNumber("Right Drive Speed", (-Drive.rightDriveMotor.getSpeed()));
		
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
    
    public void updateCamera()
    {
    	//NIVision.IMAQdxGrab(session, frame, 1);
        //CameraServer.getInstance().setImage(frame);
    }
}
