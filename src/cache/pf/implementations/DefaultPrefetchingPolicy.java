package cache.pf.implementations;

import cache.AbstractCacheStep;
import cache.internal.ICacheInternal;
import cache.logging.LogEntry;
import cache.pf.IPrefetchingPolicy;

public class DefaultPrefetchingPolicy extends AbstractCacheStep implements IPrefetchingPolicy {

	public DefaultPrefetchingPolicy(int priority) {
		super(priority);
	}

	@Override
	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le) {
		return le;
	}

}
