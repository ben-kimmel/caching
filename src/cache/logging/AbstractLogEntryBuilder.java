package cache.logging;

public abstract class AbstractLogEntryBuilder implements ILogEntryBuilder {

	protected LogEntry le;

	public AbstractLogEntryBuilder() {
		this.le = new LogEntry();
	}

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
