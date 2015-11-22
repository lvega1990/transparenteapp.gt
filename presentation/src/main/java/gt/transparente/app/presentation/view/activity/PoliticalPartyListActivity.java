
package gt.transparente.app.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import gt.transparente.app.presentation.R;
import gt.transparente.app.presentation.di.HasComponent;
import gt.transparente.app.presentation.di.components.DaggerTransparentComponent;
import gt.transparente.app.presentation.di.components.TransparentComponent;
import gt.transparente.app.presentation.model.PoliticalPartyModel;
import gt.transparente.app.presentation.view.fragment.PoliticalPartyListFragment;

/**
 * Activity that shows a list of Political Parties.
 */
public class PoliticalPartyListActivity extends BaseActivity implements HasComponent<TransparentComponent>,
        PoliticalPartyListFragment.PoliticalPartyListListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PoliticalPartyListActivity.class);
    }

    private TransparentComponent mTransparentComponent;
    private CollapsingToolbarLayout mCollapsingToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_political_party_list);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbar.setTitle("Transparente.gt");
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.mTransparentComponent = DaggerTransparentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public TransparentComponent getComponent() {
        return mTransparentComponent;
    }

    @Override
    public void onPoliticalPartyClicked(PoliticalPartyModel politicalPartyModel) {
        this.navigator.navigateToPoliticalPartyDetails(this, politicalPartyModel.getId());
    }
}
