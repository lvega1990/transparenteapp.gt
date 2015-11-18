
package gt.transparente.app.data.provider.transparent.gt;

import java.util.List;

import gt.transparente.app.data.entity.PoliticalPartyEntity;
import rx.Observable;

public interface TransparentProvider {

    /**
     * Retrieves an {@link rx.Observable} which will emit a List of {@link PoliticalPartyEntity}.
     */
    Observable<List<PoliticalPartyEntity>> politicalPartyEntityList();

    /**
     * Retrieves an {@link rx.Observable} which will emit a {@link PoliticalPartyEntity}.
     *
     * @param politicalPartyId The Political Party id used to get political party data.
     */
    Observable<PoliticalPartyEntity> politicalPartyEntityById(final int politicalPartyId);
}
