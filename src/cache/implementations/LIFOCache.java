package cache.implementations;

import java.util.Stack;

import cache.AbstractCache;
import cache.logging.LogEntry;
import cache.logging.LogEntryBuilder;

public class LIFOCache extends AbstractCache {

	private Stack<Integer> s;

	public LIFOCache(int size) {
		super(size);
		this.s = new Stack<Integer>();
	}

	@Override
	protected LogEntry handleRequest(int blockID) {
		LogEntryBuilder lb = new LogEntryBuilder();
		lb.setBlockID(blockID);
		if (!super.currentEntries.contains(blockID)) {
			if (!super.isCacheFull()) {
				this.s.push(blockID);
				super.addToCache(blockID);
				lb.setForcedEviction(false).setForcedInsertion(true);
			} else {
				int newest = this.s.pop();
				s.push(blockID);
				super.removeFromCache(newest);
				super.addToCache(blockID);
				lb.setForcedEviction(true).setForcedInsertion(true);
			}
		} else {
			lb.setForcedEviction(false).setForcedInsertion(false);
		}
		return lb.build();
	}

}
