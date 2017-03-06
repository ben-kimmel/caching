package runner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cache.DefaultCacheWrapper;
import cache.ICacheWrapper;
import cache.logging.writers.CSVLogWriter;
import cache.logging.writers.ILogWriter;
import cache.rp.implementations.FIFOReplacementPolicy;
import runner.enumerators.SequentialSectorAccessEnumerator;

public class ExampleDriver {

	private static Map<ICacheWrapper, ILogWriter> caches;

	public static void main(String[] args) {
		setup();

		for (ICacheWrapper cache : caches.keySet()) {
			ITestRunner tr = new DefaultTestRunner();
			tr.provideCacheWrapper(cache);
			tr.provideEnumerator(new SequentialSectorAccessEnumerator(0, 100000, 1000, 2));
			tr.provideLogWriter(caches.get(cache));
			tr.run(1000000);
		}
		System.out.println("Finished Trials");
	}

	private static void setup() {
		caches = new HashMap<ICacheWrapper, ILogWriter>();

		caches.put(buildCacheWrapper(), new CSVLogWriter(new File("output/out.txt")));

	}

	private static ICacheWrapper buildCacheWrapper() {
		ICacheWrapper wrapper = new DefaultCacheWrapper(100, false);
		wrapper.provideCacheStep(new FIFOReplacementPolicy(0));
		return wrapper;
	}

}
