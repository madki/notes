package xyz.madki.notes.ui.base;


import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import butterknife.ButterKnife;
import mortar.MortarScope;
import mortar.bundler.BundleService;
import mortar.bundler.BundleServiceRunner;
import timber.log.Timber;
import xyz.madki.notes.util.DaggerServiceKt;

public abstract class BaseActivity<P extends BasePresenter, C extends IActivityInjector> extends AppCompatActivity implements IBaseView {
  private static final String MORTAR_ACTIVITY_SCOPE_KEY = "mortar_activity_scope_key";
  private String scopeName;
  Toolbar toolbar;

  @Override
  public Object getSystemService(@NonNull String name) {
    MortarScope activityScope = MortarScope.findChild(getApplicationContext(), getScopeName());

    return activityScope != null && activityScope.hasService(name)
            ? activityScope.getService(name) : super.getSystemService(name);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restoreScopeName(savedInstanceState);

    setContentView(layout());
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    buildMortarScope();
    BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);

    C component = getComponent();
    //noinspection unchecked
    component.inject(this);
  }

  @LayoutRes
  protected abstract int layout();

  private void buildMortarScope() {
    MortarScope activityScope = MortarScope.findChild(getApplicationContext(), getScopeName());

    if (activityScope == null) {
      MortarScope.buildChild(getApplicationContext()) //
              .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
              .withService(DaggerServiceKt.getDAGGER_SERVICE(), createComponent())
              .build(getScopeName());
    }
  }

  @NotNull
  public abstract P getPresenter();

  @Override
  @SuppressWarnings("unchecked")
  protected void onStart() {
    super.onStart();
    getPresenter().takeView(this);
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void onStop() {
    super.onStop();
    getPresenter().dropView(this);
  }

  @Override
  @CallSuper
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    saveScopeName(outState);

    try {
      BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    } catch (NullPointerException e) {
      MortarScope activityScope = MortarScope.findChild(getApplicationContext(), getScopeName());
      Timber.e(e, "Scope: %s, scope exists : %b", getScopeName(), activityScope != null);
    }
  }

  @Override
  @CallSuper
  protected void onDestroy() {
    super.onDestroy();
    if (isFinishing() || isAlwaysFinishActivitiesEnabled()) {
      MortarScope scope = MortarScope.findChild(getApplicationContext(), getScopeName());
      if (scope != null) {
        scope.destroy();
      } else {
        Timber.e("Scope doesn't exist in onDestroy");
      }
    }
  }

  protected abstract C createComponent();

  protected final String getScopeName() {
    //generate a scope name if there is no last configuration instance
    if (scopeName == null) {
      scopeName = generateNewScopeName();
    }

    return scopeName;
  }

  private String generateNewScopeName() {
    boolean alreadyExists = true;
    String scopeName = null;
    while (alreadyExists) {
      scopeName = getClass().getName() + "-" + UUID.randomUUID().toString();
      alreadyExists = MortarScope.findChild(getApplicationContext(), scopeName) != null;
    }

    return scopeName;
  }

  private void saveScopeName(@Nullable Bundle outState) {
    if (outState != null) {
      outState.putString(MORTAR_ACTIVITY_SCOPE_KEY, getScopeName());
    }
  }

  private void restoreScopeName(@Nullable Bundle savedState) {
    if (savedState != null && savedState.containsKey(MORTAR_ACTIVITY_SCOPE_KEY)) {
      scopeName = savedState.getString(MORTAR_ACTIVITY_SCOPE_KEY);
    }
  }

  /**
   * To check whether "Always finish activities" or "Don't keep activities"
   * is enabled or not.
   *
   * @return true if "Don't keep Activities" is enabled, false otherwise
   */
  protected boolean isAlwaysFinishActivitiesEnabled() {
    return Settings.Global.getInt(getApplicationContext().getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0) == 1;
  }

  @Override @NotNull
  public BundleService getBundleService() {
    return BundleService.getBundleService(this);
  }


  public C getComponent() {
    return DaggerServiceKt.<C>getDaggerService(this);
  }
}
