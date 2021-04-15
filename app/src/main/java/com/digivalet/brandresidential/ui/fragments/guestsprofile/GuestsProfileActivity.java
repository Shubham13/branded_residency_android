package com.digivalet.brandresidential.ui.fragments.guestsprofile;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentGuestsProfileBinding;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brandresidential.helpers.ProfileMode;
import com.digivalet.brandresidential.ui.adapters.GuestsProfileAdapter;
import com.digivalet.brandresidential.ui.adapters.GuestsProfileFiltersAdapter;
import com.digivalet.brandresidential.ui.fragments.frienddetails.FriendDetailsActivity;
import com.digivalet.brandresidential.ui.fragments.guestprofiledetails.GuestProfileDetailsActivity;
import com.digivalet.brandresidential.ui.fragments.guestprofiledetails.GuestProfileDetailsFragment;
import com.digivalet.brdata.dto.GuestData;
import com.digivalet.brdata.dto.GuestProfile;
import com.digivalet.brdata.dto.GuestProfileSubTitle;
import com.digivalet.brdata.dto.HeaderData;
import com.digivalet.utils.base.BaseActivity;
import com.digivalet.utils.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuestsProfileActivity extends BaseActivity<FragmentGuestsProfileBinding, GuestsProfileViewModel> implements GuestsProfileNavigator {

    private final GuestsProfileViewModel guestsProfileViewModel = new GuestsProfileViewModel();
    private GuestsProfileFiltersAdapter filtersAdapter;
    private GuestsProfileAdapter guestsProfileAdapter;
    private final List<GuestData> filteredGuestList = new ArrayList<>();
    private final List<GuestData> guestDataList = new ArrayList<>();
    private List<HeaderData> fitterOptList;

    @Override
    public int getBindingVariable() {
        return BR.guestsProfileVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guests_profile;
    }

    @Override
    public GuestsProfileViewModel getViewModel() {
        return guestsProfileViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guestsProfileViewModel.setNavigator(this);
        guestsProfileViewModel.init();
    }
    
    @Override
    public void init() {
        mViewDataBinding.guestsProfileToolbar.toolbarRightImage.setVisibility(View.GONE);
        mViewDataBinding.guestsProfileToolbar.toolbarTitle.setText(getString(R.string.label_guests_profile));
        mViewDataBinding.guestsProfileToolbar.toolbarBackBtn.setOnClickListener(e -> Objects.requireNonNull(this).onBackPressed());
        guestsProfileViewModel.getGuestsData(this);
    }

    @Override
    public void setFilters(List<HeaderData> list) {
        fitterOptList = list;
        filtersAdapter = new GuestsProfileFiltersAdapter(this, fitterOptList);
        mViewDataBinding.guestsProfileToolbar.toolbarItemList.setAdapter(filtersAdapter);
        filtersAdapter.setOnClickListener(new CustomItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                if (filteredList().isEmpty()) {
                    filteredGuestList.clear();
                    filteredGuestList.addAll(guestDataList);
                    guestsProfileAdapter.notifyDataSetChanged();
                } else {
                    filteredList();
                }

                guestsProfileAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setGuestsProfileList(GuestProfile profiles) {
        filteredGuestList.addAll(profiles.getData());
        guestDataList.addAll(profiles.getData());
        guestsProfileAdapter = new GuestsProfileAdapter(this, filteredGuestList, mViewDataBinding.guestsProfileList, new CustomItemClickListener() {
            @Override
            public void onItemClickListener(int position, Object value) {
                //loadFragment(new GuestProfileDetailsFragment((GuestProfileSubTitle) value), R.id.container);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (GuestProfileSubTitle) value);
                getActivityNavigator(GuestsProfileActivity.this).startActWithData(GuestProfileDetailsActivity.class, bundle);
            }
        });
        mViewDataBinding.guestsProfileList.setAdapter(guestsProfileAdapter);
    }

    /*for filter list*/
    private List<GuestData> filteredList() {
        filteredGuestList.clear();
        for (HeaderData item : fitterOptList) {
            if (item.isSelected()) {
                filteredGuestList.add(getItem(item.getTitle()));
            } else {
                filteredGuestList.remove(getItem(item.getTitle()));
            }
        }
        return filteredGuestList;
    }

    /*get object by match filter option object*/
    private GuestData getItem(String title) {
        GuestData item = null;
        for (int i = 0; i < guestDataList.size(); i++) {
            if (title.matches(guestDataList.get(i).getTitle())) {
                item = guestDataList.get(i);
                break;
            }
        }
        return item;
    }
}
