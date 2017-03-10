package cache.logging.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cache.logging.LogEntry;
import cache.logging.preprocess.ILogPreprocessor;

/**
 * An abstraction of the {@link ILogWriter} interface. AbstractLogWriter handles
 * all {@link File} I/O and delegates log processing to implementing classes.
 * 
 * @author Ben Kimmel
 *
 */
public abstract class AbstractLogWriter implements ILogWriter {

	private BufferedWriter bw;
	private File outputFile;
	private ILogPreprocessor preprocessor;

	/**
	 * Initializes an AbstractLogWriter with the given {@link File} as the
	 * output file.
	 * 
	 * @param outputFile
	 *            The File specifying where the output should be written
	 */
	public AbstractLogWriter(File outputFile) {
		this.setOutputFile(outputFile);
	}

	@Override
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	@Override
	public void provideLogPreprocessor(ILogPreprocessor preprocessor) {
		this.preprocessor = preprocessor;
	}

	private void setupWriter(File outputFile) throws IOException {
		this.bw = new BufferedWriter(new FileWriter(outputFile));
	}

	private void tearDownWriter() {
		try {
			if (bw != null) {
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the given {@link String} to the AbstractLogWriter's given output
	 * file. Provided method for implementing classes.
	 * 
	 * @param line
	 *            The string to write to the output file
	 * @throws IOException
	 *             Thrown
	 */
	protected void writeln(String line) throws IOException {
		bw.write(line);
		bw.newLine();
	}

	@Override
	public void writeLog(List<LogEntry> logLines) {
		try {
			setupWriter(outputFile);
			if (this.preprocessor != null) {
				writeLogEntries(this.preprocessor.preprocess(logLines));
			} else {
				writeLogEntries(logLines);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			tearDownWriter();
		}

	}

	/**
	 * Processes all {@link LogEntry} in the given list and writes to the
	 * writer's output file.
	 * 
	 * @param logLines
	 *            The LogEntries to process
	 */
	protected abstract void writeLogEntries(List<LogEntry> logLines);
}
