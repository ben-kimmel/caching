package cache.rp.implementations;

import cache.AbstractCacheStep;
import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;

/**
 * The default implementation of {@link IReplacementPolicy}. No replacement
 * occurs. Once all entries have been filled in the backing cache, it will no
 * longer add new entries to the cache.
 * 
 * @author Ben Kimmel
 *
 */
public class DefaultReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

	/**
	 * Constructs a new DefaultReplacementPolicy with the given priority. The
	 * lower the priority, the earlier it will execute.
	 * 
	 * @param priority
	 *            The priority with which this should be executed. Lower is
	 *            sooner
	 */
	public DefaultReplacementPolicy(int priority) {
		super(priority, "DefaultReplacementPolicy");
	}

	@Override
	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le) {
		DefaultLogEntryBuilder dlb = new DefaultLogEntryBuilder(le);
		if (!cache.contains(blockID)) {
			if (!cache.isFull()) {
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				dlb.addForcedEviction(false).addForcedInsertion(true);
			} else {
				dlb.addForcedEviction(false).addForcedInsertion(false);
			}
		} else {
			dlb.addForcedEviction(false).addForcedInsertion(false);
		}
		return dlb.build();
	}

}
