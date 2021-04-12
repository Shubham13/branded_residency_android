package com.digivalet.brandresidential.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brandresidential.ui.fragments.sheetfragments.DailyFragment;
import com.digivalet.brandresidential.ui.fragments.sheetfragments.MonthlyFragment;
import com.digivalet.brandresidential.ui.fragments.sheetfragments.WeeklyFragment;

public class CustomDialogAdapter extends FragmentStatePagerAdapter {

    private final CustomItemClickListener customItemClickListener;

    public CustomDialogAdapter(@NonNull FragmentManager fm, CustomItemClickListener customItemClickListener) {
        super(fm);
        this.customItemClickListener = customItemClickListener;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DailyFragment();
            case 1:
                return new WeeklyFragment(customItemClickListener);
            case 2:
                return new MonthlyFragment(customItemClickListener);
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
