package cache.logging.preprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cache.HitStatus;
import cache.logging.LogEntry;

public class HistoricalPreprocessor implements ILogPreprocessor {

	private Map<String, Integer> fieldAccumulations;
	private String[] fieldIgnoreList;

	public HistoricalPreprocessor() {
		reset();
		this.fieldIgnoreList = new String[] { "Request", "Block ID" };
	}

	private void reset() {
		this.fieldAccumulations = new HashMap<String, Integer>();
		for (HitStatus hs : HitStatus.values()) {
			this.fieldAccumulations.put(hs.name(), 0);
		}
	}

	@Override
	public List<LogEntry> preprocess(List<LogEntry> logs) {
		reset();
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
			for (String s : fieldIgnoreList) {
				if (s.equalsIgnoreCase(field.trim())) {
					continue;
				}
			}
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
				} else {
					Integer curVal = (Integer) this.fieldAccumulations.get(field);
					if (curVal == null) {
						this.fieldAccumulations.put(field, 0);
					}
				}
			} else if (v instanceof HitStatus) {
				String fieldString = ((HitStatus) v).name();
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

	@Override
	public String getName() {
		return "HistoricalPreprocessor";
	}

}
