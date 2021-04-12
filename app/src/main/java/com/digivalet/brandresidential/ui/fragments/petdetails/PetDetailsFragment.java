package com.digivalet.brandresidential.ui.fragments.petdetails;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.digivalet.brandresidential.helpers.ProfileMode;
import com.digivalet.brandresidential.ui.adapters.ProfileFilterAdapter;
import com.digivalet.brandresidential.ui.bottomsheets.CalenderBottomDialogFragment;
import com.digivalet.brandresidential.ui.bottomsheets.SpinnerBottomDialogFragment;
import com.digivalet.brdata.dto.PetsData;
import com.digivalet.utils.MultiplePermissionsCallBack;
import com.digivalet.utils.RecyclerItemClickListener;
import com.digivalet.utils.base.BaseFragment;
import com.digivalet.utils.dialogs.DVDialog;
import com.digivalet.utils.imagepicker.bundle.PickSetup;
import com.digivalet.utils.imagepicker.dialog.PickImageDialog;
import com.digivalet.utils.styler.Host;
import com.digivalet.utils.styler.StyleBuilder;
import com.karumi.dexter.PermissionToken;

import java.util.Arrays;
import java.util.Objects;

public class PetDetailsFragment extends BaseFragment<FragmentResidentDetailsBinding, PetDetailsViewModel>
        implements PetDetailsNavigator {

    private final PetDetailsViewModel petDetailsViewModel = new PetDetailsViewModel();
    private RowResidentInfoBinding rowResidentInfoBinding;
    private RowContactInfoBinding rowContactInfoBinding;
    private RowIdentificationHolderBinding rowIdentificationHolderBinding;
    private ProfileFilterAdapter filterAdapter;
    private final PetsData data;
    private final ProfileMode mode;
    private boolean isToolbarItemClick = false;
    private SpinnerBottomDialogFragment relationSpinner, occupationSpinner, statusSpinner;
    private CalenderBottomDialogFragment calenderBottomDialogFragment;

    public PetDetailsFragment(PetsData data, ProfileMode mode) {
        this.data = data;
        this.mode = mode;
    }

    @Override
    public int getBindingVariable() {
        return BR.petDetailsVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_resident_details;
    }

    @Override
    public PetDetailsViewModel getViewModel() {
        return petDetailsViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        petDetailsViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        petDetailsViewModel.init();
    }

    @Override
    public void init() {
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.GONE);
        mViewDataBinding.toolbar.toolbarBackBtn.setOnClickListener(e -> Objects.requireNonNull(getActivity()).onBackPressed());

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
                }
            }
        });

        /*add touch listener*/
        mViewDataBinding.nestedScroll.setOnTouchListener((view, motionEvent) -> {
            isToolbarItemClick = false;
            return false;
        });

        /*init spinner data resources*/
        relationSpinner = new SpinnerBottomDialogFragment(getContext().getString(R.string.label_relation_to_owner),
                Arrays.asList(getContext().getResources().getStringArray(R.array.label_relation)));

        statusSpinner = new SpinnerBottomDialogFragment(getContext().getString(R.string.label_status),
                Arrays.asList(getContext().getResources().getStringArray(R.array.label_status)));

        occupationSpinner = new SpinnerBottomDialogFragment(getContext().getString(R.string.label_occupation),
                Arrays.asList(getContext().getResources().getStringArray(R.array.label_occupation)));

        calenderBottomDialogFragment = new CalenderBottomDialogFragment();
    }

    @Override
    public void setFilterList() {
        filterAdapter = new ProfileFilterAdapter(getContext(), Arrays.asList(getResources().getStringArray(R.array.label_filters_pet_details)));
        mViewDataBinding.toolbar.toolbarItemList.setAdapter(filterAdapter);
        mViewDataBinding.toolbar.toolbarItemList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
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
                .inflate(LayoutInflater.from(getContext()), R.layout.row_resident_info,
                        mViewDataBinding.container, false);

        if (mode.equals(ProfileMode.NEW)) {
            mViewDataBinding.toolbar.toolbarTitle.setText(getString(R.string.label_pets));
            rowResidentInfoBinding.profileImageView.setImageResource(R.mipmap.ic_friends);
            rowResidentInfoBinding.editName.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editPreferredName.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editRelationToOwner.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editStatus.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editOccupation.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editBirthday.setText(AppConstant.EMPTY);
        } else {
            mViewDataBinding.toolbar.toolbarTitle.setText(data.getName());
            rowResidentInfoBinding.profileImageView.setImageResource(R.mipmap.ic_pet_dog);
            rowResidentInfoBinding.editName.setText(data.getName());
        }

        /*image browse*/
        rowResidentInfoBinding.rowProfileImage.setOnClickListener(view -> {
            getBaseActivity().requestMultiplePermissions(new MultiplePermissionsCallBack() {
                @Override
                public void isAllPermissionsGranted() {
                    PickImageDialog.build(new PickSetup()).setOnPickResult(r -> rowResidentInfoBinding.profileImageView.setImageBitmap(r.getBitmap())).show(getChildFragmentManager());
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
            }, AppConstant.READ_WRITE_EXTERNAL_STORAGE_AND_CAMERA);
        });

        mViewDataBinding.container.addView(rowResidentInfoBinding.getRoot());
    }

    @Override
    public void loadContactInfoView() {
        rowContactInfoBinding = DataBindingUtil
                .inflate(LayoutInflater.from(getContext()), R.layout.row_contact_info,
                        mViewDataBinding.container, false);
        mViewDataBinding.container.addView(rowContactInfoBinding.getRoot());
    }

    @Override
    public void loadIdentificationView() {
        rowIdentificationHolderBinding = DataBindingUtil
                .inflate(LayoutInflater.from(getContext()), R.layout.row_identification_holder,
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
                relationSpinner.show(getChildFragmentManager(), null);

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
                calenderBottomDialogFragment.show(getChildFragmentManager(), null);
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
                statusSpinner.show(getChildFragmentManager(), null);

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
                occupationSpinner.show(getChildFragmentManager(), null);

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
                .inflate(LayoutInflater.from(getContext()), R.layout.row_identifiction,
                        rowIdentificationHolderBinding.rowHolder, false);

        if (mode.equals(ProfileMode.NEW)) {
            binding.editIdNumber.setText(AppConstant.EMPTY);
            binding.editIdType.setText(AppConstant.EMPTY);
        } else {
            binding.editIdType.setText(data.getType());
            binding.idImageView.setImageResource(R.mipmap.ic_dog_rc);
        }

        rowIdentificationHolderBinding.rowHolder.addView(binding.getRoot());
    }
}
