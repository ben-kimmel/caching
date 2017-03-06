package cache.rp.implementations;

import java.util.LinkedList;
import java.util.Queue;

import cache.AbstractCacheStep;
import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
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
public class FIFOReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

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
		super(priority);
		this.ageQueue = new LinkedList<Integer>();
	}

	@Override
	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le) {
		DefaultLogEntryBuilder dlb = new DefaultLogEntryBuilder(le);
		if (!cache.contains(blockID)) {
			if (!cache.isFull()) {
				this.ageQueue.add(blockID);
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				dlb.addForcedEviction(false).addForcedInsertion(true);
			} else {
				int oldest = this.ageQueue.poll();
				cache.removeFromCache(oldest);
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				this.ageQueue.add(blockID);
				dlb.addForcedEviction(true).addForcedInsertion(true);
			}
		} else {
			dlb.addForcedEviction(false).addForcedInsertion(false);
		}
		return dlb.build();
	}

}
