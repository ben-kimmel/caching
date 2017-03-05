package cache.logging;

import cache.HitStatus;

public class DefaultLogEntryBuilder extends AbstractLogEntryBuilder {

	public DefaultLogEntryBuilder() {
		super();
	}

	public DefaultLogEntryBuilder(LogEntry le) {
		super(le);
	}

	public DefaultLogEntryBuilder addRequests(int numRequests) {
		super.addField("Requests", numRequests);
		return this;
	}

	public DefaultLogEntryBuilder addHitStatus(HitStatus hs) {
		super.addField("Hit Status", hs);
		return this;
	}

	public DefaultLogEntryBuilder addBlockID(int blockID) {
		super.addField("Block ID", blockID);
		return this;
	}

	public DefaultLogEntryBuilder addForcedEviction(boolean forcedEviction) {
		super.addField("Forced Eviction?", forcedEviction);
		return this;
	}

	public DefaultLogEntryBuilder addForcedInsertion(boolean forcedInsertion) {
		super.addField("Forced Insertion?", forcedInsertion);
		return this;
	}
}
