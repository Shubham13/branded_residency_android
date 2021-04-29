package com.paragon.sensonic.ui.fragments.vehiclesdetails;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.FragmentResidentDetailsBinding;
import com.paragon.sensonic.databinding.RowContactInfoBinding;
import com.paragon.sensonic.databinding.RowIdentificationHolderBinding;
import com.paragon.sensonic.databinding.RowIdentifictionBinding;
import com.paragon.sensonic.databinding.RowResidentInfoBinding;
import com.paragon.sensonic.helpers.AppConstant;
import com.paragon.sensonic.helpers.CustomItemClickListener;
import com.paragon.sensonic.helpers.ProfileMode;
import com.paragon.sensonic.ui.adapters.ProfileFilterAdapter;
import com.paragon.sensonic.ui.bottomsheets.CalenderBottomDialogFragment;
import com.paragon.sensonic.ui.bottomsheets.SpinnerBottomDialogFragment;
import com.paragon.brdata.dto.VehicleData;
import com.paragon.utils.MultiplePermissionsCallBack;
import com.paragon.utils.RecyclerItemClickListener;
import com.paragon.utils.base.BaseFragment;
import com.paragon.utils.dialogs.DVDialog;
import com.paragon.utils.imagepicker.bundle.PickSetup;
import com.paragon.utils.imagepicker.dialog.PickImageDialog;
import com.karumi.dexter.PermissionToken;

import java.util.Arrays;
import java.util.Objects;

public class VehiclesDetailsFragment extends BaseFragment<FragmentResidentDetailsBinding, VehiclesDetailsViewModel>
        implements VehiclesDetailsNavigator {

    private final VehiclesDetailsViewModel vehiclesDetailsViewModel = new VehiclesDetailsViewModel();
    private RowResidentInfoBinding rowResidentInfoBinding;
    private RowContactInfoBinding rowContactInfoBinding;
    private RowIdentificationHolderBinding rowIdentificationHolderBinding;
    private ProfileFilterAdapter filterAdapter;
    private final VehicleData data;
    private final ProfileMode mode;
    private boolean isToolbarItemClick = false;

    public VehiclesDetailsFragment(VehicleData data, ProfileMode mode) {
        this.data = data;
        this.mode = mode;
    }

    @Override
    public int getBindingVariable() {
        return BR.vehiclesDetailsVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_resident_details;
    }

    @Override
    public VehiclesDetailsViewModel getViewModel() {
        return vehiclesDetailsViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vehiclesDetailsViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vehiclesDetailsViewModel.init();
    }

    @Override
    public void init() {
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.GONE);
        mViewDataBinding.toolbar.toolbarLeftLayout.setOnClickListener(e -> Objects.requireNonNull(getActivity()).onBackPressed());

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

        rowResidentInfoBinding.rowBirthday.setVisibility(View.GONE);
        rowResidentInfoBinding.rowRelationToOwner.setVisibility(View.GONE);
        rowResidentInfoBinding.rowGender.getRoot().setVisibility(View.GONE);
        rowResidentInfoBinding.rowStatus.setVisibility(View.GONE);
        rowResidentInfoBinding.rowOccupation.setVisibility(View.GONE);

        if (mode.equals(ProfileMode.NEW)) {
            mViewDataBinding.toolbar.toolbarTitle.setText(getString(R.string.label_vehicles));
            rowResidentInfoBinding.profileImageView.setImageResource(R.mipmap.ic_friends);
            rowResidentInfoBinding.editName.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editPreferredName.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editRelationToOwner.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editStatus.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editOccupation.setText(AppConstant.EMPTY);
            rowResidentInfoBinding.editBirthday.setText(AppConstant.EMPTY);
        } else {
            mViewDataBinding.toolbar.toolbarTitle.setText(data.getName());
            rowResidentInfoBinding.profileImageView.setImageResource(R.mipmap.ic_dummy_car);
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
            SpinnerBottomDialogFragment dialog = new SpinnerBottomDialogFragment(getContext().getString(R.string.label_relation_to_owner),
                    Arrays.asList(getContext().getResources().getStringArray(R.array.label_relation)));
            dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            dialog.show(getChildFragmentManager(), null);

            /*init click listener*/
            dialog.setOnItemClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClickListener(int position, String value) {
                    rowResidentInfoBinding.editRelationToOwner.setText(value);
                }
            });
        });
    }

    @Override
    public void setBirthdayDatePicker() {
        rowResidentInfoBinding.rowBirthday.setOnClickListener(view -> {
            CalenderBottomDialogFragment dialog = new CalenderBottomDialogFragment();
            dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            dialog.show(getChildFragmentManager(), null);
            /*init click listener*/
            dialog.setOnCalendarClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClickListener(int position, String value) {
                    rowResidentInfoBinding.editBirthday.setText(value);
                }
            });
        });
    }

    @Override
    public void setStatusSpinner() {
        rowResidentInfoBinding.rowStatus.setOnClickListener(view -> {
            SpinnerBottomDialogFragment dialog = new SpinnerBottomDialogFragment(getContext().getString(R.string.label_status),
                    Arrays.asList(getContext().getResources().getStringArray(R.array.label_status)));
            dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            dialog.show(getChildFragmentManager(), null);

            /*init click listener*/
            dialog.setOnItemClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClickListener(int position, String value) {
                    rowResidentInfoBinding.editStatus.setText(value);
                }
            });
        });
    }

    @Override
    public void setOccupationSpinner() {
        rowResidentInfoBinding.rowOccupation.setOnClickListener(view -> {
            SpinnerBottomDialogFragment dialog = new SpinnerBottomDialogFragment(getContext().getString(R.string.label_occupation),
                    Arrays.asList(getContext().getResources().getStringArray(R.array.label_occupation)));
            dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            dialog.show(getChildFragmentManager(), null);

            /*init click listener*/
            dialog.setOnItemClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClickListener(int position, String value) {
                    rowResidentInfoBinding.editOccupation.setText(value);
                }
            });
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
            binding.idImageView.setImageResource(R.mipmap.ic_dummy_rc);
        }

        rowIdentificationHolderBinding.rowHolder.addView(binding.getRoot());
    }
}
