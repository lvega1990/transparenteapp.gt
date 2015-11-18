
package gt.transparente.app.presentation.di.modules;

import gt.transparente.app.domain.executor.PostExecutionThread;
import gt.transparente.app.domain.executor.ThreadExecutor;
import gt.transparente.app.domain.interactor.GetPoliticalPartyDetails;
import gt.transparente.app.domain.interactor.GetPoliticalPartyList;
import gt.transparente.app.domain.interactor.UseCase;
import gt.transparente.app.domain.repository.TransparentRepository;
import gt.transparente.app.presentation.di.PerActivity;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class TransparentModule {

    private int mPoliticalPartyId = -1;

    public TransparentModule() {
    }

    public TransparentModule(int politicalPartyId) {
        this.mPoliticalPartyId = politicalPartyId;
    }

    @Provides
    @PerActivity
    @Named("politicalPartyList")
    UseCase provideGetPoliticalPartyListUseCase(
            GetPoliticalPartyList getPoliticalPartyList) {

        return getPoliticalPartyList;
    }

    @Provides
    @PerActivity
    @Named("politicalPartyDetails")
    UseCase provideGetGetPoliticalPartyDetailsUseCase(
            TransparentRepository transparentRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {

        return new GetPoliticalPartyDetails(mPoliticalPartyId,
                transparentRepository, threadExecutor, postExecutionThread);
    }
}