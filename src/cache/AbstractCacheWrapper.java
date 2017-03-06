package cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cache.internal.ICacheInternal;
import cache.logging.DefaultLogEntryBuilder;
import cache.logging.LogEntry;

/**
 * An abstraction of the {@link ICacheWrapper} interface. Handles all methods of
 * ICacheWrapper.
 * 
 * @author Ben Kimmel
 *
 */
public abstract class AbstractCacheWrapper implements ICacheWrapper {

	private ICacheInternal cache;
	private boolean stepsFinalized;

	private List<ICacheStep> cacheSteps;
	private List<LogEntry> logEntries;

	/**
	 * Initializes an AbstractCacheWrapper.
	 */
	public AbstractCacheWrapper() {
		this.cacheSteps = new ArrayList<ICacheStep>();
		this.logEntries = new LinkedList<LogEntry>();
		this.stepsFinalized = false;
	}

	@Override
	public boolean requestBlock(int blockID) {
		if (!this.stepsFinalized) {
			finalizeSteps();
		}
		boolean hit = false;
		HitStatus hs = HitStatus.HIT;
		LogEntry le = new LogEntry();
		if (!cache.hasSeen(blockID)) {
			hs = HitStatus.COMPULSORY_MISS;
		} else if (!cache.contains(blockID)) {
			hs = HitStatus.CONFLICT_MISS;
		} else {
			hit = true;
		}
		for (ICacheStep cs : this.cacheSteps) {
			le = cs.execute(blockID, this.cache, le);
		}
		DefaultLogEntryBuilder lb = new DefaultLogEntryBuilder(le);
		lb.addBlockID(blockID);
		lb.addHitStatus(hs);
		lb.addRequest(this.logEntries.size());
		this.logEntries.add(lb.build());
		return hit;
	}

	// due to the implementation of the cache steps, they are unordered until
	// they are finalized, which puts them into order based on their priority.
	private void finalizeSteps() {
		Collections.sort(this.cacheSteps);
		this.stepsFinalized = true;
	}

	@Override
	public boolean softRequest(int blockID) {
		return this.cache.contains(blockID);
	}

	@Override
	public void provideCacheImplementation(ICacheInternal cache) {
		this.cache = cache;
	}

	@Override
	public void provideCacheStep(ICacheStep step) {
		this.cacheSteps.add(step);
		this.stepsFinalized = false;
	}

	@Override
	public List<LogEntry> getLog() {
		return logEntries;
	}

}
