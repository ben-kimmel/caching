package cache.logging;

import cache.HitStatus;

public class LogEntry {

	private int blockID;
	private HitStatus hitStatus;
	private int requestNumber;
	private boolean forcedEviction;
	private boolean forcedInsertion;

	public LogEntry() {
	}

	public int getBlockID() {
		return blockID;
	}

	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}

	public HitStatus getHitStatus() {
		return hitStatus;
	}

	public void setHitStatus(HitStatus hitStatus) {
		this.hitStatus = hitStatus;
	}

	public int getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}

	public boolean isForcedEviction() {
		return forcedEviction;
	}

	public void setForcedEviction(boolean forcedEviction) {
		this.forcedEviction = forcedEviction;
	}

	public boolean isForcedInsertion() {
		return forcedInsertion;
	}

	public void setForcedInsertion(boolean forcedInsertion) {
		this.forcedInsertion = forcedInsertion;
	}

	@Override
	public String toString() {
		return "LogEntry [blockID=" + blockID + ", hitStatus=" + hitStatus + ", requestNumber=" + requestNumber
				+ ", forcedEviction=" + forcedEviction + ", forcedInsertion=" + forcedInsertion + "]";
	}

	public String toCSVEntry() {
		return requestNumber + "," + blockID + "," + hitStatus + "," + forcedEviction + "," + forcedInsertion + "\n";
	}

	public static String getCSVHeaders() {
		return "requestNumber,blockID,hitStatus,forcedEviction,forcedInsertion\n";
	}

}
