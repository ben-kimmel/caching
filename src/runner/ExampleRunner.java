package runner;

import java.io.File;

import cache.implementations.OldestEvictionCache;

public class ExampleRunner {

	public static void main(String[] args) {
		ITestRunner tr = new TestRunner(new OldestEvictionCache(1000), 1000000, new File("output"), 1000000);
		tr.run();
	}

}
