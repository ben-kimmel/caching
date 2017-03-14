package runner;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import cache.ICacheWrapper;
import cache.logging.LogEntry;
import cache.logging.writers.ILogWriter;

/**
 * The default implementation of {@link ITestRunner}. Only accepts a single
 * {@link ICacheWrapper}.
 * 
 * @author Ben Kimmel
 *
 */
public class MultipleCacheTestRunner implements ITestRunner {

	private Enumeration<? extends Integer> requestGenerator;
	private List<ILogWriter> logWriters;
	private List<ICacheWrapper> caches;
	private long testDuration;

	/**
	 * Constructs a new DefaultTestRunner.
	 */
	public MultipleCacheTestRunner() {
		this.requestGenerator = null;
		this.caches = new ArrayList<ICacheWrapper>();
		this.logWriters = new LinkedList<>();
	}

	@Override
	public void run(int iterations) {
		long startTime = System.nanoTime();
		for (int i = 0; i < iterations; i++) {
			int req = this.requestGenerator.nextElement();
			for (ICacheWrapper cw : this.caches) {
				cw.requestBlock(req);
			}
		}
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		this.testDuration = duration / 1000000;
		System.out.println("Test Duration: " + this.testDuration);
		for (ICacheWrapper cw : this.caches) {
			List<LogEntry> log = cw.getLog();
			startTime = System.nanoTime();
			for (ILogWriter lw : this.logWriters) {
				lw.setOutputFilename(cw.getName() + this.requestGenerator.toString() + lw.getName());
				lw.writeLog(log);
			}
		}

		endTime = System.nanoTime();
		duration = endTime - startTime;
		duration = duration / 1000000;
		System.out.println("Log Writing Duration: " + duration);
	}

	@Override
	public void provideCacheWrapper(ICacheWrapper cache) {
		this.caches.add(cache);
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
