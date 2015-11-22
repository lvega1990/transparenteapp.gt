
package gt.transparente.app.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gt.transparente.app.presentation.R;
import gt.transparente.app.presentation.di.components.TransparentComponent;
import gt.transparente.app.presentation.model.PoliticalPartyModel;
import gt.transparente.app.presentation.presenter.PoliticalPartyListPresenter;
import gt.transparente.app.presentation.view.PoliticalPartyListView;
import gt.transparente.app.presentation.view.adapter.PoliticalPartyAdapter;
import gt.transparente.app.presentation.view.adapter.PoliticalPartyLayoutManager;
import gt.transparente.app.presentation.view.component.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

/**
 * Fragment that shows a list of political parties.
 */
public class PoliticalPartyListFragment extends BaseFragment implements PoliticalPartyListView {

    /**
     * Interface for listening political party list events.
     */
    public interface PoliticalPartyListListener {
        void onPoliticalPartyClicked(final PoliticalPartyModel politicalPartyModel);
    }

    @Inject
    PoliticalPartyListPresenter politicalPartyListPresenter;

    @Bind(R.id.rv_political_parties)
    RecyclerView rvPolitical_parties;
    @Bind(R.id.rl_progress)
    RelativeLayout rlProgress;
    @Bind(R.id.rl_retry)
    RelativeLayout rlRetry;

    private PoliticalPartyAdapter mPoliticalPartyAdapter;
    private PoliticalPartyListListener mPoliticalPartyListListener;

    public PoliticalPartyListFragment() {
        super();
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof PoliticalPartyListListener) {
            this.mPoliticalPartyListListener = (PoliticalPartyListListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_political_party_list, container, true);
        ButterKnife.bind(this, fragmentView);
        setupUI();

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
        this.loadPoliticalPartyList();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.politicalPartyListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.politicalPartyListPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.politicalPartyListPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initialize() {
        this.getComponent(TransparentComponent.class).inject(this);
        this.politicalPartyListPresenter.setView(this);
    }

    private void setupUI() {
        PoliticalPartyLayoutManager mPoliticalPartyLayoutManager = new PoliticalPartyLayoutManager(getContext());
        this.rvPolitical_parties.setLayoutManager(mPoliticalPartyLayoutManager);
        this.rvPolitical_parties.addOnScrollListener(new EndlessRecyclerOnScrollListener(mPoliticalPartyLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                politicalPartyListPresenter.initialize(current_page);
            }
        });
        this.mPoliticalPartyAdapter = new PoliticalPartyAdapter(getContext(), new ArrayList<PoliticalPartyModel>());
        this.mPoliticalPartyAdapter.setOnItemClickListener(onItemClickListener);
        this.rvPolitical_parties.setAdapter(mPoliticalPartyAdapter);
    }

    @Override
    public void showLoading() {
        this.rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.rlProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        this.rlRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rlRetry.setVisibility(View.GONE);
    }

    @Override
    public void renderPoliticalPartyList(Collection<PoliticalPartyModel> politicalPartyModelCollection) {
        if (politicalPartyModelCollection != null) {
            this.mPoliticalPartyAdapter.setPoliticalPartyCollection(politicalPartyModelCollection);
        }
    }

    @Override
    public void viewPoliticalParty(PoliticalPartyModel politicalPartyModel) {
        if (this.mPoliticalPartyListListener != null) {
            this.mPoliticalPartyListListener.onPoliticalPartyClicked(politicalPartyModel);
        }
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context getContext() {
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all political parties.
     */
    private void loadPoliticalPartyList() {
        this.politicalPartyListPresenter.initialize(1);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        PoliticalPartyListFragment.this.loadPoliticalPartyList();
    }

    private PoliticalPartyAdapter.OnItemClickListener onItemClickListener =
            new PoliticalPartyAdapter.OnItemClickListener() {
                @Override
                public void onPoliticalPartyItemClicked(PoliticalPartyModel politicalPartyModel) {
                    if (PoliticalPartyListFragment.this.politicalPartyListPresenter != null && politicalPartyModel != null) {
                        PoliticalPartyListFragment.this.politicalPartyListPresenter.onPoliticalPartyClicked(politicalPartyModel);
                    }
                }
            };
}
