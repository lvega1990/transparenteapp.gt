
package gt.transparente.app.presentation.di.modules;

import android.content.Context;

import gt.transparente.app.data.provider.transparent.gt.TransparentProvider;
import gt.transparente.app.data.provider.transparent.gt.disk.cache.PoliticalPartyCache;
import gt.transparente.app.data.provider.transparent.gt.disk.cache.PoliticalPartyCacheImpl;
import gt.transparente.app.data.executor.JobExecutor;
import gt.transparente.app.data.provider.transparent.gt.net.TransparentImpl;
import gt.transparente.app.data.repository.TransparentDataRepository;
import gt.transparente.app.domain.executor.PostExecutionThread;
import gt.transparente.app.domain.executor.ThreadExecutor;
import gt.transparente.app.domain.repository.TransparentRepository;
import gt.transparente.app.presentation.AndroidApplication;
import gt.transparente.app.presentation.UIThread;
import gt.transparente.app.presentation.navigation.Navigator;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication mApplication;

    public ApplicationModule(AndroidApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.mApplication;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    PoliticalPartyCache providePoliticalPartyCache(PoliticalPartyCacheImpl politicalPartyCache) {
        return politicalPartyCache;
    }

    @Provides
    @Singleton
    TransparentProvider provideTransparentProvider(TransparentImpl transparentImpl) {
        return transparentImpl;
    }

    @Provides
    @Singleton
    TransparentRepository provideTransparentRepository(TransparentDataRepository transparentDataRepository) {
        return transparentDataRepository;
    }
}
