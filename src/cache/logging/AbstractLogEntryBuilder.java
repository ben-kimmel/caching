package cache.logging;

/**
 * An abstraction of the {@link ILogEntryBuilder} interface. Handles all methods
 * in ILogEntryBuilder.
 * 
 * @author Ben Kimmel
 *
 */
public abstract class AbstractLogEntryBuilder implements ILogEntryBuilder {

	protected LogEntry le;

	/**
	 * Initializes an AbstractLogEntryBuilder with an empty {@link LogEntry}.
	 */
	public AbstractLogEntryBuilder() {
		this.le = new LogEntry();
	}

	/**
	 * Initializes an AbstractLogEntryBuilder with the given {@link LogEntry}.
	 * 
	 * @param le
	 */
	public AbstractLogEntryBuilder(LogEntry le) {
		this.le = le;
	}

	@Override
	public LogEntry build() {
		return this.le;
	}

	@Override
	public ILogEntryBuilder addField(String field, Object value) {
		this.le.addField(field, value);
		return this;
	}

}
