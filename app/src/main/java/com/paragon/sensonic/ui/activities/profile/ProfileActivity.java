package com.paragon.sensonic.ui.activities.profile;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;

import com.paragon.sensonic.databinding.ActivityProfileBinding;
import com.paragon.sensonic.ui.activities.splash.SplashActivity;
import com.paragon.sensonic.utils.AppConstant;
import com.paragon.sensonic.utils.CustomItemClickListener;
import com.paragon.sensonic.utils.ProfileType;
import com.paragon.sensonic.ui.adapters.ProfileContentAdapter;
import com.paragon.sensonic.ui.fragments.sheets.InviteQRBottomDialog;
import com.paragon.sensonic.ui.activities.guesthistory.GuestHistoryActivity;
import com.paragon.sensonic.ui.activities.guestsprofile.GuestsProfileActivity;
import com.paragon.sensonic.ui.activities.interests.InterestsActivity;
import com.paragon.sensonic.ui.activities.residents.ResidentsActivity;
import com.paragon.brdata.dto.Profile;
import com.paragon.utils.MultiplePermissionsCallBack;
import com.paragon.utils.base.BaseActivity;
import com.paragon.utils.dialogs.DVDialog;
import com.paragon.utils.imagepicker.bundle.PickSetup;
import com.paragon.utils.imagepicker.dialog.PickImageDialog;
import com.karumi.dexter.PermissionToken;
import com.paragon.utils.local.AppPreference;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding, ProfileViewModel> implements ProfileNavigator, MultiplePermissionsCallBack {

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
        return R.layout.activity_profile;
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
    public void onLogout() {
        finish();
        AppPreference.getInstance(this).clearSharedPreference();
        getActivityNavigator(this).startAct(SplashActivity.class);
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
