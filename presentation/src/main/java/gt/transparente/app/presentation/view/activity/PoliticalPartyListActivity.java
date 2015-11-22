
package gt.transparente.app.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
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

    @Bind(R.id.anim_toolbar)
    public Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    public CollapsingToolbarLayout collapsingToolbar;
    @BindString(R.string.app_name)
    public String appName;

    private TransparentComponent mTransparentComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_political_party_list);
        this.initializeInjector();
        this.initializeView();
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
        collapsingToolbar.setTitle(appName);
    }

    private void initializeInjector() {
        ButterKnife.bind(this);
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
