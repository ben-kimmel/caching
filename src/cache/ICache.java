package cache;

import java.util.List;

import cache.logging.LogEntry;

public interface ICache {

	// usage info
	public int getRequests();

	public int getHits();

	public int getTotalMisses();

	public int getEvictions();

	public int getInsertions();

	public int getCompulsoryMisses();

	public int getConflictMisses();

	// cache info
	public int getSize();

	// requests
	public boolean requestBlock(int blockID);

	// setup
	public void setSize(int size);

	public void reset();

	// reporting
	public String generateSummaryReport();
	
	public List<LogEntry> getLog();

}