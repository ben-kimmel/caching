package cache.implementations;

import cache.AbstractCache;
import cache.implementations.datastructures.SwappableQueue;
import cache.logging.LogEntry;
import cache.logging.LogEntryBuilder;

public class LeastRecentlyUsedCache extends AbstractCache {

	private SwappableQueue q;

	public LeastRecentlyUsedCache(int size) {
		super(size);
		this.q = new SwappableQueue(size);
	}

	@Override
	protected LogEntry handleRequest(int blockID) {
		LogEntryBuilder lb = new LogEntryBuilder();
		lb.setBlockID(blockID);
		if (!super.currentEntries.contains(blockID)) {
			if (!super.isCacheFull()) {
				this.q.pushNode(blockID);
				super.addToCache(blockID);
				lb.setForcedEviction(false).setForcedInsertion(true);
			} else {
				int oldest = this.q.popTail();
				super.removeFromCache(oldest);
				super.addToCache(blockID);
				lb.setForcedEviction(true).setForcedInsertion(true);
			}
		} else {
			lb.setForcedEviction(false).setForcedInsertion(false);
		}
		return lb.build();
	}

}
