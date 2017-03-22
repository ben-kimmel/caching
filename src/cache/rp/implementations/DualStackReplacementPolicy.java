package cache.rp.implementations;

import java.util.Stack;

import cache.AbstractCacheStep;
import cache.internal.CacheFullException;
import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;
import cache.rp.IReplacementPolicy;

//TODO: Javadocs
public class DualStackReplacementPolicy extends AbstractCacheStep implements IReplacementPolicy {

	private Stack<Integer> stackA;
	private Stack<Integer> stackB;
	private boolean useA;

	public DualStackReplacementPolicy(int priority) {
		super(priority, "DualStackReplacementPolicy");
		this.stackA = new Stack<Integer>();
		this.stackB = new Stack<Integer>();
		this.useA = true;
	}

	@Override
	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le) {
		DefaultLogEntryBuilder dlb = new DefaultLogEntryBuilder(le);
		if (!cache.contains(blockID)) {
			if (!cache.isFull()) {
				if (this.useA) {
					this.stackA.push(blockID);
				} else {
					this.stackB.push(blockID);
				}
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				dlb.addForcedEviction(false).addForcedInsertion(true);
			} else {
				int toRemove;
				if (stackA.empty()) {
					toRemove = stackB.pop();
					this.useA = true;
				} else {
					this.useA = false;
					toRemove = stackA.pop();
				}
				cache.removeFromCache(toRemove);
				try {
					cache.addToCache(blockID);
				} catch (CacheFullException e) {
					e.printStackTrace();
				}
				this.stackA.push(blockID);
				dlb.addForcedEviction(true).addForcedInsertion(true);
			}
		} else {
			dlb.addForcedEviction(false).addForcedInsertion(false);
		}
		return dlb.build();
	}

}
