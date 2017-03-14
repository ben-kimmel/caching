package runner;

import java.util.Enumeration;

import cache.ICacheWrapper;
import cache.logging.writers.ILogWriter;

/**
 * Defines a structure for running repeated requests against a provided
 * {@link ICacheWrapper} and producing logs.
 * 
 * @author Ben Kimmel
 *
 */
public interface ITestRunner {

	/**
	 * Requests a number of blocks from the cache. This method will only run
	 * correctly if the ITestRunner has been properly configured prior to the
	 * call.
	 * 
	 * @param iterations
	 *            The number of requests to generate against the cache.
	 */
	public void run(int iterations);

	/**
	 * Adds a cache wrapper that the requests will be run against. Must be
	 * called before tests can be run.
	 * 
	 * @param cache
	 *            The cache wrapper that the requests will be run against.
	 */
	public void provideCacheWrapper(ICacheWrapper cache);

	/**
	 * Sets the enumerator that will be used to generate the block IDs to
	 * request. Must be called before tests can be run.
	 * 
	 * @param requestGenerator
	 */
	public void provideEnumerator(Enumeration<? extends Integer> requestGenerator);

	/**
	 * Adds a log writer to the list of writer to be called at end of test run.
	 * Must be called before tests are run to produce logs.
	 * 
	 * @param writer
	 *            The writer with which to produce log files.
	 */
	public void provideLogWriter(ILogWriter writer);

}
