package runner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

import cache.ICache;
import cache.logging.LogEntry;

public class TestRunner implements ITestRunner {

	private Enumeration<? extends Integer> requestGenerator;
	private ICache cache;
	private long testDuration;

	public TestRunner() {
		this.requestGenerator = null;
		this.cache = null;
	}

	@Override
	public void run(int iterations) {
		long startTime = System.nanoTime();
		for (int i = 0; i < iterations; i++) {
			this.cache.requestBlock(this.requestGenerator.nextElement());
		}
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		this.testDuration = duration / 1000000;
	}

	@Override
	public void provideCache(ICache cache) {
		this.cache = cache;
	}

	@Override
	public void provideEnumerator(Enumeration<? extends Integer> requestGenerator) {
		this.requestGenerator = requestGenerator;
	}

	public void writeLog(File logFile) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(logFile));
			bw.write(LogEntry.getCSVHeaders());
			for (LogEntry le : this.cache.getLog()) {
				bw.write(le.toCSVEntry());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeSummary(File summaryFile) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(summaryFile));
			bw.write(this.cache.generateSummaryReport());
			bw.write("\nDuration of test: " + this.testDuration + " milliseconds\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
