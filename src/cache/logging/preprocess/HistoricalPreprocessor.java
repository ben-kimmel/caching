package cache.logging.preprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cache.HitStatus;
import cache.logging.LogEntry;

public class HistoricalPreprocessor implements ILogPreprocessor {

	private Map<String, Integer> fieldAccumulations;

	public HistoricalPreprocessor() {
		this.fieldAccumulations = new HashMap<String, Integer>();
	}

	@Override
	public List<LogEntry> preprocess(List<LogEntry> logs) {
		List<LogEntry> historyLog = new ArrayList<LogEntry>();
		int lineNum = 0;
		for (LogEntry line : logs) {
			processLine(line);
			LogEntry le = new LogEntry();
			le.addField("Log Line", lineNum);
			for (String field : this.fieldAccumulations.keySet()) {
				le.addField(field, this.fieldAccumulations.get(field));
			}
			historyLog.add(le);
			lineNum++;
		}
		return historyLog;
	}

	private void processLine(LogEntry line) {
		for (String field : line.getFieldList()) {
			Object v = line.getFieldValue(field);
			if (v instanceof Integer) {
				Integer curVal = this.fieldAccumulations.get(field);
				if (curVal == null) {
					this.fieldAccumulations.put(field, (Integer) v);
				} else {
					curVal = curVal + (Integer) v;
					this.fieldAccumulations.put(field, curVal);
				}
			} else if (v instanceof Boolean) {
				if ((Boolean) v) {
					Integer curVal = (Integer) this.fieldAccumulations.get(field);
					if (curVal == null) {
						this.fieldAccumulations.put(field, 1);
					} else {
						curVal++;
						this.fieldAccumulations.put(field, curVal);
					}
				}
			} else if (v instanceof HitStatus) {
				String fieldString = field + " - " + ((HitStatus) v).name();
				Integer curVal = (Integer) this.fieldAccumulations.get(fieldString);
				if (curVal == null) {
					this.fieldAccumulations.put(fieldString, 1);
				} else {
					curVal++;
					this.fieldAccumulations.put(fieldString, curVal);
				}
			}
		}
	}

}
