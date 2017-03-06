package cache.rp;

import cache.ICacheStep;

/**
 * While this interface does not provide any methods beyond those of
 * {@link ICacheStep}, it is used to identify replacement policies from among
 * all ICacheSteps.
 * 
 * @author Ben Kimmel
 *
 */
public interface IReplacementPolicy extends ICacheStep {

}
