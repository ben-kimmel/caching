package cache.pf;

import cache.ICacheStep;

/**
 * While this interface does not provide any methods beyond those of
 * {@link ICacheStep}, it is used to identify prefetching policies from among
 * all ICacheSteps.
 * 
 * @author Ben Kimmel
 *
 */
public interface IPrefetchingPolicy extends ICacheStep {

}
