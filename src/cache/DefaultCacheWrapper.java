package cache;

import cache.internal.ICacheInternal;
import cache.internal.implementations.DefaultCacheInternalImplementation;
import cache.pf.implementations.DefaultPrefetchingPolicy;
import cache.rp.implementations.DefaultReplacementPolicy;

/**
 * The default {@link ICacheWrapper} implementation.
 * 
 * @author Ben Kimmel
 *
 */
public class DefaultCacheWrapper extends AbstractCacheWrapper {

	/**
	 * Constructs a DefaultCacheWrapper. The DefaultCacheWrapper is initialized
	 * with a {@link DefaultCacheInternalImplementation} of size 100 as the
	 * backing {@link ICacheInternal}. No {@link ICacheStep} are initialized.
	 */
	public DefaultCacheWrapper() {
		super();
		this.setup(100, false);
	}

	/**
	 * Constructs a DefaultCacheWrapper. The DefaultCacheWrapper is initialized
	 * with a {@link DefaultCacheInternalImplementation} of the given size as
	 * the backing {@link ICacheInternal}. No {@link ICacheStep} are
	 * initialized.
	 * 
	 * @param cacheSize
	 *            The size at which to initialize the backing cache
	 */
	public DefaultCacheWrapper(int cacheSize) {
		super();
		this.setup(cacheSize, false);
	}

	/**
	 * Constructs a DefaultCacheWrapper. The DefaultCacheWrapper is initialized
	 * with a {@link DefaultCacheInternalImplementation} of the given size as
	 * the backing {@link ICacheInternal}. If default {@link ICacheStep} are
	 * initialized, a {@link DefaultPrefetchingPolicy} is added with priority
	 * 100, and a {@link DefaultReplacementPolicy} is added with priority 1.
	 * 
	 * @param cacheSize
	 *            The size at which to initialize the backing cache
	 * @param initializeDefaultSteps
	 *            Whether or not to initialize the default ICacheSteps
	 */
	public DefaultCacheWrapper(int cacheSize, boolean initializeDefaultSteps) {
		super();
		this.setup(cacheSize, initializeDefaultSteps);
	}

	private void setup(int cacheSize, boolean initializeDefaultSteps) {
		super.provideCacheImplementation(new DefaultCacheInternalImplementation(cacheSize));
		if (initializeDefaultSteps) {
			super.provideCacheStep(new DefaultPrefetchingPolicy(100));
			super.provideCacheStep(new DefaultReplacementPolicy(1));
		}
	}

}
