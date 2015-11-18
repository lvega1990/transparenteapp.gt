
package gt.transparente.app.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_political_party_list);

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
