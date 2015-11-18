
package gt.transparente.app.data.provider.transparent.gt.disk.cache;

import gt.transparente.app.data.entity.PoliticalPartyEntity;
import rx.Observable;

/**
 * An interface representing a Political Party Cache.
 */
public interface PoliticalPartyCache {
  /**
   * Gets an {@link Observable} which will emit a {@link PoliticalPartyEntity}.
   *
   * @param id The id to retrieve data.
   */
  Observable<PoliticalPartyEntity> get(final int id);

  /**
   * Puts and element into the cache.
   *
   * @param entity Element to insert in the cache.
   */
  void put(PoliticalPartyEntity entity);

  /**
   * Checks if an element (Political Party) exists in the cache.
   *
   * @param politicalPartyId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isCached(final int politicalPartyId);

  /**
   * Checks if the cache is expired.
   *
   * @return true, the cache is expired, otherwise false.
   */
  boolean isExpired();

  /**
   * Evict all elements of the cache.
   */
  void evictAll();
}
