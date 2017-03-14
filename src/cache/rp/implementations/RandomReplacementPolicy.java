package cache.rp.implementations;

import java.util.ArrayList;
import java.util.Random;

import cache.AbstractCacheStep;
import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;

/**
 * A random cache replacement policy. A randomly selected entry will be evicted
 * when a new request comes in.
 * <p>
 * For example, if the cache has a maximum size of 3, and requests are issued as
 * such:
 * <p>
 * <i>1, 2, 3, 1, 4</i>
 * <p>
 * When the request for <i>4</i> comes in, either <i>1, 2,</i> or <i>3</i> will
 * be evicted. Entries will be evicted in an even distribution.
 * 
 * @author Ben Kimmel
 *
 */
public class RandomReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

	private Random r;
	private ArrayList<Integer> index;

	/**
	 * Constructs a new RandomReplacementPolicy with the given priority. The
	 * lower the priority, the earlier it will execute.
	 * 
	 * @param priority
	 *            The priority with which this should be executed. Lower is
	 *            sooner
	 */
	public RandomReplacementPolicy(int priority) {
		super(priority, "RandomReplacementPolicy");
		this.r = new Random();
		this.index = new ArrayList<Integer>();
	}

	@Override
	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le) {
		DefaultLogEntryBuilder lb = new DefaultLogEntryBuilder(le);
		if (!cache.contains(blockID)) {
			if (!cache.isFull()) {
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				this.index.add(blockID);
				lb.addForcedEviction(false).addForcedInsertion(true);
			} else {
				int randomIndex = this.r.nextInt(this.index.size());
				int remove = this.index.remove(randomIndex);
				cache.removeFromCache(remove);
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				this.index.add(blockID);
				lb.addForcedEviction(true).addForcedInsertion(true);
			}
		} else {
			lb.addForcedEviction(false).addForcedInsertion(false);
		}
		return lb.build();
	}

}
