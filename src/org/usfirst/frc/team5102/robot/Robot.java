
package org.usfirst.frc.team5102.robot;

import java.io.IOException;

import org.usfirst.frc.team5102.robot.Drive.DriveMode;
import org.usfirst.frc.team5102.robot.RobotElement.Mode;
import org.usfirst.frc.team5102.robot.Shifter.Gear;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
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
		
	//CameraServer server;
	
	int session;
    Image frame;
    
    static NetworkTable grip;
	
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
    	
    	grip = NetworkTable.getTable("GRIP/targets");
    	
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
    }
    
    public void disabledPeriodic()
    {
    	updateSmartDashboard(Mode.disabled);
    	drive.setDriveMode();
    	
    	drive.controller.updateRumbleTimer();
        shooter.controller.updateRumbleTimer();
        
        updateCamera();
        
        //System.out.println(drive.controller.getPOV());
        
        double[] targets = grip.getNumberArray("centerX", new double[0]);
        
        if(targets.length > 0)
        {
        	//System.out.println("Got contour with centerX=" + targets[0]);
        }
        /*
        for (double centerX : grip.getNumberArray("targets/centerX", new double[0]))
    	{
            System.out.println("Got contour with centerX=" + centerX);
    	}
    	*/
    }

    public void autonomousInit()		//runs when autonomous mode is enabled
    {
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
    	
    	double[] targets = grip.getNumberArray("centerX", new double[0]);
        
        if(targets.length > 0)
        {
        	//System.out.println("Got contour with centerX=" + targets[0]);
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
        
        updateCamera();
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
		
		double[] targetsX = grip.getNumberArray("centerX", new double[0]);
        double[] targetsY = grip.getNumberArray("centerY", new double[0]);
        
        if(targetsX.length > 0)
        {
        	//System.out.println("Got contour with " + targetsX[0] + ", " + targetsY[0]);
        	
        	try
        	{
        		SmartDashboard.putNumber("Target X", targetsX[0]);
        		SmartDashboard.putNumber("Target Y", targetsY[0]);
        	}
        	catch(ArrayIndexOutOfBoundsException e) {}
        }
        else
        {
        	SmartDashboard.putNumber("Target X", 0);
        	SmartDashboard.putNumber("Target Y", 0);
        }
		
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
