package cache.implementations;

import java.util.LinkedList;
import java.util.Queue;

import cache.AbstractCache;
import cache.logging.LogEntry;
import cache.logging.LogEntryBuilder;

public class FIFOCache extends AbstractCache {

	private Queue<Integer> ageQueue;

	public FIFOCache(int size) {
		super(size);
		this.ageQueue = new LinkedList<Integer>();
	}

	@Override
	protected LogEntry handleRequest(int blockID) {
		LogEntryBuilder lb = new LogEntryBuilder();
		lb.setBlockID(blockID);
		if (!super.currentEntries.contains(blockID)) {
			if (!super.isCacheFull()) {
				this.ageQueue.add(blockID);
				super.addToCache(blockID);
				lb.setForcedEviction(false).setForcedInsertion(true);
			} else {
				int oldest = this.ageQueue.poll();
				super.removeFromCache(oldest);
				super.addToCache(blockID);
				this.ageQueue.add(blockID);
				lb.setForcedEviction(true).setForcedInsertion(true);
			}
		} else {
			lb.setForcedEviction(false).setForcedInsertion(false);
		}
		return lb.build();
	}

}
