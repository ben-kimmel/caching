package cache.implementations;

import cache.AbstractCache;
import cache.logging.LogEntry;
import cache.logging.LogEntryBuilder;

public class MostRecentlyUsedCache extends AbstractCache {
	
	private int mostRecentBlockID;

	public MostRecentlyUsedCache(int size) {
		super(size);
	}

	@Override
	protected LogEntry handleRequest(int blockID) {
		LogEntryBuilder lb = new LogEntryBuilder();
		lb.setBlockID(blockID);
		if (!super.currentEntries.contains(blockID)) {
			if (!super.isCacheFull()) {
				this.mostRecentBlockID = blockID;
				super.addToCache(blockID);
				lb.setForcedEviction(false).setForcedInsertion(true);
			} else {
				super.removeFromCache(this.mostRecentBlockID);
				super.addToCache(blockID);
				this.mostRecentBlockID = blockID;
				lb.setForcedEviction(true).setForcedInsertion(true);
			}
		} else {
			this.mostRecentBlockID = blockID;
			lb.setForcedEviction(false).setForcedInsertion(false);
		}
		return lb.build();
	}

}
