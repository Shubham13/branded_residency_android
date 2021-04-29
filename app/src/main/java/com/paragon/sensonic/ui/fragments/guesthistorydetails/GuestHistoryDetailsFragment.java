package com.paragon.sensonic.ui.fragments.guesthistorydetails;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.FragmentGuestHistoryDetailsBinding;
import com.paragon.sensonic.helpers.AppConstant;
import com.paragon.sensonic.helpers.GuestType;
import com.paragon.sensonic.ui.fragments.guestprofiledetails.GuestProfileDetailsFragment;
import com.paragon.brdata.dto.GuestHistorySubTitle;
import com.paragon.brdata.dto.GuestProfileSubTitle;
import com.paragon.utils.base.BaseFragment;

import java.util.Objects;

public class GuestHistoryDetailsFragment extends BaseFragment<FragmentGuestHistoryDetailsBinding, GuestHistoryDetailsViewModel> implements GuestHistoryDetailsNavigator {

    private final GuestHistoryDetailsViewModel guestHistoryDetailsViewModel = new GuestHistoryDetailsViewModel();
    private final GuestHistorySubTitle data;

    public GuestHistoryDetailsFragment(GuestHistorySubTitle date) {
        this.data = date;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guestHistoryDetailsViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        guestHistoryDetailsViewModel.init();
    }

    @Override
    public int getBindingVariable() {
        return BR.guestHistoryDetailsVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guest_history_details;
    }

    @Override
    public GuestHistoryDetailsViewModel getViewModel() {
        return guestHistoryDetailsViewModel;
    }

    @Override
    public void init() {
        mViewDataBinding.toolbar.toolbarRightLayout.setVisibility(View.GONE);
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.GONE);
        mViewDataBinding.toolbar.toolbarRightTitle.setVisibility(View.VISIBLE);
        mViewDataBinding.toolbar.toolbarLeftLayout.setOnClickListener(e -> Objects.requireNonNull(getActivity()).onBackPressed());
        mViewDataBinding.toolbar.toolbarRightTitle.setText(getString(R.string.label_re_register));
        mViewDataBinding.toolbar.toolbarTitle.setText(data.getTitle());
        mViewDataBinding.editName.setText(data.getTitle());
        mViewDataBinding.editPhone.setText(data.getPhone());
        mViewDataBinding.editGender.setText(data.getGender());
        mViewDataBinding.editGuestType.setText(GuestType.ONE_TIME_GUEST.getValue());
        mViewDataBinding.editEmail.setText(data.getEmail());
        mViewDataBinding.editAddress.setText(data.getAddress());
        mViewDataBinding.editDateVisited.setText(data.getDate());
        mViewDataBinding.editTime.setText(data.getTime());
        mViewDataBinding.editApprovedBy.setText(data.getApprove());
        mViewDataBinding.toolbar.toolbarRightTitle.setOnClickListener(e -> getBaseActivity().loadFragment(new GuestProfileDetailsFragment(new GuestProfileSubTitle(data.getAddress(), AppConstant.EMPTY, data.getDate(), data.getEmail(), data.getGender(), data.getImage(), data.getPhone(), data.getSubTitleId(), data.getTitle())), R.id.container));
    }
}
