package task_framework;

public interface ITask {

	ISolution solve();
	
	boolean setConfig(IConfig config);
	
	IProtection getProtection();
	
	IConfig getStandartConfig();
	
	void printLastResults();
	
}
