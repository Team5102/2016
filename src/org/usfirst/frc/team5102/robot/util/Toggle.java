package org.usfirst.frc.team5102.robot.util;

import edu.wpi.first.wpilibj.Timer;

public class Toggle
{
	boolean toggleState, waitingToToggle;
	
	double delay;
	
	Timer toggleTimer;
	
	public Toggle(boolean initialState, double toggleDelay)
	{
		toggleState = initialState;
		waitingToToggle = false;
		
		delay = toggleDelay;
		
		toggleTimer = new Timer();
	}
	
	public void toggle(boolean input)
	{
		if(input == true)
		{
			if(!waitingToToggle)
			{
				toggleState = !toggleState;
				waitingToToggle = true;
				
				toggleTimer.start();
			}
		}
		else if(toggleTimer.get() >= delay)
		{
			waitingToToggle = false;
			
			toggleTimer.stop();
			toggleTimer.reset();
		}
	}
	
	public boolean getToggleState()
	{
		return toggleState;
	}
}
