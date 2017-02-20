package cache.implementations;

import java.util.Random;
import java.util.Set;

import cache.AbstractCache;
import cache.logging.LogEntry;
import cache.logging.LogEntryBuilder;

public class RandomReplacementCache extends AbstractCache {

	private Random r;
	private Integer[] index;
	private int buildIndex;

	public RandomReplacementCache(int size) {
		super(size);
		r = new Random();
		index = new Integer[size];
		buildIndex = 0;
	}

	@Override
	protected LogEntry handleRequest(int blockID) {
		LogEntryBuilder lb = new LogEntryBuilder();
		lb.setBlockID(blockID);
		if (!super.currentEntries.contains(blockID)) {
			if (!super.isCacheFull()) {
				super.addToCache(blockID);
				index[buildIndex] = blockID;
				buildIndex++;
				lb.setForcedEviction(false).setForcedInsertion(true);
			} else {
				int randomIndex = r.nextInt(index.length);
				int remove = index[randomIndex];
				super.removeFromCache(remove);
				super.addToCache(blockID);
				index[randomIndex] = blockID;
				lb.setForcedEviction(true).setForcedInsertion(true);
			}
		} else {
			lb.setForcedEviction(false).setForcedInsertion(false);
		}
		return lb.build();
	}

}
