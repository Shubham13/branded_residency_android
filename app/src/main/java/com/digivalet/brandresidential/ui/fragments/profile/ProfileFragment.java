package com.digivalet.brandresidential.ui.fragments.profile;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentProfileBinding;
import com.digivalet.brandresidential.helpers.AppConstant;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brandresidential.helpers.ProfileType;
import com.digivalet.brandresidential.ui.adapters.ProfileContentAdapter;
import com.digivalet.brandresidential.ui.bottomsheets.InviteQRBottomDialog;
import com.digivalet.brandresidential.ui.fragments.guesthistory.GuestHistoryFragment;
import com.digivalet.brandresidential.ui.fragments.guestsprofile.GuestsProfileFragment;
import com.digivalet.brandresidential.ui.fragments.interests.InterestsFragment;
import com.digivalet.brandresidential.ui.fragments.residents.ResidentsFragment;
import com.digivalet.brdata.dto.Profile;
import com.digivalet.utils.MultiplePermissionsCallBack;
import com.digivalet.utils.base.BaseFragment;
import com.digivalet.utils.dialogs.DVDialog;
import com.digivalet.utils.imagepicker.bundle.PickSetup;
import com.digivalet.utils.imagepicker.dialog.PickImageDialog;
import com.karumi.dexter.PermissionToken;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> implements ProfileNavigator, MultiplePermissionsCallBack {

    private final ProfileViewModel profileViewModel = new ProfileViewModel();
    private List<Profile.HeaderTitle> list;
    private ProfileContentAdapter adapter;
    private InviteQRBottomDialog dialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel.init();
    }

    @Override
    public int getBindingVariable() {
        return BR.profileVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public ProfileViewModel getViewModel() {
        return profileViewModel;
    }

    @Override
    public void navigateToList(ProfileType type) {
        getBaseActivity().loadFragment(new ResidentsFragment(type), R.id.container);
    }

    @Override
    public void navigateToGuestsProfile() {
        getBaseActivity().loadFragment(new GuestsProfileFragment(), R.id.container);
    }

    @Override
    public void setProfileData(Profile data) {
        list.addAll(data.getHeaderTitles());
        adapter.notifyDataSetChanged();
        mViewDataBinding.profileUserName.setText(data.getHeaderProfile().getName().getFirstname() + AppConstant.SPACE + data.getHeaderProfile().getName().getLastname());
    }


    @Override
    public void onProfileImageBrowseClick() {
        getBaseActivity().requestMultiplePermissions(ProfileFragment.this, AppConstant.READ_WRITE_EXTERNAL_STORAGE_AND_CAMERA);
    }

    @Override
    public void init() {
        list = new ArrayList<>();
        adapter = new ProfileContentAdapter(getContext(), list, new CustomItemClickListener() {
            @Override
            public void onItemClickListener(int position, String type) {
                profileViewModel.navigateToList(ProfileType.valueOf(type));
            }
        });
        profileViewModel.getProfileData(getContext());
        mViewDataBinding.profileContentList.setAdapter(adapter);
        dialog = new InviteQRBottomDialog();
    }


    @Override
    public void onInviteQrClick() {
        if (!dialog.isAdded()) {
            dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            dialog.show(getChildFragmentManager(), null);
        }
    }

    @Override
    public void onInterestsItemClick() {
        getBaseActivity().loadFragment(new InterestsFragment(), R.id.container);
    }

    @Override
    public void onGuestHistoryClick() {
        getBaseActivity().loadFragment(new GuestHistoryFragment(), R.id.container);
    }

    @Override
    public void isAllPermissionsGranted() {
        PickImageDialog.build(new PickSetup())
                .setOnPickResult(r -> mViewDataBinding.profileImageView.setImageBitmap(r.getBitmap())).show(getChildFragmentManager());
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
    public void onError() {
        //TODO have to handel later
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionToken token) {
        token.continuePermissionRequest();
    }
}
