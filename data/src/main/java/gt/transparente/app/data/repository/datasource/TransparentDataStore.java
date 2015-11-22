
package gt.transparente.app.data.repository.datasource;

import gt.transparente.app.data.entity.PoliticalPartyEntity;
import java.util.List;
import rx.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface TransparentDataStore {
  /**
   * Get an {@link rx.Observable} which will emit a List of {@link PoliticalPartyEntity}.
   */
  Observable<List<PoliticalPartyEntity>> politicalPartyEntityList(final int pageNumber);

  /**
   * Get an {@link rx.Observable} which will emit a {@link PoliticalPartyEntity} by its id.
   *
   * @param partyId The id to retrieve party data.
   */
  Observable<PoliticalPartyEntity> politicalPartyEntityDetails(final int partyId);
}
