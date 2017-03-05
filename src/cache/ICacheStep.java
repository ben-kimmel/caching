package cache;

import cache.internal.ICacheInternal;
import cache.logging.LogEntry;

/**
 * An internal request handling step for a cache. This could include things such
 * as the replacement policy or prefetching algorithms.
 * 
 * @author Ben Kimmel
 *
 */
public interface ICacheStep extends Comparable<ICacheStep> {

	/**
	 * Modifies the provided {@link ICacheInternal} based on the
	 * provided block ID. Returns a {@link LogEntry} that reflects
	 * the actions taken during the execution and any additional information.
	 * 
	 * @param blockID
	 *            The requested block ID that should be used to modify the
	 *            provided ICacheInternal
	 * @param cache
	 *            The cache representation that is being modified
	 * @param le
	 *            The current LogEntry for this request. The entries in this
	 *            LogEntry should be maintained in the returned LogEntry if they
	 *            are not modified
	 * @return A LogEntry that provides a record of all actions taken by this
	 *         ICacheStep and all previous actions taken
	 */
	public LogEntry execute(int blockID, ICacheInternal cache, LogEntry le);

	/**
	 * Sets the priority with which this ICacheStep is executed compared to
	 * other ICacheSteps. The lower the priority number, the earlier the
	 * ICacheStep is executed.
	 * 
	 * @param priority
	 *            The priority with which this ICacheStep is executed. Lower is
	 *            sooner
	 */
	public void setPriority(int priority);

	/**
	 * Returns the priority of this ICacheStep.
	 * 
	 * @return The priority of this ICacheStep
	 */
	public int getPriority();

}
