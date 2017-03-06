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
	 */
	public AccumulatorSummaryLogWriter(File outputFile) {
		super(outputFile);
		this.fieldAccumulations = new HashMap<String, Number>();
	}

	@Override
	protected void writeLogEntries(List<LogEntry> logLines) {
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
			if (v instanceof Number) {
				Number curVal = this.fieldAccumulations.get(field);
				if (curVal == null) {
					this.fieldAccumulations.put(field, (Number) v);
				} else {
					if (curVal instanceof Integer) {
						curVal = (Integer) curVal + (Integer) v;
					} else if (curVal instanceof Double) {
						curVal = (Double) curVal + (Double) v;
					} else if (curVal instanceof Float) {
						curVal = (Float) curVal + (Float) v;
					} else if (curVal instanceof Long) {
						curVal = (Long) curVal + (Long) v;
					} else if (curVal instanceof Short) {
						curVal = (Short) curVal + (Short) v;
					} else if (curVal instanceof Byte) {
						curVal = (Byte) curVal + (Byte) v;
					}
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
				}
			} else if (v instanceof HitStatus) {
				String fieldString = field + " - " + ((HitStatus) v).name();
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
