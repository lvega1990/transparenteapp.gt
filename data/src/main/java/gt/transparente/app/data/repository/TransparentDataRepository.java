package gt.transparente.app.data.repository;

import gt.transparente.app.data.entity.mapper.PoliticalPartyEntityDataMapper;
import gt.transparente.app.data.repository.datasource.TransparentDataStore;
import gt.transparente.app.data.repository.datasource.TransparentDataStoreFactory;
import gt.transparente.app.domain.PoliticalParty;
import gt.transparente.app.domain.repository.TransparentRepository;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

/**
 * {@link TransparentRepository} for retrieving Political Party data.
 */
@Singleton
public class TransparentDataRepository implements TransparentRepository {

  private final TransparentDataStoreFactory mTransparentDataStoreFactory;
  private final PoliticalPartyEntityDataMapper mPoliticalPartyEntityDataMapper;

  /**
   * Constructs a {@link TransparentRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param politicalPartyEntityDataMapper {@link PoliticalPartyEntityDataMapper}.
   */
  @Inject
  public TransparentDataRepository(TransparentDataStoreFactory dataStoreFactory,
                                   PoliticalPartyEntityDataMapper politicalPartyEntityDataMapper) {
    this.mTransparentDataStoreFactory = dataStoreFactory;
    this.mPoliticalPartyEntityDataMapper = politicalPartyEntityDataMapper;
  }

  @Override public Observable<List<PoliticalParty>> politicalPartyList() {
    //we always get all Political Parties from the cloud
    final TransparentDataStore transparentDataStore = this.mTransparentDataStoreFactory.createCloudDataStore();
    return transparentDataStore.politicalPartyEntityList()
            .map(mPoliticalPartyEntityDataMapper::transform);
  }

  @Override public Observable<PoliticalParty> politicalPartyDetails(int partyId) {
    final TransparentDataStore transparentDataStore = this.mTransparentDataStoreFactory.create(partyId);
    return transparentDataStore.politicalPartyEntityDetails(partyId)
        .map(mPoliticalPartyEntityDataMapper::transform);
  }
}
