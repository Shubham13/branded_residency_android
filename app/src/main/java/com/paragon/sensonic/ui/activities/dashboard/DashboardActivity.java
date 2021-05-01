package com.paragon.sensonic.ui.activities.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.material.appbar.AppBarLayout;
import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;

import com.paragon.sensonic.databinding.ActivityDashboardBinding;
import com.paragon.sensonic.ui.fragments.activity.ActivityFragment;
import com.paragon.sensonic.ui.fragments.book.BookFragment;
import com.paragon.sensonic.ui.fragments.profile.ProfileFragment;
import com.paragon.sensonic.ui.fragments.shop.ShopFragment;
import com.paragon.sensonic.ui.fragments.today.TodayFragment;
import com.paragon.utils.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding, DashboardViewModel> implements DashboardNavigator , BottomNavigationView.OnNavigationItemSelectedListener{

    private final DashboardViewModel dashboardViewModel = getVM(DashboardViewModel.class);
    private String selectedFragment;
    private TodayFragment todayFragment;
    private BookFragment bookFragment;
    private ShopFragment shopFragment;
    private ProfileFragment profileFragment;
    private ActivityFragment activityFragment;

    @Override
    public int getBindingVariable() {
        return BR.dashboardVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dashboard;
    }

    @Override
    public DashboardViewModel getViewModel() {
        return dashboardViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().setNavigator(this);
        getViewModel().init();
    }

    @Override
    public void init() {
        setSupportActionBar(mViewDataBinding.toolbar);
        mViewDataBinding.navigation.setOnNavigationItemSelectedListener(this);

        mViewDataBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0) {
                    Log.e("state","Collapsed");
                    mViewDataBinding.toolbarTitle.setVisibility(View.VISIBLE);
                    mViewDataBinding.toolbarRightImage.setVisibility(View.VISIBLE);
                    mViewDataBinding.toolbarCenterRightImage.setVisibility(View.GONE);
                    mViewDataBinding.toolbarCenterTitle.setVisibility(View.GONE);
                    mViewDataBinding.toolbarLayout.setBackgroundColor(getColor(R.color.dark_background));
                }
                else {
                    //Expanded
                    mViewDataBinding.toolbarTitle.setVisibility(View.GONE);
                    mViewDataBinding.toolbarRightImage.setVisibility(View.GONE);
                    mViewDataBinding.toolbarCenterRightImage.setVisibility(View.VISIBLE);
                    mViewDataBinding.toolbarCenterTitle.setVisibility(View.VISIBLE);
                    mViewDataBinding.toolbarLayout.setBackgroundColor(getColor(R.color.bar_tabbar));
                    Log.e("state","Expanded");
                }
            }
        });
    }

    @Override
    public void loadDefaultFragment() {
        selectedFragment = todayFragment.getClass().getName();
        loadDefaultFragment(todayFragment, R.id.container);
    }

    @Override
    public void initBottomNavFragment() {
        todayFragment = new TodayFragment();
        profileFragment = new ProfileFragment();
        activityFragment = new ActivityFragment();
        bookFragment = new BookFragment();
        shopFragment= new ShopFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_today:
                if (!todayFragment.getClass().getName().matches(selectedFragment)) {
                    selectedFragment = todayFragment.getClass().getName();
                    loadDefaultFragment(todayFragment, mViewDataBinding.container.getId());
                }

                break;
            case R.id.navigation_book:
                if (!bookFragment.getClass().getName().matches(selectedFragment)) {
                    selectedFragment = bookFragment.getClass().getName();
                    loadDefaultFragment(bookFragment, mViewDataBinding.container.getId());
                }

                break;
            case R.id.navigation_shop:
                if (!shopFragment.getClass().getName().matches(selectedFragment)) {
                    selectedFragment = shopFragment.getClass().getName();
                    loadDefaultFragment(shopFragment, mViewDataBinding.container.getId());
                }

                break;
            case R.id.navigation_activity:
                if (!activityFragment.getClass().getName().matches(selectedFragment)) {
                    selectedFragment = activityFragment.getClass().getName();
                    loadDefaultFragment(activityFragment, mViewDataBinding.container.getId());
                }
                break;
            case R.id.navigation_profile:
                if (!profileFragment.getClass().getName().matches(selectedFragment)) {
                    selectedFragment = profileFragment.getClass().getName();
                    loadDefaultFragment(profileFragment, mViewDataBinding.container.getId());
                }

                break;
        }
        return true;
    }

}
