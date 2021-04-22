package com.digivalet.brandresidential.ui.fragments.guesthistorydetails;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentGuestHistoryDetailsBinding;
import com.digivalet.brandresidential.helpers.AppConstant;
import com.digivalet.brandresidential.helpers.GuestType;
import com.digivalet.brandresidential.ui.fragments.guestprofiledetails.GuestProfileDetailsActivity;
import com.digivalet.brandresidential.ui.fragments.guestprofiledetails.GuestProfileDetailsFragment;
import com.digivalet.brandresidential.ui.fragments.guestsprofile.GuestsProfileActivity;
import com.digivalet.brdata.dto.GuestHistorySubTitle;
import com.digivalet.brdata.dto.GuestProfileSubTitle;
import com.digivalet.utils.base.BaseActivity;
import com.digivalet.utils.base.BaseFragment;

import java.util.Objects;

public class GuestHistoryDetailsActivity extends BaseActivity<FragmentGuestHistoryDetailsBinding, GuestHistoryDetailsViewModel> implements GuestHistoryDetailsNavigator {

    private final GuestHistoryDetailsViewModel guestHistoryDetailsViewModel = new GuestHistoryDetailsViewModel();
    private GuestHistorySubTitle data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guestHistoryDetailsViewModel.setNavigator(this);
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
        data= (GuestHistorySubTitle) getIntent().getSerializableExtra("data");
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.GONE);
        mViewDataBinding.toolbar.toolbarRightTitle.setVisibility(View.VISIBLE);
        mViewDataBinding.toolbar.toolbarLeftLayout.setOnClickListener(e -> Objects.requireNonNull(this).onBackPressed());
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
        mViewDataBinding.toolbar.toolbarRightTitle.setOnClickListener(e -> {
            //loadFragment(new GuestProfileDetailsFragment(new GuestProfileSubTitle(data.getAddress(), AppConstant.EMPTY, data.getDate(), data.getEmail(), data.getGender(), data.getImage(), data.getPhone(), data.getSubTitleId(), data.getTitle())), R.id.container)
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", new GuestProfileSubTitle(data.getAddress(), AppConstant.EMPTY, data.getDate(), data.getEmail(), data.getGender(), data.getImage(), data.getPhone(), data.getSubTitleId(), data.getTitle()));
            getActivityNavigator(GuestHistoryDetailsActivity.this).startActWithData(GuestProfileDetailsActivity.class, bundle);
        });
    }
}
