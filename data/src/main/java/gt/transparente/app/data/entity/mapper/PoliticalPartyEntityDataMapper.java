
package gt.transparente.app.data.entity.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import gt.transparente.app.data.entity.PoliticalPartyEntity;
import gt.transparente.app.domain.PoliticalParty;


/**
 * Mapper class used to transform {@link PoliticalPartyEntity} (in the data layer) to {@link PoliticalParty} in the
 * domain layer.
 */
@Singleton
public class PoliticalPartyEntityDataMapper {

    @Inject
    public PoliticalPartyEntityDataMapper() {
    }

    /**
     * Transform a {@link PoliticalPartyEntity} into an {@link PoliticalParty}.
     *
     * @param politicalPartyEntity Object to be transformed.
     * @return {@link PoliticalParty} if valid {@link PoliticalPartyEntity} otherwise null.
     */
    public PoliticalParty transform(PoliticalPartyEntity politicalPartyEntity) {
        PoliticalParty politicalParty = null;
        if (politicalPartyEntity != null) {
            politicalParty = new PoliticalParty(politicalPartyEntity.getId());
            politicalParty.setName(politicalPartyEntity.getName());
            politicalParty.setAbbreviation(politicalPartyEntity.getAbbreviation());
        }

        return politicalParty;
    }

    /**
     * Transform a List of {@link PoliticalPartyEntity} into a Collection of {@link PoliticalParty}.
     *
     * @param politicalPartyEntities Object Collection to be transformed.
     * @return {@link PoliticalParty} if valid {@link PoliticalPartyEntity} otherwise null.
     */
    public List<PoliticalParty> transform(Collection<PoliticalPartyEntity> politicalPartyEntities) {
        List<PoliticalParty> politicalParties = new ArrayList<>();
        PoliticalParty politicalParty;
        for (PoliticalPartyEntity politicalPartyEntity: politicalPartyEntities) {
            politicalParty = transform(politicalPartyEntity);
            if (politicalParty != null) {
                politicalParties.add(politicalParty);
            }
        }

        return politicalParties;
    }
}
