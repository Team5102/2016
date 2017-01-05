package org.usfirst.frc.team5102.robot.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision
{
	static NetworkTable grip;
	
	public static void init()
	{
		grip = NetworkTable.getTable("GRIP/targets");
	}
	
	public static double getTargetX()
	{
		double[] targets = grip.getNumberArray("centerX", new double[0]);
		
		if(targets.length > 0)
        {
			return targets[getLargest()];
        }
		return 0;
	}
	
	public static double getTargetY()
	{
		double[] targets = grip.getNumberArray("centerY", new double[0]);

		if(targets.length > 0)
        {
			return targets[getLargest()];
        }
		return 0;
	}
	
	public static boolean targetFound()
	{
		double[] targets = grip.getNumberArray("centerX", new double[0]);
		
		if(targets.length > 0)
        {
			return true;
        }
		return false;
	}
	
	public static int getLargest()
	{
		double[] areas = grip.getNumberArray("area", new double[0]);
		
		int largestTarget = 0;
		
		if(areas.length > 1)
		{
			double largest = 0;
			
			for(int	c = 0; c < areas.length; c++)	
			{
				if(areas[c] > largest)
				{
					largest = areas[c];
					largestTarget = c;
				}
			}
		}
		return largestTarget;
	}
}
