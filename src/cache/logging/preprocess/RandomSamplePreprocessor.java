package cache.logging.preprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cache.logging.LogEntry;

/**
 * An implementation of {@link ILogPreprocessor} that returns a random sample of
 * the log. The random sample is generated using Floyd's Algorithm.
 * 
 * @author Ben Kimmel
 *
 */
public class RandomSamplePreprocessor implements ILogPreprocessor {

	private int numSamples;

	/**
	 * Constructs a new RandomSamplePreprocessor that will reduce logs to a
	 * random sample with the given size.
	 * 
	 * @param numSamples
	 *            The number of samples to take from the input log
	 */
	public RandomSamplePreprocessor(int numSamples) {
		this.numSamples = numSamples;
	}

	@Override
	public List<LogEntry> preprocess(List<LogEntry> logs) {
		List<LogEntry> sample = new ArrayList<LogEntry>();
		Random r = new Random();
		int n = logs.size();
		for (int i = n - this.numSamples; i < n; i++) {
			int index = r.nextInt(i + 1);
			LogEntry sampleEntry = logs.get(index);
			if (sample.contains(sampleEntry)) {
				sample.add(logs.get(i));
			} else {
				sample.add(sampleEntry);
			}
		}
		return sample;
	}

	@Override
	public String getName() {
		return "RandomSamplePreprocessor";
	}

}
