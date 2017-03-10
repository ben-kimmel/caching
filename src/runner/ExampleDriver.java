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
import cache.rp.implementations.FIFOReplacementPolicy;
import runner.enumerators.SequentialSectorAccessEnumerator;

public class ExampleDriver {

	private static Map<ICacheWrapper, ILogWriter> caches;

	public static void main(String[] args) {
		setup();

		for (ICacheWrapper cache : caches.keySet()) {
			ITestRunner tr = new DefaultTestRunner();
			tr.provideCacheWrapper(cache);
			tr.provideEnumerator(new SequentialSectorAccessEnumerator(0, 20, 2, 2));
			tr.provideLogWriter(caches.get(cache));
			tr.provideLogWriter(new CSVLogWriter(new File("output/outcsv.csv")));
			tr.provideLogWriter(new AccumulatorSummaryLogWriter(new File("output/out.txt")));
			tr.run(4);
		}
		System.out.println("Finished Trials");
	}

	private static void setup() {
		caches = new HashMap<ICacheWrapper, ILogWriter>();
		ILogWriter w = new CSVLogWriter(new File("output/histoutcsv.csv"));
		w.provideLogPreprocessor(new HistoricalPreprocessor());
		caches.put(buildCacheWrapper(), w);

	}

	private static ICacheWrapper buildCacheWrapper() {
		ICacheWrapper wrapper = new DefaultCacheWrapper(100, false);
		wrapper.provideCacheStep(new FIFOReplacementPolicy(0));
		return wrapper;
	}

}
