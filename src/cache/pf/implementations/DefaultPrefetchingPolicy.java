package cache.pf.implementations;

import cache.AbstractCacheStep;
import cache.internal.ICacheInternal;
import cache.logging.LogEntry;
import cache.pf.IPrefetchingPolicy;

/**
 * The default implementation of {@link IPrefetchingPolicy}. It does not
 * prefetch any records.
 * 
 * @author Ben Kimmel
 *
 */
public class DefaultPrefetchingPolicy extends AbstractCacheStep implements IPrefetchingPolicy {

	/**
	 * Constructs a DefaultPrefetchingPolicy with the given priority.
	 * 
	 * @param priority
	 *            he priority of the AbstractCacheStep. Lower is sooner
	 */
	public DefaultPrefetchingPolicy(int priority) {
		super(priority);
	}

	@Override
	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le) {
		return le;
	}

}
