package com.moffcomm.slothstay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.SlothStayApplication;
import com.moffcomm.slothstay.model.Book;
import com.moffcomm.slothstay.model.User;
import com.moffcomm.slothstay.ui.fragment.HomeFragment;
import com.moffcomm.slothstay.ui.fragment.MyReservationFragment;
import com.moffcomm.slothstay.util.Utils;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private Book book;
    private Toolbar toolbar;


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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        User user = User.fromJsonReader(Utils.getJsonReader(this, getString(R.string.what_user)));
        ((SlothStayApplication) getApplication()).setUser(user);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mViewPager.setCurrentItem(1);
        book = intent.getParcelableExtra("book");
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

    public Book getBook() {
        return book;
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
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.home);
                case 1:
                    return getString(R.string.my_reservation);
            }
            return null;
        }
    }
}
