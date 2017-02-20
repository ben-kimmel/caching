package runner;

import java.io.File;

import cache.implementations.LeastRecentlyUsedCache;
import cache.implementations.MostRecentlyUsedCache;
import cache.implementations.OldestEvictionCache;
import runner.enumerators.DistributedRandomAccessEnumerator;

public class ExampleDriver {

	public static void main(String[] args) {
		ITestRunner tr = new TestRunner();
		tr.provideCache(new OldestEvictionCache(1000));
		tr.provideEnumerator(new DistributedRandomAccessEnumerator(0, 100000));
		tr.run(1000000);
		tr.writeLog(new File("output/OEC-log.csv"));
		tr.writeSummary(new File("output/OEC-summary.txt"));

		ITestRunner tr2 = new TestRunner();
		tr2.provideCache(new MostRecentlyUsedCache(1000));
		tr2.provideEnumerator(new DistributedRandomAccessEnumerator(0, 100000));
		tr2.run(1000000);
		tr2.writeLog(new File("output/MRU-log.csv"));
		tr2.writeSummary(new File("output/MRU-summary.txt"));

		ITestRunner tr3 = new TestRunner();
		tr3.provideCache(new LeastRecentlyUsedCache(1000));
		tr3.provideEnumerator(new DistributedRandomAccessEnumerator(0, 100000));
		tr3.run(1000000);
		tr3.writeLog(new File("output/LRU-log.csv"));
		tr3.writeSummary(new File("output/LRU-summary.txt"));
	}

}
