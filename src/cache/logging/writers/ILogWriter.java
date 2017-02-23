package cache.logging.writers;

import java.io.File;
import java.util.List;

import cache.logging.ILogLine;

public interface ILogWriter {

	public void writeLog(List<ILogLine> logLines, File outputFile);
	
}
