package task_framework;

public abstract class ATask implements ITask {
	private IConfig mConfig;
	private IProtection mProtect;
	private ISolution mLastSol;
	
	public ATask(IProtection protect) {
		mProtect = protect;
	}

	@Override
	public boolean setConfig(IConfig config) {
		if(mProtect.checkConfig(config)) {
			mConfig.copy(config);
			return true;
		} else {
			return false;
		}	
	}

	@Override
	public IProtection getProtection() {
		return mProtect;
	}

	@Override
	public IConfig getStandartConfig() {
		return mProtect.getStandartConfig();
	}

	@Override
	public void printLastResults() {
		mLastSol.print();
	}

}
