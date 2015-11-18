
package gt.transparente.app.presentation.presenter;

import android.support.annotation.NonNull;

import gt.transparente.app.domain.PoliticalParty;
import gt.transparente.app.domain.exception.DefaultErrorBundle;
import gt.transparente.app.domain.exception.ErrorBundle;
import gt.transparente.app.domain.interactor.DefaultSubscriber;
import gt.transparente.app.domain.interactor.UseCase;
import gt.transparente.app.presentation.exception.ErrorMessageFactory;
import gt.transparente.app.presentation.di.PerActivity;
import gt.transparente.app.presentation.mapper.PoliticalPartyModelDataMapper;
import gt.transparente.app.presentation.model.PoliticalPartyModel;
import gt.transparente.app.presentation.view.PoliticalPartyDetailsView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class PoliticalPartyDetailsPresenter implements Presenter {

    /**
     * id political party to retrieve political party details
     */
    private int mPoliticalPartyId;

    private PoliticalPartyDetailsView mViewDetailsView;

    private final UseCase mGetPoliticalPartyDetailsUseCase;
    private final PoliticalPartyModelDataMapper mPoliticalPartyModelDataMapper;

    @Inject
    public PoliticalPartyDetailsPresenter(@Named("politicalPartyDetails") UseCase getPoliticalPartyDetailsUseCase,
                                          PoliticalPartyModelDataMapper politicalPartyModelDataMapper) {
        this.mGetPoliticalPartyDetailsUseCase = getPoliticalPartyDetailsUseCase;
        this.mPoliticalPartyModelDataMapper = politicalPartyModelDataMapper;
    }

    public void setView(@NonNull PoliticalPartyDetailsView view) {
        this.mViewDetailsView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.mGetPoliticalPartyDetailsUseCase.unsubscribe();
    }

    /**
     * Initializes the presenter by start retrieving political party details.
     */
    public void initialize(int politicalPartyId) {
        this.mPoliticalPartyId = politicalPartyId;
        this.loadPoliticalPartyDetails();
    }

    /**
     * Loads political party details.
     */
    private void loadPoliticalPartyDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getPoliticalPartyDetails();
    }

    private void showViewLoading() {
        this.mViewDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.mViewDetailsView.hideLoading();
    }

    private void showViewRetry() {
        this.mViewDetailsView.showRetry();
    }

    private void hideViewRetry() {
        this.mViewDetailsView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.mViewDetailsView.getContext(),
                errorBundle.getException());
        this.mViewDetailsView.showError(errorMessage);
    }

    private void showPolicalPartyDetailsInView(PoliticalParty politicalParty) {
        final PoliticalPartyModel politicalPartyModel =
                this.mPoliticalPartyModelDataMapper.transform(politicalParty);
        this.mViewDetailsView.renderPoliticalParty(politicalPartyModel);
    }

    private void getPoliticalPartyDetails() {
        this.mGetPoliticalPartyDetailsUseCase.execute(new PoliticalPartyDetailsSubscriber());
    }

    private final class PoliticalPartyDetailsSubscriber extends DefaultSubscriber<PoliticalParty> {

        @Override
        public void onCompleted() {
            PoliticalPartyDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PoliticalPartyDetailsPresenter.this.hideViewLoading();
            PoliticalPartyDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            PoliticalPartyDetailsPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(PoliticalParty politicalParty) {
            PoliticalPartyDetailsPresenter.this.showPolicalPartyDetailsInView(politicalParty);
        }
    }
}
