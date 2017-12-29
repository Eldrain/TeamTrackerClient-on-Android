package task_schedule;

import task_framework.ISolution;

public class ScheduleSolution implements ISolution {
	private int mMinF;
	private int[] mBest;
	private String mMethodName;
	private double mTime;
	private int mCountVar;
	
	public ScheduleSolution(int f, int[] best, String metName, double time, int countVar) {
		mMinF = f;
		mBest = best;
		mMethodName = metName;
		mTime = time;
		mCountVar = countVar;
	}
	
	@Override
	public void print() {
		System.out.println(mMethodName);
		System.out.println("minF = " + mMinF + "; time = " + mTime + " s.; countVar = " + mCountVar);
		System.out.println("Best array: " + mBest);
	}
}