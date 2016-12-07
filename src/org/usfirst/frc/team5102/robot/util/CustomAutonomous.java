package org.usfirst.frc.team5102.robot.util;

import java.lang.reflect.Executable;
import java.util.ArrayList;

public class CustomAutonomous
{
	ArrayList autonExec, autonTimeToWait;
	int counter;
	
	public CustomAutonomous()
	{
		autonExec = new ArrayList<>();
		autonTimeToWait = new ArrayList<>();
		counter = 0;
	}
	
	public void addAutonBlock(Executable exec, double timeToWait)
	{
		autonExec.add(exec);
		autonTimeToWait.add(timeToWait);
	}
	
	public Executable currentExec()
	{
		return (Executable)autonExec.get(counter);
	}
	
	public double currentTime()
	{
		return (double)autonTimeToWait.get(counter);
	}
	
	public boolean isNext()
	{
		if(counter < autonExec.size())
		{
			return true;
		}
		return false;
	}
}
