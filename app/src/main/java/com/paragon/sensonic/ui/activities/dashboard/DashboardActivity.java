package com.paragon.sensonic.ui.activities.dashboard;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

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
        mViewDataBinding.navigation.setOnNavigationItemSelectedListener(this);
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
