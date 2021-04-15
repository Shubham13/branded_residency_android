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
import com.digivalet.brandresidential.ui.fragments.guesthistory.GuestHistoryActivity;
import com.digivalet.brandresidential.ui.fragments.guesthistory.GuestHistoryFragment;
import com.digivalet.brandresidential.ui.fragments.guestsprofile.GuestsProfileActivity;
import com.digivalet.brandresidential.ui.fragments.guestsprofile.GuestsProfileFragment;
import com.digivalet.brandresidential.ui.fragments.interests.InterestsActivity;
import com.digivalet.brandresidential.ui.fragments.interests.InterestsFragment;
import com.digivalet.brandresidential.ui.fragments.residents.ResidentsActivity;
import com.digivalet.brandresidential.ui.fragments.residents.ResidentsFragment;
import com.digivalet.brdata.dto.Profile;
import com.digivalet.utils.MultiplePermissionsCallBack;
import com.digivalet.utils.base.BaseActivity;
import com.digivalet.utils.base.BaseFragment;
import com.digivalet.utils.dialogs.DVDialog;
import com.digivalet.utils.imagepicker.bundle.PickSetup;
import com.digivalet.utils.imagepicker.dialog.PickImageDialog;
import com.karumi.dexter.PermissionToken;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity<FragmentProfileBinding, ProfileViewModel> implements ProfileNavigator, MultiplePermissionsCallBack {

    private final ProfileViewModel profileViewModel = new ProfileViewModel();
    private List<Profile.HeaderTitle> list;
    private ProfileContentAdapter adapter;
    private InviteQRBottomDialog dialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel.setNavigator(this);
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
        //loadFragment(new ResidentsFragment(type), R.id.container);
        Bundle bundle = new Bundle();
        bundle.putSerializable("type",type);
        getActivityNavigator(this).startActWithData(ResidentsActivity.class,bundle);
    }

    @Override
    public void navigateToGuestsProfile() {
        //loadFragment(new GuestsProfileFragment(), R.id.container);
        getActivityNavigator(this).startAct(GuestsProfileActivity.class);
    }

    @Override
    public void setProfileData(Profile data) {
        list.addAll(data.getHeaderTitles());
        adapter.notifyDataSetChanged();
        mViewDataBinding.profileUserName.setText(data.getHeaderProfile().getName().getFirstname() + AppConstant.SPACE + data.getHeaderProfile().getName().getLastname());
    }


    @Override
    public void onProfileImageBrowseClick() {
        requestMultiplePermissions(ProfileActivity.this, AppConstant.READ_WRITE_EXTERNAL_STORAGE_AND_CAMERA);
    }

    @Override
    public void init() {
        list = new ArrayList<>();
        adapter = new ProfileContentAdapter(this, list, new CustomItemClickListener() {
            @Override
            public void onItemClickListener(int position, String type) {
                profileViewModel.navigateToList(ProfileType.valueOf(type));
            }
        });
        profileViewModel.getProfileData(this);
        mViewDataBinding.profileContentList.setAdapter(adapter);
        dialog = new InviteQRBottomDialog();
    }


    @Override
    public void onInviteQrClick() {
        if (!dialog.isAdded()) {
            dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            dialog.show(getSupportFragmentManager(), null);
        }
    }

    @Override
    public void onInterestsItemClick() {
       // loadFragment(new InterestsFragment(), R.id.container);
        getActivityNavigator(this).startAct(InterestsActivity.class);
    }

    @Override
    public void onGuestHistoryClick() {
        //loadFragment(new GuestHistoryFragment(), R.id.container);
        getActivityNavigator(this).startAct(GuestHistoryActivity.class);
    }

    @Override
    public void isAllPermissionsGranted() {
        PickImageDialog.build(new PickSetup())
                .setOnPickResult(r -> mViewDataBinding.profileImageView.setImageBitmap(r.getBitmap())).show(getSupportFragmentManager());
    }

    @Override
    public void isAnyPermissionPermanentlyDenied() {
        DVDialog.showSettingsDialog(this);
    }

    @Override
    public void somePermissionsDenied() {
        showGeneralDialog(getString(R.string.label_alert), getString(R.string.label_denied_permission), getString(R.string.label_ok), null);
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
