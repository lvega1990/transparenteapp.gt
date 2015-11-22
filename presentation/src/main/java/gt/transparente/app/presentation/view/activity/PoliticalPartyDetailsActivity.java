
package gt.transparente.app.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import gt.transparente.app.presentation.R;
import gt.transparente.app.presentation.di.HasComponent;
import gt.transparente.app.presentation.di.components.DaggerTransparentComponent;
import gt.transparente.app.presentation.di.components.TransparentComponent;
import gt.transparente.app.presentation.di.modules.TransparentModule;
import gt.transparente.app.presentation.view.fragment.PoliticalPartyDetailsFragment;

/**
 * Activity that shows details of a certain political party.
 */
public class PoliticalPartyDetailsActivity extends BaseActivity implements HasComponent<TransparentComponent>,
        PoliticalPartyDetailsFragment.PoliticalPartyDetailsListener {

    private static final String INTENT_EXTRA_PARAM_POLITICAL_PARTY_ID = "gt.transparente.INTENT_PARAM_POLITICAL_PARTY_ID";
    private static final String INSTANCE_STATE_PARAM_POLITICAL_PARTY_ID = "gt.transparente.STATE_PARAM_POLITICAL_PARTY_ID";

    @Bind(R.id.anim_toolbar)
    public Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    public CollapsingToolbarLayout collapsingToolbar;

    private int mPoliticalPartyId;
    private TransparentComponent mTransparentComponent;

    public static Intent getCallingIntent(Context context, int politicalPartyId) {
        Intent callingIntent = new Intent(context, PoliticalPartyDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_POLITICAL_PARTY_ID, politicalPartyId);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_political_party_details);
        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
        this.initializeView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_POLITICAL_PARTY_ID, this.mPoliticalPartyId);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
    /**
     * Initializes this activity.
     */
    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.mPoliticalPartyId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_POLITICAL_PARTY_ID, -1);
            addFragment(R.id.fl_fragment, PoliticalPartyDetailsFragment.newInstance(this.mPoliticalPartyId));
        } else {
            this.mPoliticalPartyId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_POLITICAL_PARTY_ID);
        }
    }

    private void initializeInjector() {
        ButterKnife.bind(this);
        this.mTransparentComponent = DaggerTransparentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .transparentModule(new TransparentModule(this.mPoliticalPartyId))
                .build();
    }

    @Override
    public TransparentComponent getComponent() {
        return mTransparentComponent;
    }

    @Override
    public void onRenderTitle(String title) {
        if (collapsingToolbar != null) {
            collapsingToolbar.setTitle(title);
        }
    }
}
