package cache.logging.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cache.logging.LogEntry;

public abstract class AbstractLogWriter implements ILogWriter {

	private BufferedWriter bw;
	private File outputFile;

	public AbstractLogWriter(File outputFile) {
		this.setOutputFile(outputFile);
	}

	@Override
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	protected void setupWriter(File outputFile) throws IOException {
		this.bw = new BufferedWriter(new FileWriter(outputFile));
	}

	protected void tearDownWriter() {
		try {
			if (bw != null) {
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void writeln(String line) throws IOException {
		bw.write(line);
		bw.newLine();
	}

	@Override
	public void writeLog(List<LogEntry> logLines) {
		try {
			setupWriter(outputFile);
			writeLogEntries(logLines);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			tearDownWriter();
		}

	}

	protected abstract void writeLogEntries(List<LogEntry> logLines);
}
