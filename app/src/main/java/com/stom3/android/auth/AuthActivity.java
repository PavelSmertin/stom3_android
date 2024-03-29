package com.stom3.android.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.stom3.android.BaseActivity;
import com.stom3.android.MainActivity;
import com.stom3.android.R;
import com.stom3.android.api.RestClient;
import com.stom3.android.api.response.AuthResponse;
import com.stom3.android.gcm.RegistrationIntentService;
import com.stom3.android.statistic.StatisticAdapter;
import com.stom3.android.storage.PreferencesHelper;

public class AuthActivity extends BaseActivity implements
        AuthLoginFragment.OnPhoneFragmentInteractionListener,
        AuthPasswordFragment.LoginPasswordFragmentInteractionListener {

    public enum AuthState {
        NONE,
        SIGNUP,
        SIGNIN,
        CHANGE_PIN
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        AuthLoginFragment fragment = new AuthLoginFragment();
        fm.beginTransaction().
                replace(R.id.container, fragment, AuthLoginFragment.class.getName()).
                commit();

        if (PreferencesHelper.getInstance().isAuth()) {
            next();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    void next() {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPhoneSendStart() {
        showLoader("");
    }

    @Override
    public void onPhoneSendEnd(AuthState state) {
        hideLoader();

        PreferencesHelper.getInstance().setAuthState(state);

        FragmentManager fm = getSupportFragmentManager();
        AuthPasswordFragment fragment = new AuthPasswordFragment();
        fm.beginTransaction().
                setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.back_slide_in, R.anim.back_slide_out).
                replace(R.id.container, fragment, fragment.getClass().getName()).
                addToBackStack(null).
                commitAllowingStateLoss();
    }

    @Override
    public void onPhoneSendError(String error) {
        hideLoader();
    }

    @Override
    public void onPasswordSendStart() {
        showLoader("");
    }

    @Override
    public void onPasswordSendEnd(AuthResponse response) {
        StatisticAdapter.setUser();
        PreferencesHelper.getInstance().setAuthToken(response.getToken());
        RestClient.getInstance().setHeaders(PreferencesHelper.getInstance().getLogin(), response.getToken());

        startService(new Intent(AuthActivity.this, RegistrationIntentService.class));

         //hideLoader();
        next();
    }

    @Override
    public void onPasswordSendError(String error) {

        hideLoader();
    }

    @Override
    public void onPasswordChange() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
    }

}
