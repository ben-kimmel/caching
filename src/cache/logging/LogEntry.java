package cache.logging;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * LogEntries are responsible for storing information about the request process.
 * 
 * @author Ben Kimmel
 *
 */
public class LogEntry {

	private Map<String, Class<? extends Object>> fieldTypes;
	private Map<String, Object> fieldValues;

	/**
	 * Constructs a new LogEntry.
	 */
	public LogEntry() {
		this.fieldTypes = new HashMap<String, Class<? extends Object>>();
		this.fieldValues = new HashMap<String, Object>();
	}

	/**
	 * Adds a new field to the LogEntry and then provides the field's value. If
	 * the field already exists in the LogEntry, the value will be overwritten
	 * by the new value.
	 * 
	 * @param field
	 *            The name of the field
	 * @param value
	 *            The value of the field
	 */
	public void addField(String field, Object value) {
		this.fieldTypes.put(field, value.getClass());
		this.fieldValues.put(field, value);
	}

	/**
	 * Returns a list of the fields this LogEntry contains.
	 * 
	 * @return The list of fields contained by the LogEntry in no particular
	 *         order
	 */
	public Set<String> getFieldList() {
		return this.fieldValues.keySet();
	}

	/**
	 * Returns the value of the given field. Often used in conjunction with
	 * {@link #getFieldType()}.
	 * 
	 * @param field
	 *            The field requested
	 * @return The value of the requested field
	 */
	public Object getFieldValue(String field) {
		return this.fieldValues.get(field);
	}

	/**
	 * Returns the Class of the value contained by a field.
	 * 
	 * @param field
	 *            The field requested
	 * @return The Class of the value of the requested field
	 */
	public Class<? extends Object> getFieldType(String field) {
		return this.fieldTypes.get(field);
	}

}
