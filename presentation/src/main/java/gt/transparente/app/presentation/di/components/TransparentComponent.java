
package gt.transparente.app.presentation.di.components;

import gt.transparente.app.presentation.di.PerActivity;
import gt.transparente.app.presentation.di.modules.ActivityModule;
import gt.transparente.app.presentation.di.modules.TransparentModule;
import gt.transparente.app.presentation.view.fragment.PoliticalPartyDetailsFragment;
import gt.transparente.app.presentation.view.fragment.PoliticalPartyListFragment;
import dagger.Component;

/**
 * A scope {@link gt.transparente.app.presentation.di.PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, TransparentModule.class})
public interface TransparentComponent extends ActivityComponent {
    void inject(PoliticalPartyListFragment politicalPartyListFragment);

    void inject(PoliticalPartyDetailsFragment politicalPartyDetailsFragment);
}
