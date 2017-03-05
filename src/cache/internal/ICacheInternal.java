package cache.internal;

public interface ICacheInternal {

	public boolean contains(int blockID);

	public boolean hasSeen(int blockID);
	
	public boolean isFull();

	public void addToCache(int blockID);
	
	public void removeFromCache(int blockID);
}
