package cache.logging.writers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cache.HitStatus;
import cache.logging.LogEntry;

/**
 * An {@link ILogWriter} implementation that outputs a summary of the log in
 * human readable form.
 * 
 * @author Ben Kimmel
 *
 */
public class AccumulatorSummaryLogWriter extends AbstractLogWriter {

	private Map<String, Number> fieldAccumulations;

	/**
	 * Constructs a new AccumulatorSummaryLogWriter with the specified
	 * destination file.
	 * 
	 * @param outputFile
	 *            The File specifying where the output should be written
	 * @param filename
	 *            The name of the output file
	 */
	public AccumulatorSummaryLogWriter(File outputFile, String filename) {
		super(outputFile, filename, "Accumulator");
		reset();
	}

	private void reset() {
		this.fieldAccumulations = new HashMap<String, Number>();
		for (HitStatus hs : HitStatus.values()) {
			this.fieldAccumulations.put(hs.name(), 0);
		}
	}

	@Override
	protected void writeLogEntries(List<LogEntry> logLines) {
		reset();
		for (LogEntry line : logLines) {
			processLine(line);
		}
		try {
			super.writeln("========================");
			super.writeln("  Accumulation Summary  ");
			super.writeln("========================");
			for (String field : this.fieldAccumulations.keySet()) {
				super.writeln(field + ": " + this.fieldAccumulations.get(field));
			}
			super.writeln("Number of Log Lines: " + logLines.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processLine(LogEntry line) {
		for (String field : line.getFieldList()) {
			Object v = line.getFieldValue(field);
			if (v instanceof Integer) {
				Integer curVal = (Integer) this.fieldAccumulations.get(field);
				if (curVal == null) {
					this.fieldAccumulations.put(field, (Integer) v);
				} else {
					curVal = curVal + (Integer) v;
					this.fieldAccumulations.put(field, curVal);
				}
			} else if (v instanceof Boolean) {
				if ((Boolean) v) {
					Integer curVal = (Integer) this.fieldAccumulations.get(field);
					if (curVal == null) {
						this.fieldAccumulations.put(field, 1);
					} else {
						curVal++;
						this.fieldAccumulations.put(field, curVal);
					}
				} else {
					Integer curVal = (Integer) this.fieldAccumulations.get(field);
					if (curVal == null) {
						this.fieldAccumulations.put(field, 0);
					}
				}
			} else if (v instanceof HitStatus) {
				String fieldString = ((HitStatus) v).name();
				Integer curVal = (Integer) this.fieldAccumulations.get(fieldString);
				if (curVal == null) {
					this.fieldAccumulations.put(fieldString, 1);
				} else {
					curVal++;
					this.fieldAccumulations.put(fieldString, curVal);
				}
			}
		}
	}

}
