package cache.logging.writers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cache.logging.LogEntry;

public class CSVLogWriter extends AbstractLogWriter {

	public CSVLogWriter(File outputFile) {
		super(outputFile);
	}

	private String generateCSVEntry(LogEntry line) {
		StringBuilder sb = new StringBuilder();
		for (String header : line.getFieldList()) {
			sb.append(line.getFieldValue(header));
			sb.append(",");
		}
		return sb.toString();
	}

	@Override
	protected void writeLogEntries(List<LogEntry> logLines) {
		try {
			super.writeln(generateHeaders(logLines.get(0)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for (LogEntry line : logLines) {
			String outputLine = generateCSVEntry(line);
			try {
				super.writeln(outputLine);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private String generateHeaders(LogEntry line) {
		StringBuilder sb = new StringBuilder();
		for (String header : line.getFieldList()) {
			sb.append(header);
			sb.append(",");
		}
		return sb.toString();
	}

}
