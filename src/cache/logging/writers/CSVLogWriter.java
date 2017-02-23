package cache.logging.writers;

import java.io.IOException;
import java.util.List;

import cache.logging.ILogLine;

public class CSVLogWriter extends AbstractLogWriter {

	private String generateCSVEntry(ILogLine line) {
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}

	@Override
	protected void writeLog(List<ILogLine> logLines) {
		try {
			super.writeln(generateHeaders(logLines.get(0)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for (ILogLine line : logLines) {
			String outputLine = generateCSVEntry(line);
			try {
				super.writeln(outputLine);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private String generateHeaders(ILogLine iLogLine) {
		// TODO Auto-generated method stub
		return null;
	}

}
