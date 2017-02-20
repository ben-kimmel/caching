package runner;

import java.io.File;

import cache.implementations.OldestEvictionCache;
import runner.enumerators.DistributedRandomAccessEnumerator;

public class ExampleDriver {

	public static void main(String[] args) {
		ITestRunner tr = new TestRunner();
		tr.provideCache(new OldestEvictionCache(1000));
		tr.provideEnumerator(new DistributedRandomAccessEnumerator(0, 100000));
		tr.run(1000000);
		tr.writeLog(new File("output/log.csv"));
		tr.writeSummary(new File("output/summary.txt"));
	}

}
