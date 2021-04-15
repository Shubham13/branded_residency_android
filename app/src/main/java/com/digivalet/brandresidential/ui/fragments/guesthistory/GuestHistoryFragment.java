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
import com.digivalet.brandresidential.ui.fragments.guesthistorydetails.GuestHistoryDetailsFragment;
import com.digivalet.brdata.dto.GuestHistory;
import com.digivalet.brdata.dto.GuestHistorySubTitle;
import com.digivalet.utils.base.BaseFragment;
import com.digivalet.utils.styler.Host;
import com.digivalet.utils.styler.StyleBuilder;

import java.util.Objects;

public class GuestHistoryFragment extends BaseFragment<FragmentGuestHistoryBinding, GuestHistoryViewModel> implements GuestHistoryNavigator {

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
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        guestHistoryViewModel.init();
    }

    @Override
    public void init() {
        mViewDataBinding.guestHistoryToolbar.toolbarRightImage.setVisibility(View.GONE);
        mViewDataBinding.guestHistoryToolbar.toolbarTitle.setText(getString(R.string.label_guest_history));
        mViewDataBinding.guestHistoryToolbar.toolbarLeftLayout.setOnClickListener(e -> Objects.requireNonNull(getActivity()).onBackPressed());
        guestHistoryViewModel.getGuestHistoryData(getContext());
    }

    @Override
    public void setGuestHistory(GuestHistory guestHistory) {
        mViewDataBinding.guestHistoryList.setAdapter(new GuestHistoryAdapter(getContext(), guestHistory.getData(), mViewDataBinding.guestHistoryList, new CustomItemClickListener() {
            @Override
            public void onItemClickListener(int position, Object value) {
                getBaseActivity().loadFragment(new GuestHistoryDetailsFragment((GuestHistorySubTitle) value), R.id.container);
            }
        }));
    }
}
