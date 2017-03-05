package cache.rp.implementations;

import cache.AbstractCacheStep;
import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;
import cache.rp.implementations.datastructures.SwappableSetQueue;

public class LRUReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

	private SwappableSetQueue<Integer> q;

	public LRUReplacementPolicy(int priority) {
		super(priority);
		this.q = new SwappableSetQueue<Integer>();
	}

	@Override
	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le) {
		DefaultLogEntryBuilder lb = new DefaultLogEntryBuilder(le);
		if (!cache.contains(blockID)) {
			if (!cache.isFull()) {
				this.q.add(blockID);
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				lb.addForcedEviction(false).addForcedInsertion(true);
			} else {
				int oldest = this.q.poll();
				cache.removeFromCache(oldest);
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				lb.addForcedEviction(true).addForcedInsertion(true);
			}
		} else {
			lb.addForcedEviction(false).addForcedInsertion(false);
		}
		return lb.build();
	}

}
