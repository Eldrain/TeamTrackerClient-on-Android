package task_schedule;

import task_framework.IConfig;

public class ScheduleConfig implements IConfig {
	public int mMethod;
	public int mCountJobs;
	public int[] mTimes;
	public int mCountProcs;
	public String mRelations;
	
	@Override
	public void copy(IConfig config) {
		ScheduleConfig c = (ScheduleConfig) config;
		mMethod = c.mMethod;
		mCountJobs = c.mCountJobs;
		mCountProcs = c.mCountProcs;
		mRelations = c.mRelations;
		mTimes = c.mTimes;
	}
	
}