
package gt.transparente.app.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private static final String ARGUMENT_KEY_POLITICAL_PARTY_ID = "gt.transparente.ARGUMENT_POLITICAL_PARTY_ID";
    private int mPoliticalPartyId;

    @Inject
    PoliticalPartyDetailsPresenter politicalPartyDetailsPresenter;

    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_abbreviation)
    TextView tv_abbreviation;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

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
            this.tv_name.setText(politicalParty.getName());
            this.tv_abbreviation.setText(politicalParty.getAbbreviation());
        }
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
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
