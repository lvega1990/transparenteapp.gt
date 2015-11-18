
package gt.transparente.app.presentation.mapper;

import gt.transparente.app.domain.PoliticalParty;
import gt.transparente.app.presentation.di.PerActivity;
import gt.transparente.app.presentation.model.PoliticalPartyModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link PoliticalParty} (in the domain layer) to {@link PoliticalPartyModel} in the
 * presentation layer.
 */
@PerActivity
public class PoliticalPartyModelDataMapper {

    @Inject
    public PoliticalPartyModelDataMapper() {
    }

    /**
     * Transform a {@link PoliticalParty} into an {@link PoliticalPartyModel}.
     *
     * @param politicalParty Object to be transformed.
     * @return {@link PoliticalPartyModel}.
     */
    public PoliticalPartyModel transform(PoliticalParty politicalParty) {
        if (politicalParty == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        PoliticalPartyModel politicalPartyModel = new PoliticalPartyModel(politicalParty.getId());
        politicalPartyModel.setName(politicalParty.getName());
        politicalPartyModel.setAbbreviation(politicalParty.getAbbreviation());
        return politicalPartyModel;
    }

    /**
     * Transform a Collection of {@link PoliticalParty} into a Collection of {@link PoliticalPartyModel}.
     *
     * @param politicalPartyCollection Objects to be transformed.
     * @return List of {@link PoliticalPartyModel}.
     */
    public Collection<PoliticalPartyModel> transform(Collection<PoliticalParty> politicalPartyCollection) {
        Collection<PoliticalPartyModel> politicalPartyModelCollection;

        if (politicalPartyCollection != null && !politicalPartyCollection.isEmpty()) {
            politicalPartyModelCollection = new ArrayList<>();
            for (PoliticalParty politicalParty : politicalPartyCollection) {
                politicalPartyModelCollection.add(transform(politicalParty));
            }
        } else {
            politicalPartyModelCollection = Collections.emptyList();
        }

        return politicalPartyModelCollection;
    }
}
