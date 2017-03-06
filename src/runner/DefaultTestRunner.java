package runner;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import cache.ICacheWrapper;
import cache.logging.writers.ILogWriter;

/**
 * The default implementation of {@link ITestRunner}.
 * 
 * @author Ben Kimmel
 *
 */
public class DefaultTestRunner implements ITestRunner {

	private Enumeration<? extends Integer> requestGenerator;
	private List<ILogWriter> logWriters;
	private ICacheWrapper cache;
	private long testDuration;

	/**
	 * Constructs a new DefaultTestRunner.
	 */
	public DefaultTestRunner() {
		this.requestGenerator = null;
		this.cache = null;
		this.logWriters = new LinkedList<>();
	}

	@Override
	public void run(int iterations) {
		long startTime = System.nanoTime();
		for (int i = 0; i < iterations; i++) {
			this.cache.requestBlock(this.requestGenerator.nextElement());
		}
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		this.testDuration = duration / 1000000;
		System.out.println("Test Duration: " + this.testDuration);
		for (ILogWriter lw : this.logWriters) {
			lw.writeLog(cache.getLog());
		}
	}

	@Override
	public void provideCacheWrapper(ICacheWrapper cache) {
		this.cache = cache;
	}

	@Override
	public void provideEnumerator(Enumeration<? extends Integer> requestGenerator) {
		this.requestGenerator = requestGenerator;
	}

	@Override
	public void provideLogWriter(ILogWriter writer) {
		this.logWriters.add(writer);
	}

}
