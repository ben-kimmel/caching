package cache.logging;

import cache.HitStatus;

/**
 * The default {@link ILogEntryBuilder} implementation.
 * 
 * @author Ben Kimmel
 *
 */
public class DefaultLogEntryBuilder extends AbstractLogEntryBuilder {

	/**
	 * Constructs a new DefaultLogEntryBuilder with a clean {@link LogEntry}.
	 */
	public DefaultLogEntryBuilder() {
		super();
	}

	/**
	 * Constructs a new DefaultLogEntryBuilder with the given {@link LogEntry}.
	 */
	public DefaultLogEntryBuilder(LogEntry le) {
		super(le);
	}

	/**
	 * Adds the field 'Request' to the {@link LogEntry} with the specified
	 * value.
	 * 
	 * @param numRequests
	 *            The number of the request
	 * @return A DefaultLogEntryBuilder that reflects changes made by this
	 *         method
	 */
	public DefaultLogEntryBuilder addRequest(int numRequests) {
		super.addField("Request", numRequests);
		return this;
	}

	/**
	 * Adds the field 'Hit Status' to the {@link LogEntry} with the specified
	 * value.
	 * 
	 * @param hs
	 *            The hit status of the request
	 * @return A DefaultLogEntryBuilder that reflects changes made by this
	 *         method
	 */
	public DefaultLogEntryBuilder addHitStatus(HitStatus hs) {
		super.addField("Hit Status", hs);
		return this;
	}

	/**
	 * Adds the field 'Block ID' to the {@link LogEntry} with the specified
	 * value.
	 * 
	 * @param blockID
	 *            The block ID of the request
	 * @return A DefaultLogEntryBuilder that reflects changes made by this
	 *         method
	 */
	public DefaultLogEntryBuilder addBlockID(int blockID) {
		super.addField("Block ID", blockID);
		return this;
	}

	/**
	 * Adds the field 'Forced Eviction?' to the {@link LogEntry} with the
	 * specified value.
	 * 
	 * @param forcedEviction
	 *            Whether the request forced an eviction
	 * @return A DefaultLogEntryBuilder that reflects changes made by this
	 *         method
	 */
	public DefaultLogEntryBuilder addForcedEviction(boolean forcedEviction) {
		super.addField("Forced Eviction?", forcedEviction);
		return this;
	}

	/**
	 * Adds the field 'Forced Insertion?' to the {@link LogEntry} with the
	 * specified value.
	 * 
	 * @param forcedEviction
	 *            Whether the request forced an insertion
	 * @return A DefaultLogEntryBuilder that reflects changes made by this
	 *         method
	 */
	public DefaultLogEntryBuilder addForcedInsertion(boolean forcedInsertion) {
		super.addField("Forced Insertion?", forcedInsertion);
		return this;
	}
}
