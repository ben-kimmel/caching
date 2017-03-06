package cache.rp.implementations;

import cache.AbstractCacheStep;
import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;

/**
 * A Most-Recently-Used (MRU) cache replacement policy. The most recently
 * accessed entry will be evicted when a new request comes in.
 * <p>
 * For example, if the cache has a maximum size of 3, and requests are issued as
 * such:
 * <p>
 * <i>1, 2, 3, 1, 4</i>
 * <p>
 * When the request for <i>4</i> comes in, <i>1</i> will be evicted. If a
 * request for <i>5</i> then came in, <i>4</i> would be evicted. In practice,
 * this is very similar to the {@link FIFOReplacementPolicy}.
 * 
 * @author Ben Kimmel
 *
 */
public class MRUReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

	private int mostRecentBlockID;

	/**
	 * Constructs a new MRUReplacementPolicy with the given priority. The lower
	 * the priority, the earlier it will execute.
	 * 
	 * @param priority
	 *            The priority with which this should be executed. Lower is
	 *            sooner
	 */
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
