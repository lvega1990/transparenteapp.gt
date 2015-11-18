
package gt.transparente.app.presentation.di.components;

import android.app.Activity;

import gt.transparente.app.presentation.di.PerActivity;
import gt.transparente.app.presentation.di.modules.ActivityModule;
import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 * <p>
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link gt.transparente.app.presentation.di.PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //Exposed to sub-graphs.
    Activity activity();
}
