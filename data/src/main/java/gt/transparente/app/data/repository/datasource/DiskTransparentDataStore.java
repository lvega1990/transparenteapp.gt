
package gt.transparente.app.data.repository.datasource;

import gt.transparente.app.data.provider.transparent.gt.disk.cache.PoliticalPartyCache;
import gt.transparente.app.data.entity.PoliticalPartyEntity;
import java.util.List;
import rx.Observable;

/**
 * {@link TransparentDataStore} implementation based on file system data store.
 */
public class DiskTransparentDataStore implements TransparentDataStore {

  private final PoliticalPartyCache mPoliticalPartyCache;

  /**
   * Construct a {@link TransparentDataStore} based file system data store.
   *
   * @param politicalPartyCache A {@link PoliticalPartyCache} to cache data retrieved from the api.
   */
  public DiskTransparentDataStore(PoliticalPartyCache politicalPartyCache) {
    this.mPoliticalPartyCache = politicalPartyCache;
  }

  @Override
  public Observable<List<PoliticalPartyEntity>> politicalPartyEntityList(int pageNumber) {
    //TODO: implement simple cache for storing/retrieving collections of parties.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }

  @Override
  public Observable<PoliticalPartyEntity> politicalPartyEntityDetails(int partyId) {
    return this.mPoliticalPartyCache.get(partyId);
  }
}
