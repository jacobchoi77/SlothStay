package com.moffcomm.slothstay.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.SlothStayApplication;
import com.moffcomm.slothstay.model.User;
import com.moffcomm.slothstay.ui.fragment.OkCancelDialogFragment;
import com.moffcomm.slothstay.util.Utils;


public class AccountActivity extends AppCompatActivity {

    private User user;
    private static final String TAG_DIALOG = "TAG_DIALOG";
    private static final int REQ_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setupActionBar();
        user = ((SlothStayApplication) getApplication()).getUser();
        if (user != null)
            setupUserContent();

    }

    private void setupUserContent() {
        findViewById(R.id.infoLinearLayout).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.nameTextView)).setText(user.getName());
        ((TextView) findViewById(R.id.emailTextView)).setText(user.getEmail());
        findViewById(R.id.loginLinearLayout).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.mileageTextView)).setText(
                getString(R.string.your_mileage, user.getName(), user.getMileage()));
        findViewById(R.id.mileageCardView).setVisibility(View.VISIBLE);
        findViewById(R.id.logoutButton).setVisibility(View.VISIBLE);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onSignInClick(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, REQ_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_LOGIN) {
            if (resultCode == RESULT_OK) {
                user = User.fromJsonReader(Utils.getJsonReader(this, getString(R.string.what_user)));
                ((SlothStayApplication) getApplication()).setUser(user);
                setupUserContent();
            }
        }
    }

    public void onLogoutClick(View view) {
        final OkCancelDialogFragment okCancelDialogFragment = OkCancelDialogFragment.newInstance(null,
                getString(R.string.logout_desc), getString(R.string.logout), getString(R.string.cancel));
        okCancelDialogFragment.setOkCancelDialogListener(new OkCancelDialogFragment.OkCancelDialogListener() {
            @Override
            public void onDialogOkClick() {
                user = null;
                ((SlothStayApplication) getApplication()).setUser(null);
                findViewById(R.id.infoLinearLayout).setVisibility(View.GONE);
                findViewById(R.id.loginLinearLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.mileageCardView).setVisibility(View.GONE);
                findViewById(R.id.logoutButton).setVisibility(View.GONE);
            }

            @Override
            public void onDialogCancelClick() {
                okCancelDialogFragment.dismiss();
            }
        });
        okCancelDialogFragment.show(getSupportFragmentManager(), TAG_DIALOG);

    }
}
