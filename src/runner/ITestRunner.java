package runner;

import java.io.File;
import java.util.Enumeration;

import cache.ICache;

/**
 * 
 * @author kimmelbs
 *
 */
public interface ITestRunner {

	/**
	 * Requests a number of blocks from the cache. This method will only run correctly if the ITestRunner has been properly configured prior to the call.
	 * 
	 * @param iterations The number of requests to generate against the cache.
	 */
	public void run(int iterations);
	
	/**
	 * Sets the cache implementation that the requests will be run against. Must be called before tests can be run.
	 * 
	 * @param cache The cache implementation that the requests will be run against.
	 */
	public void provideCache(ICache cache);
	
	/**
	 * Sets the enumerator that will be used to generate the block IDs to request. Must be called before tests can be run.
	 * 
	 * @param requestGenerator
	 */
	public void provideEnumerator(Enumeration<? extends Integer> requestGenerator);
	
	/**
	 * Writes the log of every request transaction to the specified file. Must be called after tests are run.
	 * 
	 * @param logFile The file into which the log should be written.
	 */
	public void writeLog(File logFile);
	
	/**
	 * Writes the summary of the test to the specified file. Must be called after tests are run.
	 * 
	 * @param summaryFile The file into which the summary should be written.
	 */
	public void writeSummary(File summaryFile);

}
