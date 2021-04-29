package com.paragon.sensonic.ui.fragments.residents;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.FragmentResidentsBinding;
import com.paragon.sensonic.helpers.ProfileMode;
import com.paragon.sensonic.helpers.ProfileType;
import com.paragon.sensonic.ui.adapters.FriendListAdapter;
import com.paragon.sensonic.ui.adapters.PetListAdapter;
import com.paragon.sensonic.ui.adapters.ResidentListAdapter;
import com.paragon.sensonic.ui.adapters.StaffListAdapter;
import com.paragon.sensonic.ui.adapters.VehicleListAdapter;
import com.paragon.sensonic.ui.fragments.frienddetails.FriendDetailsFragment;
import com.paragon.sensonic.ui.fragments.petdetails.PetDetailsFragment;
import com.paragon.sensonic.ui.fragments.residentdetails.ResidentDetailsFragment;
import com.paragon.sensonic.ui.fragments.staffdetails.StaffDetailsFragment;
import com.paragon.sensonic.ui.fragments.vehiclesdetails.VehiclesDetailsFragment;
import com.paragon.brdata.dto.Friends;
import com.paragon.brdata.dto.Pets;
import com.paragon.brdata.dto.ResidentsRelationRoot;
import com.paragon.brdata.dto.Staff;
import com.paragon.brdata.dto.Vehicle;
import com.paragon.utils.RecyclerItemClickListener;
import com.paragon.utils.base.BaseFragment;

import java.util.Objects;


public class ResidentsFragment extends BaseFragment<FragmentResidentsBinding, ResidentsViewModel> implements ResidentsNavigator {

    private final ResidentsViewModel residentViewModel = new ResidentsViewModel();
    private final ProfileType profileType;


    public ResidentsFragment(ProfileType type) {
        this.profileType = type;
    }

    @Override
    public int getBindingVariable() {
        return BR.residentsVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_residents;
    }

    @Override
    public ResidentsViewModel getViewModel() {
        return residentViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        residentViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        residentViewModel.init();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init() {
        getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.bar_tabbar_alpha_84));
        mViewDataBinding.residentsToolbar.toolbarRightImage.setImageResource(R.mipmap.ic_circle_add);
        mViewDataBinding.residentsToolbar.toolbarLeftLayout.setOnClickListener(e -> Objects.requireNonNull(getActivity()).onBackPressed());
        residentViewModel.setResidentsData(getContext(), profileType);
    }

    @Override
    public void setResidentsList(ResidentsRelationRoot relationRoot) {
        mViewDataBinding.residentsToolbar.toolbarTitle.setText(relationRoot.getTitle());
        mViewDataBinding.residentsList.setAdapter(new ResidentListAdapter(getContext(), relationRoot.getData(), mViewDataBinding.residentsList));
        mViewDataBinding.residentsList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            getBaseActivity().loadFragment(new ResidentDetailsFragment(relationRoot.getData().get(position), ProfileMode.EDIT), R.id.container);
        }));

        /*toolbar new btn listener*/
        mViewDataBinding.residentsToolbar.toolbarRightImage.setOnClickListener(view -> getBaseActivity().loadFragment(new ResidentDetailsFragment(null, ProfileMode.NEW), R.id.container));
    }

    @Override
    public void setVehiclesList(Vehicle vehicle) {
        mViewDataBinding.residentsToolbar.toolbarTitle.setText(vehicle.getTitle());
        mViewDataBinding.residentsList.setAdapter(new VehicleListAdapter(getContext(), vehicle.getData(), mViewDataBinding.residentsList));
        mViewDataBinding.residentsList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            getBaseActivity().loadFragment(new VehiclesDetailsFragment(vehicle.getData().get(position), ProfileMode.EDIT), R.id.container);
        }));

        /*toolbar new btn listener*/
        mViewDataBinding.residentsToolbar.toolbarRightImage.setOnClickListener(view -> getBaseActivity().loadFragment(new VehiclesDetailsFragment(null, ProfileMode.NEW), R.id.container));
    }

    @Override
    public void setStaffList(Staff staff) {
        mViewDataBinding.residentsToolbar.toolbarTitle.setText(staff.getTitle());
        mViewDataBinding.residentsList.setAdapter(new StaffListAdapter(getContext(), staff.getData(), mViewDataBinding.residentsList));
        mViewDataBinding.residentsList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            getBaseActivity().loadFragment(new StaffDetailsFragment(staff.getData().get(position), ProfileMode.EDIT), R.id.container);
        }));

        /*toolbar new btn listener*/
        mViewDataBinding.residentsToolbar.toolbarRightImage.setOnClickListener(view -> getBaseActivity().loadFragment(new StaffDetailsFragment(null, ProfileMode.NEW), R.id.container));
    }

    @Override
    public void setPetList(Pets pets) {
        mViewDataBinding.residentsToolbar.toolbarTitle.setText(pets.getTitle());
        mViewDataBinding.residentsList.setAdapter(new PetListAdapter(getContext(), pets.getData(), mViewDataBinding.residentsList));
        mViewDataBinding.residentsList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            getBaseActivity().loadFragment(new PetDetailsFragment(pets.getData().get(position), ProfileMode.EDIT), R.id.container);
        }));

        /*toolbar new btn listener*/
        mViewDataBinding.residentsToolbar.toolbarRightImage.setOnClickListener(view -> getBaseActivity().loadFragment(new PetDetailsFragment(null, ProfileMode.NEW), R.id.container));
    }

    @Override
    public void setFriendsList(Friends friends) {
        mViewDataBinding.residentsToolbar.toolbarTitle.setText(friends.getTitle());
        mViewDataBinding.residentsList.setAdapter(new FriendListAdapter(getContext(), friends.getData(), mViewDataBinding.residentsList));
        mViewDataBinding.residentsList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            getBaseActivity().loadFragment(new FriendDetailsFragment(friends.getData().get(position), ProfileMode.EDIT), R.id.container);
        }));

        /*toolbar new btn listener*/
        mViewDataBinding.residentsToolbar.toolbarRightImage.setOnClickListener(view -> getBaseActivity().loadFragment(new FriendDetailsFragment(null, ProfileMode.NEW), R.id.container));
    }
}
