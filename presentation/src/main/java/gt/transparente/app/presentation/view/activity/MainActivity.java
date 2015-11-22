
package gt.transparente.app.presentation.view.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.Button;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gt.transparente.app.presentation.R;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.collapsing_toolbar)
    public CollapsingToolbarLayout collapsingToolbar;
    @BindString(R.string.app_name)
    public String appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        collapsingToolbar.setTitle(appName);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    /**
     * Goes to the political party list screen.
     */
    @OnClick(R.id.cv_political_parties)
    void navigateToPoliticalPartyList() {
        this.navigator.navigateToPoliticalPartyList(this);
    }
}
