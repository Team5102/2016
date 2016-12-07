
package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.Drive.DriveMode;
import org.usfirst.frc.team5102.robot.RobotElement.Mode;
import org.usfirst.frc.team5102.robot.Shifter.Gear;
import org.usfirst.frc.team5102.robot.Shooter.ShootMode;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

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
	private Climber climber;
	private Suspension suspension;
	
	private SendableChooser autoChooser, shootModeChooser;
	private int autonMode;
	
	int session;
    Image frame;
    
    NetworkTable table;
    
    static Mode robotMode;
	
    public void robotInit()				//runs when robot is turned on
    {
    	drive = new Drive();
    	shooter = new Shooter();
    	auton = new Autonomous();
    	climber = new Climber();
    	suspension = new Suspension();
    	
    	autoChooser = new SendableChooser();
    	autoChooser.addDefault("Auton Disabled", 0);
    	autoChooser.addObject("FW 6s, 55%", 1);
    	autoChooser.addObject("----------", 2);
    	autoChooser.addObject("----------", 3);
    	SmartDashboard.putData("Autonomous Selector", autoChooser);
    	
    	shootModeChooser = new SendableChooser();
    	shootModeChooser.addDefault("Auto Shoot", 1);
    	shootModeChooser.addObject("Manual Shoot", 2);
    	SmartDashboard.putData("Shoot Mode Selector", shootModeChooser);
    	
    	robotMode = Mode.disabled;
    	
    	//frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        //session = NIVision.IMAQdxOpenCamera("cam1",NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        //NIVision.IMAQdxConfigureGrab(session);
        
        //table = NetworkTable.getTable("GRIP/myContoursReport");
    }
    
    public void disabledInit()
    {
    	robotMode = Mode.disabled;
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
    	robotMode = Mode.auton;
    	
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
    	robotMode = Mode.teleop;
    }
    
    public void teleopPeriodic()		//runs periodically during teleop mode
    {
    	updateCamera();
    	
    	updateSmartDashboard(Mode.teleop);
    	
        drive.teleop();
        shooter.teleop();
        climber.teleop();
        suspension.teleop();
        
        drive.controller.updateRumbleTimer();
        shooter.controller.updateRumbleTimer();
    }
    
    public void testInit()				//runs when test mode is enabled
    {
    	robotMode = Mode.test;
    }
    
    public void testPeriodic()			//runs periodically during test mode
    {
    
    }
    
    public void updateSmartDashboard(Mode mode)
	{
		SmartDashboard.putNumber("Left Drive Speed", drive.leftDriveMotors.get());
		SmartDashboard.putNumber("Right Drive Speed", (-drive.rightDriveMotors.get()));
		
		SmartDashboard.putNumber("Suspension Air Pressure", Drive.shifter.getSuspensionPSI());
		SmartDashboard.putNumber("Working Air Pressure", Drive.shifter.getWorkingPSI());
		
		SmartDashboard.putNumber("Suspension PSI", Drive.shifter.getSuspensionPSI());
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
		
		switch((int) shootModeChooser.getSelected())
		{
			case 1:
				shooter.shootMode = ShootMode.Automatic;
				break;
			case 2:
				shooter.shootMode = ShootMode.Manual;
		}
	}
    
    public void updateCamera()
    {
    	NIVision.IMAQdxGrab(session, frame, 1);
        CameraServer.getInstance().setImage(frame);
    }
    
    public static Mode getRobotMode()
    {
    	return robotMode;
    }
}
