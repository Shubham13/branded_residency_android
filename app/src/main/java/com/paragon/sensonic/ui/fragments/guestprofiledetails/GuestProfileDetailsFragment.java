package com.paragon.sensonic.ui.fragments.guestprofiledetails;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.FragmentGuestProfileDetailsBinding;
import com.paragon.sensonic.helpers.CustomItemClickListener;
import com.paragon.sensonic.helpers.GuestType;
import com.paragon.sensonic.ui.adapters.GuestAccessAdapter;
import com.paragon.sensonic.ui.bottomsheets.CalenderBottomDialogFragment;
import com.paragon.sensonic.ui.bottomsheets.SpinnerBottomDialogFragment;
import com.paragon.brdata.dto.GuestAccess;
import com.paragon.brdata.dto.GuestAccessX;
import com.paragon.brdata.dto.GuestProfileSubTitle;
import com.paragon.utils.base.BaseFragment;
import com.paragon.utils.expandable.ExpandableRecyclerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GuestProfileDetailsFragment extends BaseFragment<FragmentGuestProfileDetailsBinding, GuestProfileDetailsViewModel> implements GuestProfileDetailsNavigator {

    private final GuestProfileDetailsViewModel guestProfileDetailsViewModel = new GuestProfileDetailsViewModel();
    private final GuestProfileSubTitle data;
    private GuestAccessAdapter mAdapter;
    private String guestType;
    private GuestAccess guestAccess;
    private CalenderBottomDialogFragment calenderBottomDialogFragment;
    private SpinnerBottomDialogFragment guestTypeSpinner;


    public GuestProfileDetailsFragment(GuestProfileSubTitle date) {
        this.data = date;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guestProfileDetailsViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        guestProfileDetailsViewModel.init();
    }

    @Override
    public int getBindingVariable() {
        return BR.profileDetailVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guest_profile_details;
    }

    @Override
    public GuestProfileDetailsViewModel getViewModel() {
        return guestProfileDetailsViewModel;
    }

    @Override
    public void init() {
        mViewDataBinding.toolbar.toolbarRightLayout.setVisibility(View.GONE);
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.GONE);
        mViewDataBinding.toolbar.toolbarRightTitle.setVisibility(View.VISIBLE);
        mViewDataBinding.toolbar.toolbarLeftLayout.setOnClickListener(e -> Objects.requireNonNull(getActivity()).onBackPressed());
        mViewDataBinding.toolbar.toolbarRightTitle.setText(getString(R.string.label_delete));
        mViewDataBinding.toolbar.toolbarTitle.setText(data.getTitle());
        mViewDataBinding.editName.setText(data.getTitle());
        mViewDataBinding.editPhone.setText(data.getPhone());
        mViewDataBinding.editGender.setText(data.getGender());
        mViewDataBinding.editDate.setText(data.getDate());
        mViewDataBinding.editGuestType.setText(GuestType.ONE_TIME_GUEST.getValue());
        mViewDataBinding.editEmail.setText(data.getEmail());
        mViewDataBinding.editAddress.setText(data.getAddress());
        guestType = GuestType.ONE_TIME_GUEST.getValue();
        guestProfileDetailsViewModel.getExpandableList(getContext());

        //init sheets
        calenderBottomDialogFragment = new CalenderBottomDialogFragment();
        guestTypeSpinner = new SpinnerBottomDialogFragment(getActivity().getString(R.string.label_guest_type),
                Arrays.asList(GuestType.ONE_TIME_GUEST.getValue(), GuestType.FREQUENT_GUEST.getValue(), GuestType.SHORT_STAYING_GUEST.getValue()));
    }

    @Override
    public void setExpandableView(GuestAccess guestAccess) {
        this.guestAccess = guestAccess;
        GuestAccessX guestAccessX = new GuestAccessX(guestAccess.getGuestAccess().getCommonAccess());
        final List<GuestAccessX> guestAccessXList = Arrays.asList(guestAccessX);
        mAdapter = new GuestAccessAdapter(getActivity(), guestAccessXList, guestType);
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
    public void onDatePickerClick() {
        if (!calenderBottomDialogFragment.isAdded()) {
            calenderBottomDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            calenderBottomDialogFragment.show(getChildFragmentManager(), null);
            /*init click listener*/
            calenderBottomDialogFragment.setOnCalendarClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClickListener(int position, String value) {
                    mViewDataBinding.editDate.setText(value);
                }
            });
        }
    }

    @Override
    public void onGuestTypeClick() {
        if (!guestTypeSpinner.isAdded()) {
            guestTypeSpinner.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            guestTypeSpinner.show(getChildFragmentManager(), null);

            /*init click listener*/
            guestTypeSpinner.setOnItemClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClickListener(int position, String value) {
                    mViewDataBinding.editGuestType.setText(value);

                    if (GuestType.FREQUENT_GUEST.getValue().matches(value))
                        guestType = GuestType.FREQUENT_GUEST.getValue();

                    if (GuestType.ONE_TIME_GUEST.getValue().matches(value))
                        guestType = GuestType.ONE_TIME_GUEST.getValue();

                    if (GuestType.SHORT_STAYING_GUEST.getValue().matches(value))
                        guestType = GuestType.SHORT_STAYING_GUEST.getValue();

                    setExpandableView(guestAccess);
                }
            });
        }
    }
}
