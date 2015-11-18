
package gt.transparente.app.presentation;

import android.app.Application;

import gt.transparente.app.presentation.di.components.ApplicationComponent;
import gt.transparente.app.presentation.di.components.DaggerApplicationComponent;
import gt.transparente.app.presentation.di.modules.ApplicationModule;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
