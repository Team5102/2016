package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class ScissorLift extends RobotElement
{
	DigitalInput bottomLimit, topLimit;
	Talon liftMotor;
	Solenoid lock;
	
	ScissorLift()
	{
		super(1);
		
		bottomLimit = new DigitalInput(RobotMap.liftLowerLimit);
		topLimit = new DigitalInput(RobotMap.liftUpperLimit);
		
		liftMotor = new Talon(RobotMap.scissorLiftMotor);
		
		lock = new Solenoid(RobotMap.liftLock);
	}
	
	public void moveLift(double speed)
	{
		if(bottomLimit.get())
		{
			if(speed > 0.0)
			{
				liftMotor.set(speed/4);
			}
		}
		else if(topLimit.get())
		{
			if(speed < 0.0)
			{
				liftMotor.set(speed/4);
			}
		}
		else
		{
			liftMotor.set(speed/4);
		}
		
	}
	
	public void teleop()
	{
		moveLift(controller.applyDeadband(controller.getRightStickY()));
		
		if(controller.getLeftTriggerButton())
		{
			lock.set(true);
		}
		else if(controller.getRightTriggerButton())
		{
			lock.set(false);
		}
	}
}
