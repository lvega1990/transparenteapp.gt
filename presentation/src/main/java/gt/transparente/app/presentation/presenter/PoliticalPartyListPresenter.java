
package gt.transparente.app.presentation.presenter;

import android.support.annotation.NonNull;

import gt.transparente.app.data.exception.NoMoreDataAvailableException;
import gt.transparente.app.domain.PoliticalParty;
import gt.transparente.app.domain.exception.DefaultErrorBundle;
import gt.transparente.app.domain.exception.ErrorBundle;
import gt.transparente.app.domain.interactor.DefaultSubscriber;
import gt.transparente.app.domain.interactor.GetPoliticalPartyList;
import gt.transparente.app.domain.interactor.UseCase;
import gt.transparente.app.presentation.di.modules.TransparentModule_ProvideGetPoliticalPartyListUseCaseFactory;
import gt.transparente.app.presentation.exception.ErrorMessageFactory;
import gt.transparente.app.presentation.di.PerActivity;
import gt.transparente.app.presentation.mapper.PoliticalPartyModelDataMapper;
import gt.transparente.app.presentation.model.PoliticalPartyModel;
import gt.transparente.app.presentation.view.PoliticalPartyListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class PoliticalPartyListPresenter implements Presenter {

    private PoliticalPartyListView mViewListView;

    private final GetPoliticalPartyList mGetPoliticalPartyListUseCase;
    private final PoliticalPartyModelDataMapper mPoliticalPartyModelDataMapper;

    @Inject
    public PoliticalPartyListPresenter(@Named("politicalPartyList") UseCase getPoliticalPartyListUseCase, PoliticalPartyModelDataMapper politicalPartyModelDataMapper) {
        this.mGetPoliticalPartyListUseCase = (GetPoliticalPartyList) getPoliticalPartyListUseCase;
        this.mPoliticalPartyModelDataMapper = politicalPartyModelDataMapper;
    }

    public void setView(@NonNull PoliticalPartyListView view) {
        this.mViewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.mGetPoliticalPartyListUseCase.unsubscribe();
    }

    /**
     * Initializes the presenter by start retrieving the political party list.
     */
    public void initialize(int pageNumber) {
        this.loadPoliticalPartyList(pageNumber);
    }

    /**
     * Loads all political parties.
     */
    private void loadPoliticalPartyList(int pageNumber) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getPoliticalPartyList(pageNumber);
    }

    public void onPoliticalPartyClicked(PoliticalPartyModel politicalPartyModel) {
        this.mViewListView.viewPoliticalParty(politicalPartyModel);
    }

    private void showViewLoading() {
        this.mViewListView.showLoading();
    }

    private void hideViewLoading() {
        this.mViewListView.hideLoading();
    }

    private void showViewRetry() {
        this.mViewListView.showRetry();
    }

    private void hideViewRetry() {
        this.mViewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.mViewListView.getContext(),
                errorBundle.getException());
        this.mViewListView.showError(errorMessage);
    }

    private void showPoliticalPartiesCollectionInView(Collection<PoliticalParty> politicalPartyCollection) {
        final Collection<PoliticalPartyModel> PoliticalPartyModel =
                this.mPoliticalPartyModelDataMapper.transform(politicalPartyCollection);
        this.mViewListView.renderPoliticalPartyList(PoliticalPartyModel);
    }

    private void getPoliticalPartyList(int pageNumber) {
        this.mGetPoliticalPartyListUseCase.setPageNumber(pageNumber);
        this.mGetPoliticalPartyListUseCase.execute(new PoliticalPartyListSubscriber());
    }

    private final class PoliticalPartyListSubscriber extends DefaultSubscriber<List<PoliticalParty>> {

        @Override
        public void onCompleted() {
            PoliticalPartyListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PoliticalPartyListPresenter.this.hideViewLoading();
            PoliticalPartyListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            if (!(e instanceof NoMoreDataAvailableException)) {
                PoliticalPartyListPresenter.this.showViewRetry();
            }
        }

        @Override
        public void onNext(List<PoliticalParty> politicalParties) {
            PoliticalPartyListPresenter.this.showPoliticalPartiesCollectionInView(politicalParties);
        }
    }
}
