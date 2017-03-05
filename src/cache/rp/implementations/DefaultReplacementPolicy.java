package cache.rp.implementations;

import cache.AbstractCacheStep;
import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;

public class DefaultReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

	public DefaultReplacementPolicy(int priority) {
		super(priority);
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
