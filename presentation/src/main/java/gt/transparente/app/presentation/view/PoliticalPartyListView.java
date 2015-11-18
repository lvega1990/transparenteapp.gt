
package gt.transparente.app.presentation.view;

import gt.transparente.app.presentation.model.PoliticalPartyModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link PoliticalPartyModel}.
 */
public interface PoliticalPartyListView extends LoadDataView {
    /**
     * Render a political party list in the UI.
     *
     * @param politicalPartyModelCollection The collection of {@link PoliticalPartyModel} that will be shown.
     */
    void renderPoliticalPartyList(Collection<PoliticalPartyModel> politicalPartyModelCollection);

    /**
     * View a {@link PoliticalPartyModel} profile/details.
     *
     * @param politicalPartyModel The political party that will be shown.
     */
    void viewPoliticalParty(PoliticalPartyModel politicalPartyModel);
}
