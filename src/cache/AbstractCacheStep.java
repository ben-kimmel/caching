package cache;

public abstract class AbstractCacheStep implements ICacheStep {

	int priority;

	public AbstractCacheStep(int priority) {
		this.setPriority(priority);
	}

	@Override
	public int compareTo(ICacheStep o) {
		if (this.priority == o.getPriority()) {
			return 0;
		} else if (this.priority > o.getPriority()) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int getPriority() {
		return this.priority;
	}

}
