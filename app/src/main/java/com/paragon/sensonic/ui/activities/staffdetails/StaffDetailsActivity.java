package com.paragon.sensonic.ui.activities.staffdetails;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;

import com.paragon.sensonic.databinding.RowContactInfoBinding;
import com.paragon.sensonic.databinding.RowIdentificationHolderBinding;
import com.paragon.sensonic.databinding.RowIdentifictionBinding;
import com.paragon.sensonic.databinding.RowResidentInfoBinding;
import com.paragon.sensonic.utils.AppConstant;
import com.paragon.sensonic.utils.CustomItemClickListener;
import com.paragon.sensonic.utils.ProfileMode;
import com.paragon.sensonic.ui.adapters.ProfileFilterAdapter;
import com.paragon.sensonic.ui.fragments.sheets.CalenderBottomDialogFragment;
import com.paragon.sensonic.ui.fragments.sheets.SpinnerBottomDialogFragment;
import com.paragon.sensonic.ui.views.countrypicker.CountryPicker;
import com.paragon.brdata.dto.StaffData;
import com.paragon.utils.GeneralFunctions;
import com.paragon.utils.MultiplePermissionsCallBack;
import com.paragon.utils.RecyclerItemClickListener;
import com.paragon.utils.base.BaseActivity;
import com.paragon.utils.dialogs.DVDialog;
import com.paragon.utils.imagepicker.bundle.PickSetup;
import com.paragon.utils.imagepicker.dialog.PickImageDialog;
import com.karumi.dexter.PermissionToken;

import java.util.Arrays;
import java.util.Objects;

public class StaffDetailsActivity extends BaseActivity<com.paragon.sensonic.databinding.ActivityResidentDetailsBinding, StaffDetailsViewModel>
        implements StaffDetailsNavigator {

    private final StaffDetailsViewModel staffDetailsViewModel = new StaffDetailsViewModel();
    private RowResidentInfoBinding rowResidentInfoBinding;
    private RowContactInfoBinding rowContactInfoBinding;
    private RowIdentificationHolderBinding rowIdentificationHolderBinding;
    private ProfileFilterAdapter filterAdapter;
    private StaffData data;
    private ProfileMode mode;
    private boolean isToolbarItemClick = false;
    private SpinnerBottomDialogFragment relationSpinner, occupationSpinner, statusSpinner;
    private CalenderBottomDialogFragment calenderBottomDialogFragment;

    @Override
    public int getBindingVariable() {
        return BR.staffDetailsVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resident_details;
    }

    @Override
    public StaffDetailsViewModel getViewModel() {
        return staffDetailsViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staffDetailsViewModel.setNavigator(this);
        staffDetailsViewModel.init();
    }
    
    @Override
    public void init() {
        data = (StaffData) getIntent().getSerializableExtra("data");
        mode = (ProfileMode) getIntent().getSerializableExtra("mode");
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.GONE);
        mViewDataBinding.toolbar.toolbarLeftLayout.setOnClickListener(e -> Objects.requireNonNull(this).onBackPressed());

        /*add scroll listener on nested scroll*/
        final Rect scrollBounds = new Rect();
        mViewDataBinding.nestedScroll.getHitRect(scrollBounds);

        mViewDataBinding.nestedScroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!isToolbarItemClick) {
                if (scrollY == 0) {
                    filterAdapter.setPosition(0);
                } else if (mViewDataBinding.container.getChildAt(1) != null) {

                    if (mViewDataBinding.container.getChildAt(1).getLocalVisibleRect(scrollBounds)) {
                        if (mViewDataBinding.container.getChildAt(1).getLocalVisibleRect(scrollBounds)
                                || scrollBounds.height() > mViewDataBinding.container.getChildAt(1).getHeight()) {
                            filterAdapter.setPosition(1);
                        }
                    }

                    if (mViewDataBinding.container.getChildAt(2).getLocalVisibleRect(scrollBounds)) {
                        if (mViewDataBinding.container.getChildAt(2).getLocalVisibleRect(scrollBounds)
                                || scrollBounds.height() > mViewDataBinding.container.getChildAt(2).getHeight()) {
                            filterAdapter.setPosition(2);
                        }
                    }
                }
            }
        });

        /*add touch listener*/
        mViewDataBinding.nestedScroll.setOnTouchListener((view, motionEvent) -> {
            isToolbarItemClick = false;
            return false;
        });

        /*init spinner data resources*/
        relationSpinner = new SpinnerBottomDialogFragment(this.getString(R.string.label_relation_to_owner),
                Arrays.asList(this.getResources().getStringArray(R.array.label_relation)));

        statusSpinner = new SpinnerBottomDialogFragment(this.getString(R.string.label_status),
                Arrays.asList(this.getResources().getStringArray(R.array.label_status)));

        occupationSpinner = new SpinnerBottomDialogFragment(this.getString(R.string.label_occupation),
                Arrays.asList(this.getResources().getStringArray(R.array.label_occupation)));

        calenderBottomDialogFragment = new CalenderBottomDialogFragment();
    }

    @Override
    public void setFilterList() {
        filterAdapter = new ProfileFilterAdapter(this, Arrays.asList(getResources().getStringArray(R.array.label_filters_residents_details)));
        mViewDataBinding.toolbar.toolbarItemList.setAdapter(filterAdapter);
        mViewDataBinding.toolbar.toolbarItemList.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
            filterAdapter.setPosition(position);
            isToolbarItemClick = true;
            mViewDataBinding.container.post(() -> {
                float y = mViewDataBinding.container.getY() + mViewDataBinding.container.getChildAt(position).getY();
                mViewDataBinding.nestedScroll.smoothScrollTo(0, (int) y);
            });
        }));
    }

    @Override
    public void loadResidentDetailsView() {
        rowResidentInfoBinding = DataBindingUtil
                .inflate(LayoutInflater.from(this), R.layout.row_resident_info,
                        mViewDataBinding.container, false);

        if (mode.equals(ProfileMode.NEW)) {
            mViewDataBinding.toolbar.toolbarTitle.setText(getString(R.string.label_guests));
            rowResidentInfoBinding.profileImageView.setImageResource(R.mipmap.ic_friends);
            rowResidentInfoBinding.editName.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editPreferredName.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editRelationToOwner.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editStatus.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editOccupation.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editBirthday.setText(AppConstant.EMPTY);
        } else {
            mViewDataBinding.toolbar.toolbarTitle.setText(data.getName());
            rowResidentInfoBinding.profileImageView.setImageResource(R.mipmap.ic_staff);
            rowResidentInfoBinding.editName.setText(data.getName());
            //rowResidentInfoBinding.editPreferredName.setText(data.getPrefferedName());
            //rowResidentInfoBinding.editRelationToOwner.setText(data.getRelation());
            //rowResidentInfoBinding.editStatus.setText(data.getStatus());
            //rowResidentInfoBinding.editOccupation.setText(data.getOccupation());
            //rowResidentInfoBinding.editBirthday.setText(data.getBirthday());
        }

        /*image browse*/
        rowResidentInfoBinding.rowProfileImage.setOnClickListener(view -> {
            requestMultiplePermissions(new MultiplePermissionsCallBack() {
                @Override
                public void isAllPermissionsGranted() {
                    PickImageDialog.build(new PickSetup()).setOnPickResult(r -> rowResidentInfoBinding.profileImageView.setImageBitmap(r.getBitmap())).show(getSupportFragmentManager());
                }

                @Override
                public void isAnyPermissionPermanentlyDenied() {
                    DVDialog.showSettingsDialog(getApplicationContext());
                }

                @Override
                public void somePermissionsDenied() {
                    showGeneralDialog(getString(R.string.label_alert), getString(R.string.label_denied_permission), getString(R.string.label_ok), null);
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionToken token) {
                    token.continuePermissionRequest();
                }

                @Override
                public void onError() {

                }
            }, AppConstant.READ_WRITE_EXTERNAL_STORAGE_AND_CAMERA);
        });

        mViewDataBinding.container.addView(rowResidentInfoBinding.getRoot());
    }

    @Override
    public void loadContactInfoView() {
        rowContactInfoBinding = DataBindingUtil
                .inflate(LayoutInflater.from(this), R.layout.row_contact_info,
                        mViewDataBinding.container, false);

        if (mode.equals(ProfileMode.NEW)) {
            rowContactInfoBinding.editMobileNo.setText(AppConstant.EMPTY);
            rowContactInfoBinding.editEmail.setText(AppConstant.EMPTY);
            rowContactInfoBinding.editAltAddress.setText(AppConstant.EMPTY);

        } else {
            //rowContactInfoBinding.editMobileNo.setText(data.getMobile());
            //rowContactInfoBinding.editEmail.setText(data.getEmail());
            //rowContactInfoBinding.editAltAddress.setText(data.getAddress());
        }

        /*default from device*/
        rowContactInfoBinding.editCountryCode.setText(GeneralFunctions.getCountryMobileCode(this));
        rowContactInfoBinding.flagImageView.setText(GeneralFunctions.getCountryFlag(this.getResources().getConfiguration().locale.getCountry()));

        /*open country picker*/
        rowContactInfoBinding.editCountryCode.setOnClickListener(view -> CountryPicker.showPicker((AppCompatActivity) this, true, country -> {
            rowContactInfoBinding.flagImageView.setText(GeneralFunctions.getCountryFlag(country.getCode()));
            rowContactInfoBinding.editCountryCode.setText(country.getDialCode());
        }));

        mViewDataBinding.container.addView(rowContactInfoBinding.getRoot());
    }

    @Override
    public void loadIdentificationView() {
        rowIdentificationHolderBinding = DataBindingUtil
                .inflate(LayoutInflater.from(this), R.layout.row_identification_holder,
                        mViewDataBinding.container, false);
        mViewDataBinding.container.addView(rowIdentificationHolderBinding.getRoot());
    }

    @Override
    public void loadInnerIdentificationItem() {
        insertIdentificationRow();
    }

    @Override
    public void onSaveBTNClick() {

    }


    @Override
    public void setRelationshipWithOwnerSpinner() {
        rowResidentInfoBinding.rowRelationToOwner.setOnClickListener(view -> {
            if (!relationSpinner.isAdded()) {
                relationSpinner.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
                relationSpinner.show(getSupportFragmentManager(), null);

                /*init click listener*/
                relationSpinner.setOnItemClickListener(new CustomItemClickListener() {
                    @Override
                    public void onItemClickListener(int position, String value) {
                        rowResidentInfoBinding.editRelationToOwner.setText(value);
                    }
                });
            }
        });
    }

    @Override
    public void setBirthdayDatePicker() {
        rowResidentInfoBinding.rowBirthday.setOnClickListener(view -> {
            if (!calenderBottomDialogFragment.isAdded()) {
                calenderBottomDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
                calenderBottomDialogFragment.show(getSupportFragmentManager(), null);
                /*init click listener*/
                calenderBottomDialogFragment.setOnCalendarClickListener(new CustomItemClickListener() {
                    @Override
                    public void onItemClickListener(int position, String value) {
                        rowResidentInfoBinding.editBirthday.setText(value);
                    }
                });
            }
        });
    }

    @Override
    public void setStatusSpinner() {
        rowResidentInfoBinding.rowStatus.setOnClickListener(view -> {
            if (!statusSpinner.isAdded()) {
                statusSpinner.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
                statusSpinner.show(getSupportFragmentManager(), null);

                /*init click listener*/
                statusSpinner.setOnItemClickListener(new CustomItemClickListener() {
                    @Override
                    public void onItemClickListener(int position, String value) {
                        rowResidentInfoBinding.editStatus.setText(value);
                    }
                });
            }
        });
    }

    @Override
    public void setOccupationSpinner() {
        rowResidentInfoBinding.rowOccupation.setOnClickListener(view -> {
            if (!occupationSpinner.isAdded()) {
                occupationSpinner.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
                occupationSpinner.show(getSupportFragmentManager(), null);

                /*init click listener*/
                occupationSpinner.setOnItemClickListener(new CustomItemClickListener() {
                    @Override
                    public void onItemClickListener(int position, String value) {
                        rowResidentInfoBinding.editOccupation.setText(value);
                    }
                });
            }
        });
    }

    @Override
    public void setIdTypeSpinner() {

    }

    /*insert identification items*/
    private void insertIdentificationRow() {
        RowIdentifictionBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(this), R.layout.row_identifiction,
                        rowIdentificationHolderBinding.rowHolder, false);

        if (mode.equals(ProfileMode.NEW)) {
            binding.editIdNumber.setText(AppConstant.EMPTY);
            binding.editIdType.setText(AppConstant.EMPTY);
        } else {
            //binding.editIdNumber.setText(data.getIdNumber());
            binding.editIdType.setText(data.getType());
            binding.idImageView.setImageResource(R.mipmap.ic_aadhar_dummy);
        }

        rowIdentificationHolderBinding.rowHolder.addView(binding.getRoot());
    }
}
