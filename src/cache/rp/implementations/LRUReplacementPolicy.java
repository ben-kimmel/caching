package cache.rp.implementations;

import cache.AbstractCacheStep;
import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;
import cache.rp.implementations.datastructures.SwappableSetQueue;

/**
 * A Least-Recently-Used (LRU) cache replacement policy. The least recently
 * accessed entry will be evicted when a new request comes in.
 * <p>
 * For example, if the cache has a maximum size of 3, and requests are issued as
 * such:
 * <p>
 * <i>1, 2, 3, 1, 4</i>
 * <p>
 * When the request for <i>4</i> comes in, <i>2</i> will be evicted. If a
 * request for <i>5</i> then came in, <i>3</i> would be evicted.
 * 
 * @author Ben Kimmel
 *
 */
public class LRUReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

	private SwappableSetQueue<Integer> q;

	/**
	 * Constructs a new LRUReplacementPolicy with the given priority. The lower
	 * the priority, the earlier it will execute.
	 * 
	 * @param priority
	 *            The priority with which this should be executed. Lower is
	 *            sooner
	 */
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
