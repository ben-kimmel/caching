package cache.rp.implementations;

import java.util.ArrayList;
import java.util.Random;

import cache.AbstractCacheStep;
import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;

public class RandomReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

	private Random r;
	private ArrayList<Integer> index;

	public RandomReplacementPolicy(int priority) {
		super(priority);
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
