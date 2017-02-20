package runner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import cache.ICache;
import cache.logging.LogEntry;

public class TestRunner implements ITestRunner {

	private ICache cache;
	private int iterations;
	private File outputFolder;
	private int maxBlockID;

	public TestRunner(ICache cache, int iterations, File outputFolder, int maxBlockID) {
		this.setCacheImplementation(cache);
		this.setIterations(iterations);
		this.setOutputFolder(outputFolder);
		this.setMaxBlockID(maxBlockID);
		this.cache.enableLog(iterations);
	}

	private void setCacheImplementation(ICache cache) {
		this.cache = cache;
	}

	private void setIterations(int iterations) {
		this.iterations = iterations;
	}

	private void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}

	private void setMaxBlockID(int maxBlockID) {
		this.maxBlockID = maxBlockID;

	}

	public void setCacheSize(int size) {
		this.cache.setSize(size);
	}

	@Override
	public void run() {
		Random r = new Random();
		long startTime = System.nanoTime();
		for (int i = 0; i < this.iterations; i++) {
			double rFactor = r.nextDouble();
			int blockID = (int) Math.floor(this.maxBlockID * rFactor);
			this.cache.requestBlock(blockID);
		}
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		long durationMillis = duration / 1000000;
		writeSummary(durationMillis);
		writeLog();

	}

	private void writeLog() {
		File logFile = new File(this.outputFolder.getAbsolutePath() + "/log.csv");
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

	private void writeSummary(long durationMillis) {
		File summaryFile = new File(this.outputFolder.getAbsolutePath() + "/summary.txt");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(summaryFile));
			bw.write(this.cache.generateSummaryReport());
			bw.write("\nDuration of test: " + durationMillis + " milliseconds\n");
			bw.write("Number of iterations: " + this.iterations + "\n");
			bw.write("Max BlockID: " + this.maxBlockID + "\n");
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
