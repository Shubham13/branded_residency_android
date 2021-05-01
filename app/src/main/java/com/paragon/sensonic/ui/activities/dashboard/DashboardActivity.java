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

import com.paragon.utils.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding, DashboardViewModel> implements DashboardNavigator , BottomNavigationView.OnNavigationItemSelectedListener{

    private final DashboardViewModel dashboardViewModel = getVM(DashboardViewModel.class);
    private String selectedFragment;


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

    }

    @Override
    public void initBottomNavFragment() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_today:

                break;
            case R.id.navigation_book:


                break;
            case R.id.navigation_shop:

                break;
            case R.id.navigation_activity:

                break;
            case R.id.navigation_profile:

                break;
        }
        return true;
    }

}
