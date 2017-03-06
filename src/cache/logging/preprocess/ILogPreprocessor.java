package cache.logging.preprocess;

import java.util.List;

import cache.logging.LogEntry;
import cache.logging.writers.ILogWriter;

/**
 * Allows logs to be processed before being handed off to {@link ILogWriter} for
 * output. Useful for things like sampling log data, or modifying it.
 * 
 * @author Ben Kimmel
 *
 */
public interface ILogPreprocessor {

	/**
	 * Performs some preprocessing task on the given log and return the modified
	 * log.
	 * 
	 * @param logs
	 *            The log to preprocess
	 * @return The modified log
	 */
	public List<LogEntry> preprocess(List<LogEntry> logs);

}
