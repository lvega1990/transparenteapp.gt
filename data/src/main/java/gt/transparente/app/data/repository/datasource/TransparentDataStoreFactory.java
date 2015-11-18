
package gt.transparente.app.data.repository.datasource;

import android.content.Context;

import gt.transparente.app.data.provider.transparent.gt.TransparentProvider;
import gt.transparente.app.data.provider.transparent.gt.disk.cache.PoliticalPartyCache;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link TransparentDataStore}.
 */
@Singleton
public class TransparentDataStoreFactory {

    private final Context mContext;
    private final PoliticalPartyCache mPoliticalPartyCache;
    private final TransparentProvider mTransparentProvider;
    @Inject
    public TransparentDataStoreFactory(Context context,
                                       PoliticalPartyCache politicalPartyCache,
                                       TransparentProvider transparentProvider) {

        if (context == null || politicalPartyCache == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.mContext = context.getApplicationContext();
        this.mPoliticalPartyCache = politicalPartyCache;
        this.mTransparentProvider = transparentProvider;
    }

    /**
     * Create {@link TransparentDataStore} from a Political Party id.
     */
    public TransparentDataStore create(int partyId) {
        TransparentDataStore transparentDataStore;
        if (!this.mPoliticalPartyCache.isExpired() && this.mPoliticalPartyCache.isCached(partyId)) {
            transparentDataStore = new DiskTransparentDataStore(this.mPoliticalPartyCache);
        } else {
            transparentDataStore = createCloudDataStore();
        }

        return transparentDataStore;
    }

    /**
     * Create {@link TransparentDataStore} to retrieve data from the Cloud.
     */
    public TransparentDataStore createCloudDataStore() {
        return new CloudTransparentDataStore(this.mTransparentProvider, this.mPoliticalPartyCache);
    }
}
