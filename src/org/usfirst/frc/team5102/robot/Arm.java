package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CanTalonJNI;

import edu.wpi.first.wpilibj.DigitalInput;

public class Arm extends RobotElement
{
	CANTalon armMotor;
	CANTalon rollerMotor;
	DigitalInput armLimit;
	
	Arm()
	{
		super(1);
		
		armMotor = new CANTalon(RobotMap.armMotor);
		rollerMotor = new CANTalon(RobotMap.rollerMotor);
		
		armLimit = new DigitalInput(RobotMap.armLimit);
	}
	
	public void moveArm(double amount)
	{
		if(armLimit.get())
		{
			armMotor.set(amount);
		}
	}
	
	public void teleop()
	{
		
	}
	
	public void autonomous()
	{
		
	}
}