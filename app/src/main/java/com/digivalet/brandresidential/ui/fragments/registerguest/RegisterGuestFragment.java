package com.digivalet.brandresidential.ui.fragments.registerguest;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentRegisterGuestBinding;
import com.digivalet.brandresidential.helpers.GuestType;
import com.digivalet.brandresidential.ui.adapters.GuestAccessAdapter;
import com.digivalet.brandresidential.ui.bottomsheets.GuestRegisteredBottomDialogFragment;
import com.digivalet.brdata.dto.GuestAccess;
import com.digivalet.brdata.dto.GuestAccessX;
import com.digivalet.utils.MultiplePermissionsCallBack;
import com.digivalet.utils.base.BaseFragment;
import com.digivalet.utils.dialogs.DVDialog;
import com.digivalet.utils.expandable.ExpandableRecyclerAdapter;

import com.karumi.dexter.PermissionToken;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RegisterGuestFragment extends BaseFragment<FragmentRegisterGuestBinding, RegisterGuestViewModel>
        implements RegisterGuestNavigator, MultiplePermissionsCallBack {

    private final RegisterGuestViewModel registerGuestViewModel = new RegisterGuestViewModel();
    private GuestAccess guestAccess;
    private GuestAccessAdapter mAdapter;
    private final GuestType type;
    private GuestRegisteredBottomDialogFragment guestRegisteredBottomDialogFragment;

    public RegisterGuestFragment(GuestType type) {
        this.type = type;
    }

    @Override
    public int getBindingVariable() {
        return BR.registerGuestVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_guest;
    }

    @Override
    public RegisterGuestViewModel getViewModel() {
        return registerGuestViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerGuestViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerGuestViewModel.init();
    }

    @Override
    public void init() {
        mViewDataBinding.toolbar.toolbarTitle.setText(getString(R.string.label_guest));
        mViewDataBinding.toolbar.toolbarBackBtn.setVisibility(View.VISIBLE);
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.VISIBLE);
        mViewDataBinding.toolbar.toolbarBackBtn.setOnClickListener(e -> Objects.requireNonNull(getActivity()).onBackPressed());

        if (!type.getValue().matches(GuestType.ONE_TIME_GUEST.getValue())) {
            mViewDataBinding.rowProfile.setVisibility(View.VISIBLE);
            mViewDataBinding.rowAccompanyGuest.setVisibility(View.GONE);
            mViewDataBinding.rowAccess.setVisibility(View.GONE);
            mViewDataBinding.divider.setVisibility(View.GONE);
            mViewDataBinding.registerBtn.setText(getString(R.string.label_register_frequent_guest));
        } else {
            mViewDataBinding.rowProfile.setVisibility(View.GONE);
            mViewDataBinding.rowAccompanyGuest.setVisibility(View.VISIBLE);
            mViewDataBinding.rowAccess.setVisibility(View.VISIBLE);
            mViewDataBinding.divider.setVisibility(View.VISIBLE);
            mViewDataBinding.registerBtn.setText(getString(R.string.label_register_one_guest));
        }

        registerGuestViewModel.getExpandableList(getContext());

        /*init dialog*/
        guestRegisteredBottomDialogFragment = new GuestRegisteredBottomDialogFragment();
    }

    @Override
    public void setExpandableView(GuestAccess guestAccess) {
        this.guestAccess = guestAccess;
        GuestAccessX guestAccessX = new GuestAccessX(guestAccess.getGuestAccess().getCommonAccess());
        final List<GuestAccessX> guestAccessXList = Arrays.asList(guestAccessX);
        mAdapter = new GuestAccessAdapter(getActivity(), guestAccessXList, type.getValue());
        mAdapter.onAttachedToRecyclerView(mViewDataBinding.listAccess);
        mAdapter.expandParent(0);
        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {
                GuestAccessX expandedAccessCategory = guestAccessXList.get(position);
            }

            @Override
            public void onListItemCollapsed(int position) {
                GuestAccessX collapsedAccessCategory = guestAccessXList.get(position);
            }
        });
        mViewDataBinding.listAccess.setAdapter(mAdapter);
        mViewDataBinding.listAccess.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onRegisterClick() {
        if (!guestRegisteredBottomDialogFragment.isAdded()) {
            guestRegisteredBottomDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            guestRegisteredBottomDialogFragment.show(getChildFragmentManager(), null);
        }
    }


    @Override
    public void isAllPermissionsGranted() {
    }

    @Override
    public void isAnyPermissionPermanentlyDenied() {
        DVDialog.showSettingsDialog(getContext());
    }

    @Override
    public void somePermissionsDenied() {
        getBaseActivity().showGeneralDialog(getString(R.string.label_alert), getString(R.string.label_denied_permission), getString(R.string.label_ok), null);
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionToken token) {
        token.continuePermissionRequest();
    }

    @Override
    public void onError() {

    }
}
