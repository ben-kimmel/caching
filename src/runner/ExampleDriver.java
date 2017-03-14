package runner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cache.DefaultCacheWrapper;
import cache.ICacheWrapper;
import cache.logging.preprocess.HistoricalPreprocessor;
import cache.logging.writers.AccumulatorSummaryLogWriter;
import cache.logging.writers.CSVLogWriter;
import cache.logging.writers.ILogWriter;
import cache.rp.implementations.LIFOReplacementPolicy;
import runner.enumerators.SequentialSectorAccessEnumerator;

public class ExampleDriver {

	private static Map<ICacheWrapper, ILogWriter> caches;

	public static void main(String[] args) {
		setup();

		for (ICacheWrapper cache : caches.keySet()) {
			ITestRunner tr = new DefaultTestRunner();
			tr.provideCacheWrapper(cache);
			tr.provideEnumerator(new SequentialSectorAccessEnumerator(0, 10000, 200, 2));
			tr.provideLogWriter(caches.get(cache));
			tr.provideLogWriter(new CSVLogWriter(new File("output"), "log.csv"));
			tr.provideLogWriter(new AccumulatorSummaryLogWriter(new File("output"), "summary.txt"));
			tr.run(40000);
		}
		System.out.println("Finished Trials");
	}

	private static void setup() {
		caches = new HashMap<ICacheWrapper, ILogWriter>();
		ILogWriter w = new CSVLogWriter(new File("output"), "history.csv");
		w.provideLogPreprocessor(new HistoricalPreprocessor());
		caches.put(buildCacheWrapper(), w);

	}

	private static ICacheWrapper buildCacheWrapper() {
		ICacheWrapper wrapper = new DefaultCacheWrapper(100, false);
		wrapper.provideCacheStep(new LIFOReplacementPolicy(0));
		return wrapper;
	}

}
