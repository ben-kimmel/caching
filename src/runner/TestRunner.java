package runner;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import cache.ICacheWrapper;
import cache.logging.writers.ILogWriter;

public class TestRunner implements ITestRunner {

	private Enumeration<? extends Integer> requestGenerator;
	private List<ILogWriter> logWriters;
	private ICacheWrapper cache;
	private long testDuration;

	public TestRunner() {
		this.requestGenerator = null;
		this.cache = null;
		this.logWriters = new LinkedList<>();
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void provideCacheWrapper(ICacheWrapper cache) {
		this.cache = cache;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void provideEnumerator(Enumeration<? extends Integer> requestGenerator) {
		this.requestGenerator = requestGenerator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void provideLogWriter(ILogWriter writer) {
		this.logWriters.add(writer);
	}

}
