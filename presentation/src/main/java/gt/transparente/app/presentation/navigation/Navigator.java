
package gt.transparente.app.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import gt.transparente.app.presentation.view.activity.PoliticalPartyDetailsActivity;
import gt.transparente.app.presentation.view.activity.PoliticalPartyListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public void Navigator() {
        //empty
    }

    /**
     * Goes to the political party list screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToPoliticalPartyList(Context context) {
        if (context != null) {
            Intent intentToLaunch = PoliticalPartyListActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the Political Party details screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToPoliticalPartyDetails(Context context, int politicalPartyId) {
        if (context != null) {
            Intent intentToLaunch = PoliticalPartyDetailsActivity.getCallingIntent(context, politicalPartyId);
            context.startActivity(intentToLaunch);
        }
    }
}
