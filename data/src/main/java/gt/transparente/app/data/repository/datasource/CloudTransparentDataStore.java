
package gt.transparente.app.data.repository.datasource;

import gt.transparente.app.data.entity.PoliticalPartyEntity;
import gt.transparente.app.data.provider.transparent.gt.TransparentProvider;
import gt.transparente.app.data.provider.transparent.gt.disk.cache.PoliticalPartyCache;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * {@link TransparentDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudTransparentDataStore implements TransparentDataStore {

    private final TransparentProvider mTransparentProvider;
    private final PoliticalPartyCache mPoliticalPartyCache;

    private final Action1<PoliticalPartyEntity> saveToCacheAction =
            politicalPartyEntity -> {
                if (politicalPartyEntity != null) {
                    CloudTransparentDataStore.this.mPoliticalPartyCache.put(politicalPartyEntity);
                }
            };

    /**
     * Construct a {@link TransparentDataStore} based on connections to the api (Cloud).
     *
     * @param transparentProvider
     * @param politicalPartyCache A {@link PoliticalPartyCache} to cache data retrieved from the api.
     */
    public CloudTransparentDataStore(TransparentProvider transparentProvider, PoliticalPartyCache politicalPartyCache) {
        this.mTransparentProvider = transparentProvider;
        this.mPoliticalPartyCache = politicalPartyCache;
    }

    @Override
    public Observable<List<PoliticalPartyEntity>> politicalPartyEntityList() {
        return mTransparentProvider.politicalPartyEntityList();
    }

    @Override
    public Observable<PoliticalPartyEntity> politicalPartyEntityDetails(int partyId) {
        return mTransparentProvider.politicalPartyEntityById(partyId)
                .doOnNext(saveToCacheAction);
    }
}
