package cache.logging.writers;

import java.io.File;
import java.util.List;

import cache.logging.LogEntry;

public interface ILogWriter {

	public void setOutputFile(File outputFile);
	
	public void writeLog(List<LogEntry> logLines);

}
