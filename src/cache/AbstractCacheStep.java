package cache;

/**
 * An abstraction of the {@link ICacheStep} interface. Encompasses all aspects
 * of priority handling.
 * 
 * @author Ben Kimmel
 *
 */
public abstract class AbstractCacheStep implements ICacheStep {

	int priority;

	/**
	 * Initializes an AbstractCacheStep with the given priority. The lower the
	 * priority number, the sooner it executes.
	 * 
	 * @param priority
	 *            The priority of the AbstractCacheStep. Lower is sooner
	 */
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
