package cache.logging;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LogEntry {

	@SuppressWarnings("rawtypes")
	private Map<String, Class> fieldTypes;
	private Map<String, Object> fieldValues;

	@SuppressWarnings("rawtypes")
	public LogEntry() {
		this.fieldTypes = new HashMap<String, Class>();
		this.fieldValues = new HashMap<String, Object>();
	}

	public void addField(String field, Object value) {
		this.fieldTypes.put(field, value.getClass());
		this.fieldValues.put(field, value);
	}

	public Set<String> getFieldList() {
		return this.fieldValues.keySet();
	}

	public Object getFieldValue(String field) {
		return this.fieldValues.get(field);
	}

	@SuppressWarnings("rawtypes")
	public Class getFieldType(String field) {
		return this.fieldTypes.get(field);
	}

}
