package cache.logging.writers;

import java.io.File;

import cache.ICache;

public interface ISummaryWriter {

	public void writeSummary(ICache cache, File outputFile);
	
}
