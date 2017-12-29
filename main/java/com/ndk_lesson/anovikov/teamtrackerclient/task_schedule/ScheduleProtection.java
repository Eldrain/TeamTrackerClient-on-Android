package task_schedule;

import task_framework.IConfig;
import task_framework.IProtection;

public class ScheduleProtection implements IProtection {
	private int mMaxJ;
	private int mCountMethods;
	
	public ScheduleProtection(int maxJ, int metCount) {
		mMaxJ = maxJ;
		mCountMethods = metCount;
	}
	
	@Override
	public boolean checkConfig(IConfig config) {
		ScheduleConfig c = (ScheduleConfig) config;
		
		if(c.mCountJobs > mMaxJ) {
			System.out.println("Protection.checkConfig: count of jobs excess the permissible value!");
			return false;
		}
		if(c.mMethod >= mCountMethods) {
			System.out.println("Protection.checkConfig: index of method excess the permissible value!");
			return false;
		}
			
		return true;
	}

	@Override
	public IConfig getStandartConfig() {
		ScheduleConfig c = new ScheduleConfig();
		
		c.mMethod = 0;
		c.mCountJobs = mMaxJ;
		c.mCountProcs = mMaxJ/2;
		c.mRelations = null;
		c.mTimes = null;
		return c;
	}
}