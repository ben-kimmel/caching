package runner;

import java.io.File;

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
	}

}
