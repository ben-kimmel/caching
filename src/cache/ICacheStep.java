package cache;

import cache.internal.ICacheInternal;
import cache.logging.LogEntry;

public interface ICacheStep extends Comparable<ICacheStep> {

	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le);
	
	public void setPriority(int priority);
	
	public int getPriority();

}
