
package gt.transparente.app.domain.repository;

import java.util.List;

import gt.transparente.app.domain.PoliticalParty;
import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link PoliticalParty} related data.
 */
public interface TransparentRepository {
  /**
   * Get an {@link Observable} which will emit a List of {@link PoliticalParty}.
   */
  Observable<List<PoliticalParty>> politicalPartyList();

  /**
   * Get an {@link Observable} which will emit a {@link PoliticalParty}.
   *
   * @param politicalPartyId The Political Party id used to retrieve party data.
   */
  Observable<PoliticalParty> politicalPartyDetails(final int politicalPartyId);
}
