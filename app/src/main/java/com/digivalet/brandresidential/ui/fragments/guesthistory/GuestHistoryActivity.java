package com.digivalet.brandresidential.ui.fragments.guesthistory;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentGuestHistoryBinding;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brandresidential.ui.adapters.GuestHistoryAdapter;
import com.digivalet.brandresidential.ui.fragments.guesthistorydetails.GuestHistoryDetailsActivity;
import com.digivalet.brandresidential.ui.fragments.guesthistorydetails.GuestHistoryDetailsFragment;
import com.digivalet.brandresidential.ui.fragments.residents.ResidentsActivity;
import com.digivalet.brdata.dto.GuestHistory;
import com.digivalet.brdata.dto.GuestHistorySubTitle;
import com.digivalet.utils.base.BaseActivity;
import com.digivalet.utils.base.BaseFragment;

import java.util.Objects;

public class GuestHistoryActivity extends BaseActivity<FragmentGuestHistoryBinding, GuestHistoryViewModel> implements GuestHistoryNavigator {

    private final GuestHistoryViewModel guestHistoryViewModel = new GuestHistoryViewModel();

    @Override
    public int getBindingVariable() {
        return BR.guestHistoryVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guest_history;
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
