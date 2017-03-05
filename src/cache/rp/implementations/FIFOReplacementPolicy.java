package cache.rp.implementations;

import java.util.LinkedList;
import java.util.Queue;

import cache.AbstractCacheStep;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;

public class FIFOReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

	private Queue<Integer> ageQueue;

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
				cache.addToCache(blockID);
				dlb.addForcedEviction(false).addForcedInsertion(true);
			} else {
				int oldest = this.ageQueue.poll();
				cache.removeFromCache(oldest);
				cache.addToCache(blockID);
				this.ageQueue.add(blockID);
				dlb.addForcedEviction(true).addForcedInsertion(true);
			}
		} else {
			dlb.addForcedEviction(false).addForcedInsertion(false);
		}
		return dlb.build();
	}

}
