/*
 * Copyright (C) 2015-2016 Lukoh Nam, goForer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.goforer.storefronter.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.goforer.base.ui.activity.BaseActivity;
import com.goforer.storefronter.R;
import com.goforer.storefronter.model.data.Profile;
import com.goforer.storefronter.ui.adapter.TabInfoPagerAdapter;
import com.goforer.storefronter.ui.fragment.TabFirstFragment;
import com.goforer.storefronter.ui.fragment.TabForthFragment;
import com.goforer.storefronter.ui.fragment.TabSecondFragment;
import com.goforer.storefronter.ui.fragment.TabThirdFragment;
import com.goforer.storefronter.ui.view.drawer.SlidingDrawer;
import com.goforer.storefronter.utility.ActivityCaller;
import com.goforer.storefronter.utility.CommonUtils;
import com.goforer.storefronter.utility.ConnectionUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private ActionBar mActionBar;
    private Profile mProfile;
    private SlidingDrawer mSlidingDrawer;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_notice)
    TextView mNoticeText;
    @BindView(R.id.viewpager)
    ViewPager mPager;
    @BindView(R.id.tabs)
    TabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mProfile = getIntent().getExtras().getParcelable(ActivityCaller.EXTRA_PROFILE);

        CommonUtils.setProfile(mProfile);

        mSlidingDrawer = new SlidingDrawer(this, SlidingDrawer.DRAWER_PROFILE_TYPE,
                R.id.drawer_container,
                savedInstanceState);
        mSlidingDrawer.setDrawerInfo(mProfile);

        super.onCreate(savedInstanceState);

        if (!ConnectionUtils.INSTANCE.isNetworkAvailable(getApplicationContext())) {
            mNoticeText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        mSlidingDrawer.setDrawerInfo(mProfile);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = mSlidingDrawer.getDrawer().saveInstanceState(outState);
        outState =  mSlidingDrawer.getCommentsDrawer().saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void setViews(Bundle savedInstanceState) {
    }

    @Override
    protected void setActionBar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
            mActionBar.setTitle(getResources().getString(R.string.app_name));
            mActionBar.setElevation(0);
            mActionBar.setDisplayShowTitleEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.cart:
                ActivityCaller.INSTANCE.callCart(this);
            case R.id.event:
                ActivityCaller.INSTANCE.callEvent(this);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void setEffectIn() {
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.scale_down_exit);
    }

    @Override
    protected void setEffectOut() {
        overridePendingTransition(R.anim.scale_up_enter, R.anim.slide_out_to_bottom);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
        setupTabInfoPager(mPager);
        mTabs.setupWithViewPager(mPager);
    }

    private void setupTabInfoPager(ViewPager viewPager) {
        TabInfoPagerAdapter adapter = new TabInfoPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabFirstFragment(), "Grocery");
        adapter.addFragment(new TabSecondFragment(), "Cosmetic");
        adapter.addFragment(new TabThirdFragment(), "Clothes");
        adapter.addFragment(new TabForthFragment(), "Appliances");
        viewPager.setAdapter(adapter);
    }

}
