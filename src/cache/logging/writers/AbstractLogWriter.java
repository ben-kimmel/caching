package cache.logging.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cache.logging.ILogLine;

public abstract class AbstractLogWriter implements ILogWriter {

	private BufferedWriter bw;

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
	public void writeLog(List<ILogLine> logLines, File outputFile) {
		try {
			setupWriter(outputFile);
			writeLog(logLines);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			tearDownWriter();
		}

	}

	protected abstract void writeLog(List<ILogLine> logLines);
}
