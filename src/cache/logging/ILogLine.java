package cache.logging;

import java.util.Map;

public interface ILogLine extends Iterable<String> {

	public Map<String, String> toMap();
	
}
