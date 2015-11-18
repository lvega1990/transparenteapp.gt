
package gt.transparente.app.presentation.di.components;

import android.content.Context;

import gt.transparente.app.domain.executor.PostExecutionThread;
import gt.transparente.app.domain.executor.ThreadExecutor;
import gt.transparente.app.domain.repository.TransparentRepository;
import gt.transparente.app.presentation.di.modules.ApplicationModule;
import gt.transparente.app.presentation.view.activity.BaseActivity;
import dagger.Component;

import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    TransparentRepository transparentRepository();
}
