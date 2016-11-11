package com.moffcomm.slothstay.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.SlothStayApplication;
import com.moffcomm.slothstay.customtabs.CustomTabActivityHelper;
import com.moffcomm.slothstay.model.Book;
import com.moffcomm.slothstay.model.Reservation;
import com.moffcomm.slothstay.model.User;
import com.moffcomm.slothstay.ui.fragment.HomeFragment;
import com.moffcomm.slothstay.ui.fragment.MileageFragment;
import com.moffcomm.slothstay.ui.fragment.MyReservationFragment;
import com.moffcomm.slothstay.ui.fragment.OkCancelDialogFragment;
import com.moffcomm.slothstay.ui.fragment.PartnerFragment;
import com.moffcomm.slothstay.util.Utils;

import static android.Manifest.permission.CALL_PHONE;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private Toolbar toolbar;

    private static final int MY_PERMISSIONS_REQUEST = 10;

    private int selectedReservationIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (toolbar.getVisibility() == View.GONE)
                    toolbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        User user = User.fromJsonReader(Utils.getJsonReader(this, getString(R.string.what_user)));
        ((SlothStayApplication) getApplication()).setUser(user);

        mayRequestPermissions();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Reservation reservation = intent.getParcelableExtra("reservation");
        if (reservation != null) {
            ((SlothStayApplication) getApplication()).addReservation(reservation);
            getMyReservationFragment().refresh();
            mViewPager.setCurrentItem(1);
        }

    }

    public void setSelectedReservationIndex(int selectedReservationIndex) {
        this.selectedReservationIndex = selectedReservationIndex;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_account) {
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSeeMoreClick(View v) {
        Intent intent = new Intent(this, HotelListActivity.class);
        startActivity(intent);
    }

    public void showToolBar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    public void hideToolBar() {
        toolbar.setVisibility(View.GONE);
    }

    public MyReservationFragment getMyReservationFragment() {
        String name = makeFragmentName(R.id.viewpager, 1);
        return (MyReservationFragment) getSupportFragmentManager().findFragmentByTag(name);
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }

    @Override
    public void onBackPressed() {
        final OkCancelDialogFragment okCancelDialogFragment = OkCancelDialogFragment.newInstance(null,
                getString(R.string.end), getString(android.R.string.ok), getString(android.R.string.cancel));
        okCancelDialogFragment.setOkCancelDialogListener(new OkCancelDialogFragment.OkCancelDialogListener() {
            @Override
            public void onDialogOkClick() {
                finish();
            }

            @Override
            public void onDialogCancelClick() {
            }
        });
        okCancelDialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    private boolean mayRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, CALL_PHONE)) {
            final OkCancelDialogFragment okCancelDialogFragment = OkCancelDialogFragment.newInstance(null,
                    getString(R.string.permission_rationale), getString(android.R.string.ok), getString(android.R.string.cancel));
            okCancelDialogFragment.setOkCancelDialogListener(new OkCancelDialogFragment.OkCancelDialogListener() {
                @Override
                public void onDialogOkClick() {
                    requestPermissions(new String[]{CALL_PHONE}, MY_PERMISSIONS_REQUEST);
                }

                @Override
                public void onDialogCancelClick() {
                    finish();
                }
            });
            okCancelDialogFragment.show(getSupportFragmentManager(), "dialog");
        } else {
            requestPermissions(new String[]{CALL_PHONE}, MY_PERMISSIONS_REQUEST);
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyReservationFragment.REQUEST_CODE_CHECK && resultCode == RESULT_OK) {
            Book book = data.getParcelableExtra("book");
            Reservation reservation = ((SlothStayApplication) getApplication())
                    .getReservationList().get(selectedReservationIndex);
            reservation.setBook(book);
            reservation.setCheckInDate(book.getCheckInDate());
            reservation.setCheckOutDate(book.getCheckOutDate());
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new MyReservationFragment();
                    break;
                case 2:
                    fragment = new MileageFragment();
                    break;
                case 3:
                    fragment = new PartnerFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.home);
                case 1:
                    return getString(R.string.my_reservation);
                case 2:
                    return getString(R.string.mileage);
                case 3:
                    return "제휴사";
            }
            return null;
        }
    }


}
