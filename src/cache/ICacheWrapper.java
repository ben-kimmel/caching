package cache;

import java.util.List;

import cache.internal.ICacheInternal;
import cache.logging.LogEntry;

public interface ICacheWrapper {

	// requests

	public boolean requestBlock(int blockID);

	public boolean softRequest(int blockID);

	// setup
	
	public void provideCacheImplementation(ICacheInternal cache);
	
	public void provideCacheStep(ICacheStep step);

	// reporting

	public List<LogEntry> getLog();

}