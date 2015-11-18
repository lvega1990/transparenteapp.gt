package gt.transparente.app.presentation.view;

import gt.transparente.app.presentation.model.PoliticalPartyModel;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a political party profile.
 */
public interface PoliticalPartyDetailsView extends LoadDataView {
  /**
   * Render a political party in the UI.
   *
   * @param politicalParty The {@link PoliticalPartyModel} that will be shown.
   */
  void renderPoliticalParty(PoliticalPartyModel politicalParty);
}
