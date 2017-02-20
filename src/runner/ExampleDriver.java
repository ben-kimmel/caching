package runner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cache.ICache;
import cache.implementations.FIFOCache;
import cache.implementations.LIFOCache;
import cache.implementations.LeastRecentlyUsedCache;
import cache.implementations.MostRecentlyUsedCache;
import cache.implementations.RandomReplacementCache;
import runner.enumerators.DistributedRandomAccessEnumerator;

public class ExampleDriver {

	private static Map<ICache, String[]> caches;

	public static void main(String[] args) {
		setup();

		for (ICache cache : caches.keySet()) {
			ITestRunner tr = new TestRunner();
			tr.provideCache(cache);
			tr.provideEnumerator(new DistributedRandomAccessEnumerator(0, 100000));
			tr.run(1000000);
			tr.writeLog(new File(caches.get(cache)[0]));
			tr.writeSummary(new File(caches.get(cache)[1]));
		}
		System.out.println("Finished Trials");
	}

	private static void setup() {
		caches = new HashMap<ICache, String[]>();
		caches.put((ICache) new FIFOCache(1000), new String[] { "output/FIFO-log.csv", "output/FIFO-summary.txt" });
		caches.put((ICache) new LIFOCache(1000), new String[] { "output/LIFO-log.csv", "output/LIFO-summary.txt" });
		caches.put((ICache) new MostRecentlyUsedCache(1000),
				new String[] { "output/MRU-log.csv", "output/MRU-summary.txt" });
		caches.put((ICache) new LeastRecentlyUsedCache(1000),
				new String[] { "output/LRU-log.csv", "output/LRU-summary.txt" });
		caches.put((ICache) new RandomReplacementCache(1000),
				new String[] { "output/RR-log.csv", "output/RR-summary.txt" });

	}

}
