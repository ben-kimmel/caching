package cache.logging.preprocess;

import java.util.ArrayList;
import java.util.List;

import cache.logging.LogEntry;
import cache.logging.writers.ILogWriter;

/**
 * An implementation of {@link ILogPreprocessor} that returns a reduced sample
 * of the log. The sample is an evenly distributed selection of entries from the
 * original log.
 * 
 * @author Ben Kimmel
 *
 */
public class RegularSamplePreprocessor implements ILogPreprocessor {

	private int samplingRate;

	/**
	 * Constructs a new RegularSamplePreprocessor with the specified sampling
	 * rate. Samples will be selected from the log at regular intervals so that
	 * the appropriate proportion of entries are passed onto the
	 * {@link ILogWriter}.
	 * 
	 * @param samplingRate
	 *            The portion of the log to be returned. Must be 1 or greater.
	 *            The sampling rate will be applied so that
	 *            <i>1/samplingRate</i> entries will be preserved in the output
	 *            log.
	 */
	public RegularSamplePreprocessor(int samplingRate) {
		if (samplingRate < 1) {
			throw new IllegalArgumentException("Sampling rate must be 1 or greater");
		}
		this.samplingRate = samplingRate;
	}

	@Override
	public List<LogEntry> preprocess(List<LogEntry> logs) {
		List<LogEntry> sample = new ArrayList<>(logs.size() / this.samplingRate);
		for (int i = 0; i < logs.size(); i += this.samplingRate) {
			sample.add(logs.get(i));
		}
		return sample;
	}
}
