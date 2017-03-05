package cache.rp.implementations;

import java.util.Stack;

import cache.AbstractCacheStep;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;

public class LIFOCache extends AbstractCacheStep implements IReplacementPolicy {

	private Stack<Integer> s;

	public LIFOCache(int priority) {
		super(priority);
		this.s = new Stack<Integer>();
	}

	@Override
	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le) {
		DefaultLogEntryBuilder lb = new DefaultLogEntryBuilder(le);
		if (!cache.contains(blockID)) {
			if (!cache.isFull()) {
				this.s.push(blockID);
				cache.addToCache(blockID);
				lb.addForcedEviction(false).addForcedInsertion(true);
			} else {
				int newest = this.s.pop();
				s.push(blockID);
				cache.removeFromCache(newest);
				cache.addToCache(blockID);
				lb.addForcedEviction(true).addForcedInsertion(true);
			}
		} else {
			lb.addForcedEviction(false).addForcedInsertion(false);
		}
		return lb.build();
	}

}
