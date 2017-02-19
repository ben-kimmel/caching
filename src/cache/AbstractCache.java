package cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cache.logging.LogEntry;

public abstract class AbstractCache implements ICache {
	private int requests;
	private int hits;
	private int evictions;
	private int insertions;
	private int compulsoryMisses;
	private int conflictMisses;
	private int size;

	private boolean log;

	private Set<Integer> allEntries;
	protected Set<Integer> currentEntries;
	private List<LogEntry> logEntries;

	public AbstractCache(int size) {
		this.reset(size);
	}

	@Override
	public boolean requestBlock(int blockID) {
		boolean hit = false;
		HitStatus hs = HitStatus.HIT;
		LogEntry le;
		this.addRequest();
		if (!allEntries.contains(blockID)) {
			this.addCompulsoryMiss();
			this.allEntries.add(blockID);
			hs = HitStatus.COMPULSORY_MISS;
		} else if (!currentEntries.contains(blockID)) {
			this.addConflictMiss();
			hs = HitStatus.CONFLICT_MISS;
		} else {
			this.addHit();
			hit = true;
		}
		le = handleRequest(blockID);
		if (log) {
			le.setRequestNumber(this.requests);
			le.setHitStatus(hs);
			this.logEntries.add(le);
		}

		return hit;
	}

	abstract protected LogEntry handleRequest(int blockID);

	protected void addToCache(int blockID) {
		this.currentEntries.add(blockID);
		this.addInsertion();
	}

	protected void removeFromCache(int blockID) {
		this.currentEntries.remove(blockID);
		this.addEviction();
	}

	@Override
	public int getRequests() {
		return this.requests;
	}

	protected void addRequest() {
		this.requests++;
	}

	@Override
	public int getHits() {
		return this.hits;
	}

	protected void addHit() {
		this.hits++;
	}

	@Override
	public int getTotalMisses() {
		return this.compulsoryMisses + this.conflictMisses;
	}

	@Override
	public int getEvictions() {
		return this.evictions;
	}

	protected void addEviction() {
		this.evictions++;
	}

	@Override
	public int getInsertions() {
		return this.insertions;
	}

	protected void addInsertion() {
		this.insertions++;
	}

	@Override
	public int getCompulsoryMisses() {
		return this.compulsoryMisses;
	}

	protected void addCompulsoryMiss() {
		this.compulsoryMisses++;
	}

	@Override
	public int getConflictMisses() {
		return this.conflictMisses;
	}

	protected void addConflictMiss() {
		this.conflictMisses++;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public void setSize(int size) {
		this.size = size;
	}

	public void enableLog(int size) {
		this.log = true;
		this.logEntries = new ArrayList<LogEntry>(size);
	}

	public void disableLog() {
		this.log = false;
	}

	public void reset() {
		this.requests = 0;
		this.hits = 0;
		this.evictions = 0;
		this.insertions = 0;
		this.compulsoryMisses = 0;
		this.conflictMisses = 0;
		this.disableLog();

		this.allEntries = new HashSet<Integer>();
		this.currentEntries = new HashSet<>();
	}

	public void reset(int size) {
		this.reset();
		this.setSize(size);
	}

	public String generateReport() {
		StringBuilder sb = new StringBuilder();
		sb.append("Summary:\n");
		sb.append("===========================\n");
		sb.append("Requests: " + this.getRequests() + "\n");
		sb.append("Hits: " + this.getHits() + "\n");
		sb.append("Total Misses: " + this.getTotalMisses() + "\n");
		sb.append("Evictions: " + this.getEvictions() + "\n");
		sb.append("Insertions: " + this.getInsertions() + "\n");
		sb.append("Compulsory Misses: " + this.getCompulsoryMisses() + "\n");
		sb.append("Conflict Misses: " + this.getConflictMisses() + "\n");
		return sb.toString();

	}
	
	public List<LogEntry> getLog() {
		return this.logEntries;
	}
}
