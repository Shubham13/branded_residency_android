package com.paragon.sensonic.ui.activities.guesthistory;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;

import com.paragon.sensonic.databinding.ActivityGuestHistoryBinding;
import com.paragon.sensonic.utils.CustomItemClickListener;
import com.paragon.sensonic.ui.adapters.GuestHistoryAdapter;
import com.paragon.sensonic.ui.activities.guesthistorydetails.GuestHistoryDetailsActivity;
import com.paragon.brdata.dto.GuestHistory;
import com.paragon.brdata.dto.GuestHistorySubTitle;
import com.paragon.utils.base.BaseActivity;

import java.util.Objects;

public class GuestHistoryActivity extends BaseActivity<ActivityGuestHistoryBinding, GuestHistoryViewModel> implements GuestHistoryNavigator {

    private final GuestHistoryViewModel guestHistoryViewModel = new GuestHistoryViewModel();

    @Override
    public int getBindingVariable() {
        return BR.guestHistoryVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guest_history;
    }

    @Override
    public GuestHistoryViewModel getViewModel() {
        return guestHistoryViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guestHistoryViewModel.setNavigator(this);
        guestHistoryViewModel.init();
    }

    @Override
    public void init() {
        mViewDataBinding.guestHistoryToolbar.toolbarRightImage.setVisibility(View.GONE);
        mViewDataBinding.guestHistoryToolbar.toolbarTitle.setText(getString(R.string.label_guest_history));
        mViewDataBinding.guestHistoryToolbar.toolbarLeftLayout.setOnClickListener(e -> Objects.requireNonNull(this).onBackPressed());
        guestHistoryViewModel.getGuestHistoryData(this);
    }

    @Override
    public void setGuestHistory(GuestHistory guestHistory) {
        mViewDataBinding.guestHistoryList.setAdapter(new GuestHistoryAdapter(this, guestHistory.getData(), mViewDataBinding.guestHistoryList, new CustomItemClickListener() {
            @Override
            public void onItemClickListener(int position, Object value) {
                //loadFragment(new GuestHistoryDetailsFragment((GuestHistorySubTitle) value), R.id.container);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (GuestHistorySubTitle) value);
                getActivityNavigator(GuestHistoryActivity.this).startActWithData(GuestHistoryDetailsActivity.class, bundle);
            }
        }));
    }
}
