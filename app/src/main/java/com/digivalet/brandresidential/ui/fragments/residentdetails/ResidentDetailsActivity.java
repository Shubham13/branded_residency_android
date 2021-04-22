package com.digivalet.brandresidential.ui.fragments.residentdetails;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentResidentDetailsBinding;
import com.digivalet.brandresidential.databinding.RowContactInfoBinding;
import com.digivalet.brandresidential.databinding.RowIdentificationHolderBinding;
import com.digivalet.brandresidential.databinding.RowIdentifictionBinding;
import com.digivalet.brandresidential.databinding.RowResidentInfoBinding;
import com.digivalet.brandresidential.helpers.AppConstant;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brandresidential.helpers.ErrorMessage;
import com.digivalet.brandresidential.helpers.ProfileMode;
import com.digivalet.brandresidential.ui.adapters.ProfileFilterAdapter;
import com.digivalet.brandresidential.ui.bottomsheets.CalenderBottomDialogFragment;
import com.digivalet.brandresidential.ui.bottomsheets.SpinnerBottomDialogFragment;
import com.digivalet.brandresidential.ui.views.countrypicker.CountryPicker;
import com.digivalet.brdata.dto.Identification;
import com.digivalet.brdata.dto.ResidentsRelationMapper;
import com.digivalet.utils.GeneralFunctions;
import com.digivalet.utils.MultiplePermissionsCallBack;
import com.digivalet.utils.RecyclerItemClickListener;
import com.digivalet.utils.base.BaseActivity;
import com.digivalet.utils.base.BaseFragment;
import com.digivalet.utils.dialogs.DVDialog;
import com.digivalet.utils.imagepicker.bundle.PickSetup;
import com.digivalet.utils.imagepicker.dialog.PickImageDialog;
import com.karumi.dexter.PermissionToken;

import java.util.Arrays;
import java.util.Objects;

public class ResidentDetailsActivity extends BaseActivity<FragmentResidentDetailsBinding, ResidentDetailsViewModel>
        implements ResidentDetailsNavigator {

    private final ResidentDetailsViewModel residentDetailViewModel = new ResidentDetailsViewModel();
    private RowResidentInfoBinding rowResidentInfoBinding;
    private RowContactInfoBinding rowContactInfoBinding;
    private RowIdentificationHolderBinding rowIdentificationHolderBinding;
    private ProfileFilterAdapter filterAdapter;
    private ResidentsRelationMapper data;
    private ProfileMode mode;
    private boolean isToolbarItemClick = false;
    private SpinnerBottomDialogFragment relationSpinner, occupationSpinner, statusSpinner;
    private CalenderBottomDialogFragment calenderBottomDialogFragment;

    @Override
    public int getBindingVariable() {
        return BR.residentDetailsVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_resident_details;
    }

    @Override
    public ResidentDetailsViewModel getViewModel() {
        return residentDetailViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        residentDetailViewModel.setNavigator(this);
        residentDetailViewModel.init();
    }

    @Override
    public void init() {
        data = (ResidentsRelationMapper) getIntent().getSerializableExtra("data");
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
        relationSpinner = new SpinnerBottomDialogFragment(getString(R.string.label_relation_to_owner),
                Arrays.asList(getResources().getStringArray(R.array.label_relation)));

        statusSpinner = new SpinnerBottomDialogFragment(getString(R.string.label_status),
                Arrays.asList(getResources().getStringArray(R.array.label_status)));

        occupationSpinner = new SpinnerBottomDialogFragment(getString(R.string.label_occupation),
                Arrays.asList(getResources().getStringArray(R.array.label_occupation)));

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
            rowResidentInfoBinding.profileImageView.setImageResource(R.mipmap.dummy_resident);
            rowResidentInfoBinding.editName.setText(data.getName());
            rowResidentInfoBinding.editPreferredName.setText(data.getPrefferedName());
            rowResidentInfoBinding.editRelationToOwner.setText(data.getRelation());
            rowResidentInfoBinding.editStatus.setText(data.getStatus());
            rowResidentInfoBinding.editOccupation.setText(data.getOccupation());
            rowResidentInfoBinding.editBirthday.setText(data.getBirthday());
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
            rowContactInfoBinding.editMobileNo.setText(data.getMobile());
            rowContactInfoBinding.editEmail.setText(data.getEmail());
            rowContactInfoBinding.editAltAddress.setText(data.getAddress());
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
        if (mode.equals(ProfileMode.NEW)) {
            insertIdentificationRow(null, 0);
        } else {
            for (int i = 0; i < data.getIdentification().size(); i++) {
                insertIdentificationRow(data.getIdentification().get(i), i);

            }
                /*for (Identification item : Objects.requireNonNull(data.getIdentification())) {
                    insertIdentificationRow(item, );
                }*/
        }
    }

    @Override
    public void loadTheme() {
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
    private void insertIdentificationRow(Identification item, int position) {
        RowIdentifictionBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(this), R.layout.row_identifiction,
                        rowIdentificationHolderBinding.rowHolder, false);


        if (mode.equals(ProfileMode.NEW)) {
            binding.editIdNumber.setText(AppConstant.EMPTY);
            binding.editIdType.setText(AppConstant.EMPTY);
        } else {
            binding.editIdNumber.setText(item.getIdNumber());
            binding.editIdType.setText(item.getIdType());
            binding.idImageView.setImageResource(R.mipmap.ic_aadhar_dummy);
        }

        binding.editIdNumber.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_NEXT) {
                if (TextUtils.isEmpty(textView.getText().toString())) {
                    DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.idNumber.getValue(), getString(R.string.label_ok), null);
                }
                return true;
            }

            if (i == EditorInfo.IME_ACTION_DONE) {
                if (TextUtils.isEmpty(textView.getText().toString())) {
                    DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.idNumber.getValue(), getString(R.string.label_ok), null);
                } else {
                    mViewDataBinding.registerBtn.requestFocus();
                    GeneralFunctions.hideKeyboard(getApplicationContext());
                }
                return true;
            }
            return false;
        });

        rowIdentificationHolderBinding.rowHolder.addView(binding.getRoot());
    }

    @Override
    public void setEditorActionListener() {
        rowResidentInfoBinding.editName.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_NEXT) {
                if (TextUtils.isEmpty(rowResidentInfoBinding.editName.getText().toString())) {
                    DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.name.getValue(), getString(R.string.label_ok), null);
                } else {
                    rowResidentInfoBinding.editName.clearFocus();
                    rowResidentInfoBinding.editPreferredName.requestFocus();
                }
                return true;
            }
            return false;
        });
        rowResidentInfoBinding.editPreferredName.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_NEXT) {
                if (TextUtils.isEmpty(rowResidentInfoBinding.editPreferredName.getText().toString())) {
                    DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.preferredName.getValue(), getString(R.string.label_ok), null);
                } else {
                    rowResidentInfoBinding.editPreferredName.clearFocus();
                    rowContactInfoBinding.editMobileNo.requestFocus();
                }
                return true;
            }
            return false;
        });

        /*contact fields*/
        rowContactInfoBinding.editMobileNo.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_NEXT) {
                if (!GeneralFunctions.isValidMobileNumber(rowContactInfoBinding.editMobileNo.getText().toString())) {
                    DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.mobileNumber.getValue(), getString(R.string.label_ok), null);
                } else {
                    rowContactInfoBinding.editMobileNo.clearFocus();
                    rowContactInfoBinding.editEmail.requestFocus();
                }
                return true;
            }
            return false;
        });

        rowContactInfoBinding.editEmail.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_NEXT) {
                if (!GeneralFunctions.isEmailValid(rowContactInfoBinding.editEmail.getText().toString().trim())) {
                    DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.email.getValue(), getString(R.string.label_ok), null);
                } else {
                    rowContactInfoBinding.editEmail.clearFocus();
                    rowContactInfoBinding.editAltAddress.requestFocus();
                }
                return true;
            }
            return false;
        });

        rowContactInfoBinding.editAltAddress.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_NEXT) {
                if (TextUtils.isEmpty(rowContactInfoBinding.editAltAddress.getText().toString())) {
                    DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.address.getValue(), getString(R.string.label_ok), null);
                } else {
                    rowContactInfoBinding.editAltAddress.clearFocus();
                }
                return true;
            }
            return false;
        });
    }

    @Override
    public void setTextWatcher() {
    }

    @Override
    public void validateAllFields() {
        if (TextUtils.isEmpty(rowResidentInfoBinding.editName.getText().toString())) {
            //DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.name.getValue(), getString(R.string.label_ok), null);
        } else if (TextUtils.isEmpty(rowResidentInfoBinding.editPreferredName.getText().toString())) {
            //DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.preferredName.getValue(), getString(R.string.label_ok), null);
        } else if (!GeneralFunctions.isValidMobileNumber(rowContactInfoBinding.editMobileNo.getText().toString())) {
            //DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.mobileNumber.getValue(), getString(R.string.label_ok), null);
        } else if (!GeneralFunctions.isEmailValid(rowContactInfoBinding.editEmail.getText().toString().trim())) {
            //DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.email.getValue(), getString(R.string.label_ok), null);
        } else if (TextUtils.isEmpty(rowContactInfoBinding.editAltAddress.getText().toString())) {
            //DVDialog.showGeneralDialog(this, getString(R.string.label_alert), ErrorMessage.address.getValue(), getString(R.string.label_ok), null);
        } else {
            mViewDataBinding.registerBtn.setEnabled(false);
        }

    }
}
