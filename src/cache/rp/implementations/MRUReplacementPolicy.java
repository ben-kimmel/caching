package cache.rp.implementations;

import cache.AbstractCacheStep;
import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;

public class MRUReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

	private int mostRecentBlockID;

	public MRUReplacementPolicy(int priority) {
		super(priority);
	}

	@Override
	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le) {
		DefaultLogEntryBuilder lb = new DefaultLogEntryBuilder(le);
		if (!cache.contains(blockID)) {
			if (!cache.isFull()) {
				this.mostRecentBlockID = blockID;
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				lb.addForcedEviction(false).addForcedInsertion(true);
			} else {
				cache.removeFromCache(this.mostRecentBlockID);
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				this.mostRecentBlockID = blockID;
				lb.addForcedEviction(true).addForcedInsertion(true);
			}
		} else {
			this.mostRecentBlockID = blockID;
			lb.addForcedEviction(false).addForcedInsertion(false);
		}
		return lb.build();
	}

}
