
package gt.transparente.app.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gt.transparente.app.presentation.R;
import gt.transparente.app.presentation.di.components.TransparentComponent;
import gt.transparente.app.presentation.model.PoliticalPartyModel;
import gt.transparente.app.presentation.presenter.PoliticalPartyDetailsPresenter;
import gt.transparente.app.presentation.view.PoliticalPartyDetailsView;

import javax.inject.Inject;

/**
 * Fragment that shows details of a certain political party.
 */
public class PoliticalPartyDetailsFragment extends BaseFragment implements PoliticalPartyDetailsView {
    /**
     * Interface to render title.
     */
    public interface PoliticalPartyDetailsListener {
        void onRenderTitle(final String title);
    }

    private static final String ARGUMENT_KEY_POLITICAL_PARTY_ID = "gt.transparente.ARGUMENT_POLITICAL_PARTY_ID";
    private int mPoliticalPartyId;

    @Inject
    PoliticalPartyDetailsPresenter politicalPartyDetailsPresenter;

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.rl_progress)
    RelativeLayout rlProgress;
    @Bind(R.id.rl_retry)
    RelativeLayout rlRetry;

    private PoliticalPartyDetailsListener mPoliticalPartyDetailsListener;

    public PoliticalPartyDetailsFragment() {
        super();
    }

    public static PoliticalPartyDetailsFragment newInstance(int politicalPartyId) {
        PoliticalPartyDetailsFragment politicalPartyDetailsFragment =
                new PoliticalPartyDetailsFragment();

        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putInt(ARGUMENT_KEY_POLITICAL_PARTY_ID, politicalPartyId);
        politicalPartyDetailsFragment.setArguments(argumentsBundle);

        return politicalPartyDetailsFragment;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof PoliticalPartyDetailsListener) {
            this.mPoliticalPartyDetailsListener = (PoliticalPartyDetailsListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_political_party_details, container, false);
        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.politicalPartyDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.politicalPartyDetailsPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.politicalPartyDetailsPresenter.destroy();
    }

    private void initialize() {
        this.getComponent(TransparentComponent.class).inject(this);
        this.politicalPartyDetailsPresenter.setView(this);
        this.mPoliticalPartyId = getArguments().getInt(ARGUMENT_KEY_POLITICAL_PARTY_ID);
        this.politicalPartyDetailsPresenter.initialize(this.mPoliticalPartyId);
    }

    @Override
    public void renderPoliticalParty(PoliticalPartyModel politicalParty) {
        if (politicalParty != null) {
            if (this.mPoliticalPartyDetailsListener != null) {
                this.mPoliticalPartyDetailsListener.onRenderTitle(politicalParty.getAbbreviation());
            }
            this.tvName.setText(politicalParty.getName());
        }
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
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    /**
     * Loads all political parties.
     */
    private void loadPoliticalPartyDetails() {
        if (this.politicalPartyDetailsPresenter != null) {
            this.politicalPartyDetailsPresenter.initialize(this.mPoliticalPartyId);
        }
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        PoliticalPartyDetailsFragment.this.loadPoliticalPartyDetails();
    }
}
