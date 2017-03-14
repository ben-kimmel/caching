package runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cache.DefaultCacheWrapper;
import cache.ICacheWrapper;
import cache.logging.preprocess.HistoricalPreprocessor;
import cache.logging.writers.AccumulatorSummaryLogWriter;
import cache.logging.writers.CSVLogWriter;
import cache.logging.writers.ILogWriter;
import cache.rp.IReplacementPolicy;
import cache.rp.implementations.DefaultReplacementPolicy;
import cache.rp.implementations.FIFOReplacementPolicy;
import cache.rp.implementations.LIFOReplacementPolicy;
import cache.rp.implementations.LRUReplacementPolicy;
import cache.rp.implementations.MRUReplacementPolicy;
import cache.rp.implementations.RandomReplacementPolicy;
import runner.enumerators.DistributedRandomAccessEnumerator;

public class MultiCacheDriver {

	private static List<ICacheWrapper> caches;

	public static void main(String[] args) {
		setup();

		ITestRunner runner = new MultipleCacheTestRunner();
		runner.provideEnumerator(new DistributedRandomAccessEnumerator(0, 10000));
		ILogWriter w = new CSVLogWriter(new File("output"), "/histlog.csv");
		w.provideLogPreprocessor(new HistoricalPreprocessor());
		runner.provideLogWriter(w);
		runner.provideLogWriter(new CSVLogWriter(new File("output"), "/log.csv"));
		runner.provideLogWriter(new AccumulatorSummaryLogWriter(new File("output"), "/summary.txt"));
		for (ICacheWrapper cache : caches) {
			runner.provideCacheWrapper(cache);
		}
		runner.run(40000);
		System.out.println("Finished Trials");
	}

	private static void setup() {
		caches = new ArrayList<ICacheWrapper>();
		caches.add(buildCacheWrapper(new LIFOReplacementPolicy(0)));
		caches.add(buildCacheWrapper(new FIFOReplacementPolicy(0)));
		caches.add(buildCacheWrapper(new LRUReplacementPolicy(0)));
		caches.add(buildCacheWrapper(new MRUReplacementPolicy(0)));
		caches.add(buildCacheWrapper(new RandomReplacementPolicy(0)));
		caches.add(buildCacheWrapper(new DefaultReplacementPolicy(0)));
	}

	private static ICacheWrapper buildCacheWrapper(IReplacementPolicy rp) {
		ICacheWrapper wrapper = new DefaultCacheWrapper(100, false);
		wrapper.provideCacheStep(rp);
		return wrapper;
	}

}
