package runner;

import java.io.File;
import java.util.Enumeration;

import cache.ICache;

public interface ITestRunner {

	public void run(int iterations);
	public void provideCache(ICache cache);
	public void provideEnumerator(Enumeration<? extends Integer> requestGenerator);
	public void writeLog(File logFile);
	public void writeSummary(File summaryFile);

}
