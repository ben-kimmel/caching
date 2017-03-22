package cache.rp.implementations;

import java.util.LinkedList;
import java.util.Queue;

import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.AbstractReplacementPolicy;
import cache.rp.IReplacementPolicy;

/**
 * A First-In-First-Out (FIFO) cache replacement policy. Entries will be
 * replaced in the order in which they are added to the cache regardless of
 * their last access time.
 * <p>
 * For example, if the cache has a maximum size of 3, and requests are issued as
 * such:
 * <p>
 * <i>1, 2, 3, 1, 4</i>
 * <p>
 * When the request for <i>4</i> comes in, <i>1</i> will be evicted, despite
 * being the most recently accessed block.
 * 
 * @author Ben Kimmel
 *
 */
public class FIFOReplacementPolicy extends AbstractReplacementPolicy implements IReplacementPolicy {

	private Queue<Integer> ageQueue;

	/**
	 * Constructs a new FIFOReplacementPolicy with the given priority. The lower
	 * the priority, the earlier it will execute.
	 * 
	 * @param priority
	 *            The priority with which this should be executed. Lower is
	 *            sooner
	 */
	public FIFOReplacementPolicy(int priority) {
		super(priority, "FIFOReplacementPolicy");
		this.ageQueue = new LinkedList<Integer>();
	}

	@Override
	protected LogEntry handleCacheHit(int blockID, ICacheInternal cache, LogEntry le) {
		DefaultLogEntryBuilder dlb = new DefaultLogEntryBuilder(le);
		dlb.addForcedEviction(false).addForcedInsertion(false);
		return dlb.build();
	}

	@Override
	protected LogEntry handleCacheEviction(int blockID, ICacheInternal cache, LogEntry le) {
		DefaultLogEntryBuilder dlb = new DefaultLogEntryBuilder(le);
		int oldest = this.ageQueue.poll();
		cache.removeFromCache(oldest);
		try {
			cache.addToCache(blockID);
		} catch (CacheFullException e) {
			e.printStackTrace();
		}
		this.ageQueue.add(blockID);
		dlb.addForcedEviction(true).addForcedInsertion(true);
		return dlb.build();
	}

	@Override
	protected LogEntry handleCacheWarmUp(int blockID, ICacheInternal cache, LogEntry le) {
		DefaultLogEntryBuilder dlb = new DefaultLogEntryBuilder(le);
		this.ageQueue.add(blockID);
		try {
			cache.addToCache(blockID);
		} catch (CacheFullException e) {
			e.printStackTrace();
		}
		dlb.addForcedEviction(false).addForcedInsertion(true);
		return dlb.build();
	}

}
