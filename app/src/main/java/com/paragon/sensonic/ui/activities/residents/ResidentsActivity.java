package com.paragon.sensonic.ui.activities.residents;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;

import com.paragon.sensonic.databinding.ActivityResidentsBinding;
import com.paragon.sensonic.utils.ProfileMode;
import com.paragon.sensonic.utils.ProfileType;
import com.paragon.sensonic.ui.adapters.FriendListAdapter;
import com.paragon.sensonic.ui.adapters.PetListAdapter;
import com.paragon.sensonic.ui.adapters.ResidentListAdapter;
import com.paragon.sensonic.ui.adapters.StaffListAdapter;
import com.paragon.sensonic.ui.adapters.VehicleListAdapter;
import com.paragon.sensonic.ui.activities.frienddetails.FriendDetailsActivity;
import com.paragon.sensonic.ui.activities.petdetails.PetDetailsActivity;
import com.paragon.sensonic.ui.activities.residentdetails.ResidentDetailsActivity;
import com.paragon.sensonic.ui.activities.staffdetails.StaffDetailsActivity;
import com.paragon.sensonic.ui.activities.vehiclesdetails.VehiclesDetailsActivity;
import com.paragon.brdata.dto.Friends;
import com.paragon.brdata.dto.Pets;
import com.paragon.brdata.dto.ResidentsRelationRoot;
import com.paragon.brdata.dto.Staff;
import com.paragon.brdata.dto.Vehicle;
import com.paragon.utils.RecyclerItemClickListener;
import com.paragon.utils.base.BaseActivity;

import java.util.Objects;


public class ResidentsActivity extends BaseActivity<ActivityResidentsBinding, ResidentsViewModel> implements ResidentsNavigator {

    private final ResidentsViewModel residentViewModel = new ResidentsViewModel();
    private ProfileType profileType;

    @Override
    public int getBindingVariable() {
        return BR.residentsVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_residents;
    }

    @Override
    public ResidentsViewModel getViewModel() {
        return residentViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        residentViewModel.setNavigator(this);
        residentViewModel.init();
    }

    @Override
    public void init() {
        mViewDataBinding.residentsToolbar.toolbarRightImage.setImageResource(R.mipmap.ic_circle_add);
        mViewDataBinding.residentsToolbar.toolbarLeftLayout.setOnClickListener(e -> Objects.requireNonNull(this).onBackPressed());
        profileType = (ProfileType) getIntent().getSerializableExtra("type");
        residentViewModel.setResidentsData(this, profileType);
    }

    @Override
    public void setResidentsList(ResidentsRelationRoot relationRoot) {
        mViewDataBinding.residentsToolbar.toolbarTitle.setText(relationRoot.getTitle());
        mViewDataBinding.residentsList.setAdapter(new ResidentListAdapter(this, relationRoot.getData(), mViewDataBinding.residentsList));
        mViewDataBinding.residentsList.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
            //loadFragment(new ResidentDetailsFragment(relationRoot.getData().get(position), ProfileMode.EDIT), R.id.container);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", relationRoot.getData().get(position));
            bundle.putSerializable("mode", ProfileMode.EDIT);
            getActivityNavigator(this).startActWithData(ResidentDetailsActivity.class, bundle);

        }));

        /*toolbar new btn listener*/
        mViewDataBinding.residentsToolbar.toolbarRightImage.setOnClickListener(view -> {
            //loadFragment(new ResidentDetailsFragment(null, ProfileMode.NEW), R.id.container)
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", null);
            bundle.putSerializable("mode", ProfileMode.NEW);
            getActivityNavigator(this).startActWithData(ResidentDetailsActivity.class, bundle);
        });
    }

    @Override
    public void setVehiclesList(Vehicle vehicle) {
        mViewDataBinding.residentsToolbar.toolbarTitle.setText(vehicle.getTitle());
        mViewDataBinding.residentsList.setAdapter(new VehicleListAdapter(this, vehicle.getData(), mViewDataBinding.residentsList));
        mViewDataBinding.residentsList.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
            //loadFragment(new VehiclesDetailsFragment(vehicle.getData().get(position), ProfileMode.EDIT), R.id.container);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", vehicle.getData().get(position));
            bundle.putSerializable("mode", ProfileMode.EDIT);
            getActivityNavigator(this).startActWithData(VehiclesDetailsActivity.class, bundle);

        }));

        /*toolbar new btn listener*/
        mViewDataBinding.residentsToolbar.toolbarRightImage.setOnClickListener(view -> {
            //loadFragment(new VehiclesDetailsFragment(null, ProfileMode.NEW), R.id.container)
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", null);
            bundle.putSerializable("mode", ProfileMode.NEW);
            getActivityNavigator(this).startActWithData(VehiclesDetailsActivity.class, bundle);
        });
    }

    @Override
    public void setStaffList(Staff staff) {
        mViewDataBinding.residentsToolbar.toolbarTitle.setText(staff.getTitle());
        mViewDataBinding.residentsList.setAdapter(new StaffListAdapter(this, staff.getData(), mViewDataBinding.residentsList));
        mViewDataBinding.residentsList.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
            //loadFragment(new StaffDetailsFragment(staff.getData().get(position), ProfileMode.EDIT), R.id.container);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", staff.getData().get(position));
            bundle.putSerializable("mode", ProfileMode.EDIT);
            getActivityNavigator(this).startActWithData(StaffDetailsActivity.class, bundle);

        }));

        /*toolbar new btn listener*/
        mViewDataBinding.residentsToolbar.toolbarRightImage.setOnClickListener(view -> {
            //loadFragment(new StaffDetailsFragment(null, ProfileMode.NEW), R.id.container);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", null);
            bundle.putSerializable("mode", ProfileMode.NEW);
            getActivityNavigator(this).startActWithData(StaffDetailsActivity.class, bundle);

        });
    }

    @Override
    public void setPetList(Pets pets) {
        mViewDataBinding.residentsToolbar.toolbarTitle.setText(pets.getTitle());
        mViewDataBinding.residentsList.setAdapter(new PetListAdapter(this, pets.getData(), mViewDataBinding.residentsList));
        mViewDataBinding.residentsList.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
            //loadFragment(new PetDetailsFragment(pets.getData().get(position), ProfileMode.EDIT), R.id.container);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", pets.getData().get(position));
            bundle.putSerializable("mode", ProfileMode.EDIT);
            getActivityNavigator(this).startActWithData(PetDetailsActivity.class, bundle);

        }));

        /*toolbar new btn listener*/
        mViewDataBinding.residentsToolbar.toolbarRightImage.setOnClickListener(view -> {
            //loadFragment(new PetDetailsFragment(null, ProfileMode.NEW), R.id.container);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", null);
            bundle.putSerializable("mode", ProfileMode.NEW);
            getActivityNavigator(this).startActWithData(PetDetailsActivity.class, bundle);

        });
    }

    @Override
    public void setFriendsList(Friends friends) {
        mViewDataBinding.residentsToolbar.toolbarTitle.setText(friends.getTitle());
        mViewDataBinding.residentsList.setAdapter(new FriendListAdapter(this, friends.getData(), mViewDataBinding.residentsList));
        mViewDataBinding.residentsList.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
            //loadFragment(new FriendDetailsFragment(friends.getData().get(position), ProfileMode.EDIT), R.id.container);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", friends.getData().get(position));
            bundle.putSerializable("mode", ProfileMode.EDIT);
            getActivityNavigator(this).startActWithData(FriendDetailsActivity.class, bundle);
        }));

        /*toolbar new btn listener*/
        mViewDataBinding.residentsToolbar.toolbarRightImage.setOnClickListener(view -> {
            //loadFragment(new FriendDetailsFragment(null, ProfileMode.NEW), R.id.container);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", null);
            bundle.putSerializable("mode", ProfileMode.NEW);
            getActivityNavigator(this).startActWithData(FriendDetailsActivity.class, bundle);

        });
    }
}
