package cache;

import cache.internal.implementations.DefaultCacheInternalImplementation;
import cache.pf.implementations.DefaultPrefetchingPolicy;
import cache.rp.implementations.DefaultReplacementPolicy;

public class DefaultCacheWrapper extends AbstractCacheWrapper {

	public DefaultCacheWrapper() {
		super();
		this.setup(100, true);
	}

	public DefaultCacheWrapper(int cacheSize) {
		super();
		this.setup(cacheSize, true);
	}

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
