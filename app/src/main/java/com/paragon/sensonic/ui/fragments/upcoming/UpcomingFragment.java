package com.paragon.sensonic.ui.fragments.upcoming;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.paragon.brdata.dto.ResidentsRelationRoot;
import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.FragmentUpcomingBinding;
import com.paragon.sensonic.ui.activities.residentdetails.ResidentDetailsActivity;
import com.paragon.sensonic.ui.adapters.ResidentListAdapter;
import com.paragon.sensonic.utils.ProfileMode;
import com.paragon.sensonic.utils.ProfileType;
import com.paragon.utils.RecyclerItemClickListener;
import com.paragon.utils.base.BaseFragment;

public class UpcomingFragment extends BaseFragment<FragmentUpcomingBinding,UpcomingViewModel> implements UpcomingNavigator {

    UpcomingViewModel upcomingViewModel = new UpcomingViewModel();
    ResidentListAdapter residentListAdapter;

    @Override
    public int getBindingVariable() {
        return BR.upcomingVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_upcoming;
    }

    @Override
    public UpcomingViewModel getViewModel() {
        return upcomingViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        upcomingViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        upcomingViewModel.init();
    }

    @Override
    public void init() {
        //getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        upcomingViewModel.setResidentsData(getBaseActivity(), ProfileType.residents);
    }

    @Override
    public void setResidentsList(ResidentsRelationRoot relationRoot) {
        mViewDataBinding.dummyRv.setAdapter(new ResidentListAdapter(getBaseActivity(), relationRoot.getData(), mViewDataBinding.dummyRv));
    }
}
