
package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.Drive.DriveMode;
import org.usfirst.frc.team5102.robot.RobotElement.Mode;
import org.usfirst.frc.team5102.robot.Shifter.Gear;
import org.usfirst.frc.team5102.robot.util.ArduinoComm;
import org.usfirst.frc.team5102.robot.util.ArduinoComm.RobotMode;
import org.usfirst.frc.team5102.robot.util.LaunchPad;
import org.usfirst.frc.team5102.robot.util.Vision;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot
{
	private Drive drive;
	private Shooter shooter;
	private Autonomous auton;
	
	private SendableChooser autoChooser;
	private int autonMode;
	
	//private LaunchPad launchpad;
	
	private ArduinoComm arduinoComm;
	
	//CameraServer server;
	
	//int session;
    //Image frame;
	
    public void robotInit()				//runs when robot is turned on
    {
    	drive = new Drive();
    	shooter = new Shooter();
    	auton = new Autonomous();
    	
    	//launchpad = new LaunchPad(2);
    	
    	arduinoComm = new ArduinoComm(2);
    	
    	autoChooser = new SendableChooser();
    	autoChooser.addDefault("Auton 1", 1);
    	autoChooser.addObject("Auton 2", 2);
    	autoChooser.addObject("Auton 3", 3);
    	SmartDashboard.putData("Autonomous Selector", autoChooser);
    	
    	Vision.init();
    	
    	/*
    	server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam0");
        */
    	/*
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        
        NIVision.IMAQdxStartAcquisition(session);
        */
    	/*
    	try {
            new ProcessBuilder("/home/lvuser/grip").inheritIO().start();
        } catch (IOException e) {
        	System.out.println("it didn't work");
            e.printStackTrace();
        }
        */
    }
    
    public void disabledInit()
    {
    	Drive.aim.interrupt();
    	drive.aiming = false;
    	
    	arduinoComm.setMode(RobotMode.disabled);
    }
    
    public void disabledPeriodic()
    {
    	updateSmartDashboard(Mode.disabled);
    	drive.setDriveMode();
        
        updateCamera();
        
        arduinoComm.updateAirMeter(drive.shifter.getWorkingPSI());
        
        //System.out.println(drive.controller.getPOV());
        
        
        
    }

    public void autonomousInit()		//runs when autonomous mode is enabled
    {
    	arduinoComm.setMode(RobotMode.auton);
    	
    	auton.autonInit();
    	autonMode = (int) autoChooser.getSelected();
    	
    	switch(autonMode)
    	{
    		case 1:
    			
    			break;
    		case 2:
    			auton.autonomous2Init();
    			break;
    		case 3:
    			
    			break;
    	}
    	//System.out.println("auton init");
    	//auton.autonomous2Init();
    }
    
    public void autonomousPeriodic()	//runs periodically during autonomous mode
    {    	
    	updateSmartDashboard(Mode.auton);
    	
    	shooter.autonomous();
    	drive.autonomous();
    	
    	arduinoComm.updateAirMeter(drive.shifter.getWorkingPSI());
    	
    	/*
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
    	*/
    }

    public void teleopInit()			//runs when teleop mode is enabled
    {
    	arduinoComm.setMode(RobotMode.teleop);
    }
    
    public void teleopPeriodic()		//runs periodically during teleop mode
    {    	
    	updateSmartDashboard(Mode.teleop);
    	
        drive.teleop();
        shooter.teleop();
        
        updateCamera();
        
        arduinoComm.updateAirMeter(drive.shifter.getWorkingPSI());
        
        /*
        double error = 335 - Vision.getTargetX();
		double degrees = error/9;
		System.out.println(degrees + " - " + Drive.gyro.getAngle());
		*/
    }
    
    public void testInit()				//runs when test mode is enabled
    {
    	
    }
    
    public void testPeriodic()			//runs periodically during test mode
    {
    	drive.teleop();
    	shooter.teleop();
    	
    	System.out.println(Vision.getTargetX());
    }
    
    public void updateSmartDashboard(Mode mode)
	{
		SmartDashboard.putNumber("Stored Air Pressure", Drive.shifter.getWorkingPSI());
		SmartDashboard.putNumber("Working Air Pressure", Drive.shifter.getWorkingPSI());
		
		SmartDashboard.putNumber("Stored PSI", Drive.shifter.getStoredPSI());
		SmartDashboard.putNumber("Working PSI", Drive.shifter.getWorkingPSI());
		
		SmartDashboard.putNumber("Shooter Angle", shooter.getAngle());
		
		SmartDashboard.putNumber("Target X", Vision.getTargetX());
		SmartDashboard.putNumber("Target Y", Vision.getTargetY());
		
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
		
		switch(Aim.state)
		{
			case notAiming:
				SmartDashboard.putString("AimState", "Not Aiming");
				break;
			case aimX:
				SmartDashboard.putString("AimState", "Aiming X");
				break;
			case aimY:
				SmartDashboard.putString("AimState", "Aiming Y");
				break;
			case shoot:
				SmartDashboard.putString("AimState", "Shooting");
		}
		
		if(mode == Mode.disabled)
		{
			SmartDashboard.putString("AimState", "Not Aiming");
		}
	}
    
    public void updateCamera()
    {
    	/*
    	NIVision.Rect rect = new NIVision.Rect(290, 275, 100, 100);
    	NIVision.Rect rect2 = new NIVision.Rect(75, 410, 60, 60);
    	    	
    	
    	NIVision.IMAQdxGrab(session, frame, 1);
        
    	NIVision.imaqDrawShapeOnImage(frame, frame, rect,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
    	NIVision.imaqDrawShapeOnImage(frame, frame, rect2,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
    	
        CameraServer.getInstance().setImage(frame);
        */
    }
}
