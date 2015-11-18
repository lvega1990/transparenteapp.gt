package gt.transparente.app.presentation.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import gt.transparente.app.presentation.AndroidApplication;
import gt.transparente.app.presentation.di.components.ApplicationComponent;
import gt.transparente.app.presentation.di.modules.ActivityModule;
import gt.transparente.app.presentation.navigation.Navigator;
import javax.inject.Inject;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends Activity {

  @Inject Navigator navigator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
  }

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link gt.transparente.app.presentation.di.components.ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication)getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link gt.transparente.app.presentation.di.modules.ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }
}
