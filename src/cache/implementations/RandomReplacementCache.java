package cache.implementations;

import java.util.Random;

import cache.AbstractCache;
import cache.logging.LogEntry;
import cache.logging.LogEntryBuilder;

public class RandomReplacementCache extends AbstractCache {

	private Random r;
	private Integer[] index;
	private int buildIndex;

	public RandomReplacementCache(int size) {
		super(size);
		this.r = new Random();
		this.index = new Integer[size];
		this.buildIndex = 0;
	}

	@Override
	protected LogEntry handleRequest(int blockID) {
		LogEntryBuilder lb = new LogEntryBuilder();
		lb.setBlockID(blockID);
		if (!super.currentEntries.contains(blockID)) {
			if (!super.isCacheFull()) {
				super.addToCache(blockID);
				this.index[this.buildIndex] = blockID;
				this.buildIndex++;
				lb.setForcedEviction(false).setForcedInsertion(true);
			} else {
				int randomIndex = this.r.nextInt(this.index.length);
				int remove = this.index[randomIndex];
				super.removeFromCache(remove);
				super.addToCache(blockID);
				this.index[randomIndex] = blockID;
				lb.setForcedEviction(true).setForcedInsertion(true);
			}
		} else {
			lb.setForcedEviction(false).setForcedInsertion(false);
		}
		return lb.build();
	}

}
