package cache.logging;

import cache.HitStatus;

public class LogEntryBuilder {

	private LogEntry le;
	
	public LogEntryBuilder() {
		this.le = new LogEntry();
	}
	
	public LogEntryBuilder setBlockID(int blockID) {
		le.setBlockID(blockID);
		return this;
	}
	
	public LogEntryBuilder setHitStatus(HitStatus hitStatus) {
		le.setHitStatus(hitStatus);
		return this;
	}
	
	public LogEntryBuilder setRequestNumber(int requestNumber) {
		le.setRequestNumber(requestNumber);
		return this;
	}
	
	public LogEntryBuilder setForcedEviction(boolean forcedEviction) {
		le.setForcedEviction(forcedEviction);
		return this;
	}
	
	public LogEntryBuilder setForcedInsertion(boolean forcedInsertion) {
		le.setForcedInsertion(forcedInsertion);
		return this;
	}
	
	public LogEntry build() {
		return le;
	}
}
