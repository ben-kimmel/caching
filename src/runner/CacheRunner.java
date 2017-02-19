package runner;

import java.util.List;
import java.util.Random;

import cache.ICache;
import cache.implementations.OldestEvictionCache;
import cache.logging.LogEntry;

public class CacheRunner {

	public static void main(String[] args) {
		int max = 100000;

		ICache cache = new OldestEvictionCache(1000000);
		cache.enableLog(1000000);
		Random r = new Random();
		long startTime = System.nanoTime();
		for (int i = 0; i < 1000000; i++) {
			double rFactor = r.nextDouble();
			int blockID = (int) Math.floor(max * rFactor);
			cache.requestBlock(blockID);
		}
		long endTime = System.nanoTime();
		System.out.println(cache.generateReport());
		long duration = endTime - startTime;
		long durationMillis = duration / 1000000;
		System.out.println("Duration: " + durationMillis + " milliseconds");
		/*System.out.println("Logs:");
		List<LogEntry> logs = cache.getLog();
		for (int i = 0; i < 10; i++) {
			System.out.println(logs.get(i));
		}*/
	}

}
