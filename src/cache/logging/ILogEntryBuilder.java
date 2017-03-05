package cache.logging;

public interface ILogEntryBuilder {

	public LogEntry build();

	public ILogEntryBuilder addField(String field, Object value);

}
