package cache.rp.implementations;

import java.util.Stack;

import cache.AbstractCacheStep;
import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;

/**
 * A Last-In-First-Out (LIFO) cache replacement policy. The most recently added
 * entry will be evicted when a new request comes in, regardless of their last
 * access time.
 * <p>
 * For example, if the cache has a maximum size of 3, and requests are issued as
 * such:
 * <p>
 * <i>1, 2, 3, 1, 4</i>
 * <p>
 * When the request for <i>4</i> comes in, <i>3</i> will be evicted.
 * 
 * @author Ben Kimmel
 *
 */
public class LIFOReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

	private Stack<Integer> s;

	/**
	 * Constructs a new LIFOReplacementPolicy with the given priority. The lower
	 * the priority, the earlier it will execute.
	 * 
	 * @param priority
	 *            The priority with which this should be executed. Lower is
	 *            sooner
	 */
	public LIFOReplacementPolicy(int priority) {
		super(priority);
		this.s = new Stack<Integer>();
	}

	@Override
	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le) {
		DefaultLogEntryBuilder lb = new DefaultLogEntryBuilder(le);
		if (!cache.contains(blockID)) {
			if (!cache.isFull()) {
				this.s.push(blockID);
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				lb.addForcedEviction(false).addForcedInsertion(true);
			} else {
				int newest = this.s.pop();
				s.push(blockID);
				cache.removeFromCache(newest);
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
