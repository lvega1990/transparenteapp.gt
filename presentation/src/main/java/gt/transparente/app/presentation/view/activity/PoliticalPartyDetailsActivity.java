
package gt.transparente.app.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import gt.transparente.app.presentation.R;
import gt.transparente.app.presentation.di.HasComponent;
import gt.transparente.app.presentation.di.components.DaggerTransparentComponent;
import gt.transparente.app.presentation.di.components.TransparentComponent;
import gt.transparente.app.presentation.di.modules.TransparentModule;
import gt.transparente.app.presentation.view.fragment.PoliticalPartyDetailsFragment;

/**
 * Activity that shows details of a certain political party.
 */
public class PoliticalPartyDetailsActivity extends BaseActivity implements HasComponent<TransparentComponent> {

    private static final String INTENT_EXTRA_PARAM_POLITICAL_PARTY_ID = "gt.transparente.INTENT_PARAM_POLITICAL_PARTY_ID";
    private static final String INSTANCE_STATE_PARAM_POLITICAL_PARTY_ID = "gt.transparente.STATE_PARAM_POLITICAL_PARTY_ID";

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
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_political_party_details);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_POLITICAL_PARTY_ID, this.mPoliticalPartyId);
        }
        super.onSaveInstanceState(outState);
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
}
